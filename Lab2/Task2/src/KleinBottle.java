import com.jogamp.common.util.Function;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import com.sun.javafx.geom.Vec2f;
import com.sun.javafx.geom.Vec3f;
import com.sun.prism.impl.BufferUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

final class FunctionSurface{

    static Vec3f getPosition(final Function<Vec3f, Float> fn, float x, float z){

        return fn.eval(x, z);
    }

    static void calculateNormal(SVertexP3N vertice, final Function<Vec3f, Float> fn, float step){

        final Vec3f position = vertice.position;
        Vec3f dir1 = getPosition(fn, position.x, position.z + step);
        dir1.sub(position);
        Vec3f dir2 = getPosition(fn, position.x + step, position.z);
        dir2.sub(position);

        vertice.normal = MathVec3f.normalize(MathVec3f.cross(dir1, dir2));
    }

    static void doWithBindedArrays(Vector<SVertexP3N> vertices, GLAutoDrawable drawable, Callable callable){

        GL2 gl = drawable.getGL().getGL2();

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

        // Выполняем привязку vertex array и normal array
        FloatBuffer positions = fillPositionsArray(vertices);
        FloatBuffer normals = fillNormalsArray(vertices);

        normals.rewind();
        positions.rewind();

        gl.glNormalPointer(GL2.GL_FLOAT, 0, normals);
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, positions);

        try {
            callable.call();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // Выключаем режим vertex array и normal array.
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);

    }

    private static FloatBuffer fillPositionsArray(final Vector<SVertexP3N> vertices){

        FloatBuffer positions = BufferUtil.newFloatBuffer(vertices.size() * 3);

        for (SVertexP3N vertice : vertices) {

            positions.put(vertice.position.x);
            positions.put(vertice.position.y);
            positions.put(vertice.position.z);
        }

        return positions;
    }

    private static FloatBuffer fillNormalsArray(Vector<SVertexP3N> vertices){

        FloatBuffer normals = BufferUtil.newFloatBuffer(vertices.size() * 3 + 1);

        int x = 0;
        for (SVertexP3N vertice : vertices) {

            normals.put(vertice.normal.x);
            normals.put(vertice.normal.y);
            normals.put(vertice.normal.z);
        }

        return normals;
    }
}

class DottedKleinBottle {

    private Vector<SVertexP3N> vertices;
    private float DOT_SIZE = 8.f;
    private Function<Vec3f, Float> fn;

    DottedKleinBottle(final Function<Vec3f, Float> fn){

        vertices = new Vector<>();
        this.fn = fn;
    }

    void tesselate(final Vec2f rangeX, final Vec2f rangeZ, float step){

        vertices.clear();

        for (float x = rangeX.x; x < rangeX.y; x += step){
            for (float z = rangeZ.x; z < rangeZ.y; z += step){

                vertices.add(new SVertexP3N(FunctionSurface.getPosition(fn, x, z)));
                FunctionSurface.calculateNormal(vertices.lastElement(), fn, step);
            }
        }
    }

    private void drawArrays(GL2 gl){

        gl.glDrawArrays(GL2.GL_POINTS, 0, vertices.size());
    }

    void draw(GLAutoDrawable drawable){

        final GL2 gl = drawable.getGL().getGL2();

        gl.glPointSize(DOT_SIZE);

        FunctionSurface.doWithBindedArrays(vertices, drawable, new Callable() {
            @Override
            public Object call() throws Exception {

                drawArrays(gl);
                return null;
            }
        });
    }
}

class SolidMoebiusStrip {

    private Vector<SVertexP3N> vertices;
    private Function<Vec3f, Float> fn;
    private IntBuffer indiciesBuffer;
    //private FloatBuffer verticiesBuffer;

    SolidMoebiusStrip(final Function<Vec3f, Float> fn){

        vertices = new Vector<>();
        this.fn = fn;
    }

    private void calculateTriangleStripIndicies(int columnCount, int rowCount) {

        indiciesBuffer.clear();

        // вычисляем индексы вершин.
        for (int ci = 0; ci < columnCount - 1; ci++)
        {
            if (ci % 2 == 0)
            {
                for (int ri = 0; ri < rowCount; ri++)
                {
                    int index = ci * rowCount + ri;
                    indiciesBuffer.put(index + rowCount);
                    indiciesBuffer.put(index);
                }
            }
            else
            {
                for (int ri = rowCount - 1; ri >= 0; ri--)
                {
                    int index = ci * rowCount + ri;
                    indiciesBuffer.put(index);
                    indiciesBuffer.put(index + rowCount);
                }
            }
        }

        indiciesBuffer.rewind();
    }

    void tesselate(final Vec2f rangeX, final Vec2f rangeZ, float step){

        final int columnCount = (int)((rangeX.y - rangeX.x) / step);
        final int rowCount = (int)((rangeZ.y - rangeZ.x) / step);

        indiciesBuffer = BufferUtil.newIntBuffer((columnCount - 1) * rowCount * 2);
        vertices.clear();

        for (int ci = 0; ci < columnCount; ci++) {
            final float x = rangeX.x + step * (float)ci;
            for (int ri = 0; ri < rowCount; ri++) {
                final float z = rangeZ.x + step * (float)ri;
                vertices.add(new SVertexP3N(FunctionSurface.getPosition(fn, x, z)));

                FunctionSurface.calculateNormal(vertices.lastElement(), fn, step);
            }
        }

        calculateTriangleStripIndicies(columnCount, rowCount);
    }

    private void drawElements(GL2 gl){

        gl.glDrawElements(GL2.GL_TRIANGLE_STRIP, indiciesBuffer.limit(), GL2.GL_UNSIGNED_INT, indiciesBuffer);
    }

    void draw(GLAutoDrawable drawable){
        final GL2 gl = drawable.getGL().getGL2();

        gl.glCullFace(GL2.GL_BACK);

       //gl.glFrontFace(GL2.GL_CW);

        FunctionSurface.doWithBindedArrays(vertices, drawable, new Callable() {
            @Override
            public Object call() throws Exception {
                drawElements(gl);
                return null;
            }
        });

        gl.glCullFace(GL2.GL_FRONT);
        //gl.glFrontFace(GL2.GL_CCW);

        FunctionSurface.doWithBindedArrays(vertices, drawable, new Callable() {
            @Override
            public Object call() throws Exception {
                drawElements(gl);
                return null;
            }
        });
    }
}

