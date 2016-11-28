import com.jogamp.common.util.Function;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import com.sun.javafx.geom.Vec2f;
import com.sun.javafx.geom.Vec3f;
import com.sun.prism.impl.BufferUtil;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

final class FunctionUtils{

	static void doWithBindedArrays(Vector<SVertexP3N> vertices, GLAutoDrawable drawable, Callable callable){

		GL2 gl = drawable.getGL().getGL2();

		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

		FloatBuffer positions = fillPositionsArray(vertices);

		positions.rewind();

		gl.glVertexPointer(3, GL2.GL_FLOAT, 0, positions);

		try {
			callable.call();
		}
		catch (Exception e){
			e.printStackTrace();
		}

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
}

class Function2d {

	private static final float SHIFT = 0.05f;

	private Vector<SVertexP3N> vertices;

	Function2d(float length, int verticeAmount){

		vertices = new Vector<>();
		tesselate(length, verticeAmount);
	}

	private void tesselate(float length, int verticeAmount){

		for (int i = 0; i < verticeAmount; ++i)
		{
			SVertexP3N vertex = new SVertexP3N();
			vertex.position =  new Vec3f((- SHIFT * verticeAmount + i) * length / verticeAmount, -2.f, 0.f);
			vertices.add(vertex);
		}
	}

	private void drawElements(GL2 gl){

		gl.glDrawArrays(GL2.GL_POINTS, 0, vertices.size());
	}

	void draw(GLAutoDrawable drawable){
		final GL2 gl = drawable.getGL().getGL2();

		FunctionUtils.doWithBindedArrays(vertices, drawable, () -> {
			drawElements(gl);
			return null;
		});
	}
}