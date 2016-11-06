import com.jogamp.common.util.Function;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.sun.javafx.geom.Vec2f;
import com.sun.javafx.geom.Vec3f;
import com.sun.prism.impl.BufferUtil;

import java.lang.instrument.Instrumentation;
import java.nio.FloatBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

final class FunctionSurface{

    static Vec3f getPosition(final Function<Vec3f, Float> fn, float x, float z){

        return fn.eval(x, z);
    }

    static void calculateNormals(Vector<SVertexP3N> vertices, final Function<Vec3f, Float> fn, float step){

        for (SVertexP3N vertice : vertices){

            final Vec3f position = vertice.position;
            Vec3f dir1 = getPosition(fn, position.x, position.z + step);
            dir1.sub(position);
            Vec3f dir2 = getPosition(fn, position.x + step, position.z);
            dir2.sub(position);

            vertice.normal = MathVec3f.normalize(MathVec3f.cross(dir1, dir2));
        }
    }

    static void doWithBindedArrays(final Vector<SVertexP3N> vertices, Callable callback, GLAutoDrawable drawable){

        GL2 gl = drawable.getGL().getGL2();

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

        // Выполняем привязку vertex array и normal array
        //final int stride = (int)ObjectSizeFetcher.getObjectSize(new SVertexP3N());
        final int stride = 24;

        //FloatBuffer firstElementNormals = FloatBuffer.wrap(new float[] {vertices.firstElement().normal.x,
        //        vertices.firstElement().normal.y, vertices.firstElement().normal.z});
        //FloatBuffer firstElementPositions = FloatBuffer.wrap(new float[] {vertices.firstElement().position.x,
        //        vertices.firstElement().position.y, vertices.firstElement().position.z});

        float a[] = {vertices.get(0).normal.x, vertices.get(0).normal.y, vertices.get(0).normal.z};
        float b[] = {vertices.get(0).position.x, vertices.get(0).position.y, vertices.get(0).position.z};

        FloatBuffer normals = BufferUtil.newFloatBuffer(3);
        normals.put(a);
        normals.rewind();

        FloatBuffer positions = BufferUtil.newFloatBuffer(3);
        positions.put(b);
        positions.rewind();

        gl.glNormalPointer(GL2.GL_FLOAT, stride, normals);
        gl.glVertexPointer(3, GL2.GL_FLOAT, stride, positions);

        // Выполняем внешнюю функцию.
        try{
            callback.call();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Выключаем режим vertex array и normal array.
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);

    }

    static void calculateTriangleStripIndicies(Vector<Integer> indicies, int columnCount, int rowCount)
    {
        indicies.clear();
        // вычисляем индексы вершин.
        for (int ci = 0; ci < columnCount - 1; ++ci)
        {
            if (ci % 2 == 0)
            {
                for (int ri = 0; ri < rowCount; ++ri)
                {
                    int index = ci * rowCount + ri;
                    indicies.add(index + rowCount);
                    indicies.add(index);
                }
            }
            else
            {
                for (int ri = rowCount - 1; ri < rowCount; --ri)
                {
                    int index = ci * rowCount + ri;
                    indicies.add(index);
                    indicies.add(index + rowCount);
                }
            }
        }
    }
}

class DottedMoebiusStrip {

    private Vector<SVertexP3N> vertices;
    private float DOT_SIZE = 5.f;
    private Function<Vec3f, Float> fn;

    DottedMoebiusStrip(final Function<Vec3f, Float> fn){

        vertices = new Vector<>();
        this.fn = fn;
    }

    void tesselate(final Vec2f rangeX, final Vec2f rangeZ, float step){

        vertices.clear();

        for (float x = rangeX.x; x < rangeX.y; x += step){
            for (float z = rangeZ.x; z < rangeZ.y; z += step){

                vertices.add(new SVertexP3N(FunctionSurface.getPosition(fn, x, z)));
            }
        }
        FunctionSurface.calculateNormals(vertices, fn, step);
    }

    void draw(GLAutoDrawable drawable){

        final GL2 gl = drawable.getGL().getGL2();

        gl.glPointSize(DOT_SIZE);
        FunctionSurface.doWithBindedArrays(vertices, new Callable() {
            @Override
            public Object call() throws Exception {
                gl.glDrawArrays(GL2.GL_POINTS, 0, vertices.size());
                return null;
            }
        }, drawable);
    }
}

class SolidMoebiusStrip {

    private Vector<SVertexP3N> vertices;
    private Function<Vec3f, Float> fn;
    private Vector<Integer> indicies;

    SolidMoebiusStrip(final Function<Vec3f, Float> fn){

        vertices = new Vector<>();
        indicies = new Vector<>();
        this.fn = fn;
    }

    void tesselate(final Vec2f rangeX, final Vec2f rangeZ, float step){

        final int columnCount = (int)((rangeX.y - rangeX.x) / step);
        final int rowCount = (int)((rangeZ.y - rangeZ.x) / step);

        vertices.clear();

        for (int ci = 0; ci < columnCount; ++ci)
        {
        final float x = rangeX.x + step * (float)ci;
            for (int ri = 0; ri < rowCount; ++ri)
            {
            final float z = rangeZ.x + step * (float)ri;
                vertices.add(new SVertexP3N(FunctionSurface.getPosition(fn, x, z)));
            }
        }

        FunctionSurface.calculateNormals(vertices, fn, step);
        FunctionSurface.calculateTriangleStripIndicies(indicies, columnCount, rowCount);
    }

}

