import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.sun.javafx.geom.Vec2f;
import com.sun.prism.impl.BufferUtil;
import vector.utils.SVertexP2T2;
import vector.utils.VecUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.Callable;

final class FunctionUtils{

	static void doWithBindedArrays(Vector<SVertexP2T2> vertices, GLAutoDrawable drawable, Callable callable){

		GL2 gl = drawable.getGL().getGL2();

		gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);

		FloatBuffer positions = fillPositionsBuffer(vertices);
		FloatBuffer textures = fillTexturesBuffer(vertices);

		positions.rewind();
		textures.rewind();

		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, textures);
		gl.glVertexPointer(2, GL2.GL_FLOAT, 0, positions);

		try {
			callable.call();
		}
		catch (Exception e){
			e.printStackTrace();
		}

		gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
	}

	private static FloatBuffer fillPositionsBuffer(final Vector<SVertexP2T2> vertices){

		FloatBuffer positions = BufferUtil.newFloatBuffer(vertices.size() * 2);

		for (SVertexP2T2 vertice : vertices) {

			positions.put(vertice.position.x);
			positions.put(vertice.position.y);
		}

		return positions;
	}

	private static FloatBuffer fillTexturesBuffer(final Vector<SVertexP2T2> vertices){

		FloatBuffer texCoords = BufferUtil.newFloatBuffer(vertices.size() * 2);

		for (SVertexP2T2 vertice : vertices) {

			texCoords.put(vertice.texCoord.x);
			texCoords.put(vertice.texCoord.y);
		}

		return texCoords;
	}
}

class Quad {

	private static final float MAX_TEX_COORD = 1.f;

	private Vector<SVertexP2T2> vertices;
	private IntBuffer indicies;

	Quad(Vec2f leftTop, Vec2f size){

		//SVertexP2T2 vLeftTop = new SVertexP2T2(leftTop, new Vec2f(0, 0));
		//SVertexP2T2 vRightTop = new SVertexP2T2(VecUtils.sum(leftTop, new Vec2f(size.x, 0.f)), new Vec2f(MAX_TEX_COORD, 0));
		//SVertexP2T2 vLeftBottom = new SVertexP2T2(VecUtils.sum(leftTop, new Vec2f(0.f, size.y)), new Vec2f(0, MAX_TEX_COORD));
		//SVertexP2T2 vRightBottom = new SVertexP2T2(VecUtils.sum(leftTop, new Vec2f(size.x, size.y)), new Vec2f(MAX_TEX_COORD, MAX_TEX_COORD));

		SVertexP2T2 vLeftTop = new SVertexP2T2(new Vec2f(-1, 1), new Vec2f(0, 0));
		SVertexP2T2 vRightTop = new SVertexP2T2(new Vec2f(1, 1), new Vec2f(MAX_TEX_COORD, 0));
		SVertexP2T2 vLeftBottom = new SVertexP2T2(new Vec2f(-1, -1), new Vec2f(0, MAX_TEX_COORD));
		SVertexP2T2 vRightBottom = new SVertexP2T2(new Vec2f(1, -1), new Vec2f(MAX_TEX_COORD, MAX_TEX_COORD));

		vertices = new Vector<>(Arrays.asList(vLeftTop, vRightTop, vLeftBottom, vRightBottom));
		indicies = BufferUtil.newIntBuffer(6);
		int[] indArray = { 0, 1, 2, 1, 3, 2 };
		indicies.put(indArray);
		indicies.rewind();
	}

	private void drawElements(GL2 gl){

		gl.glDrawElements(GL2.GL_QUADS, indicies.limit(), GL2.GL_UNSIGNED_INT, indicies);
		//gl.glDrawArrays(GL2.GL_QUADS, 0, 6);
	}

	void draw(GLAutoDrawable drawable){
		final GL2 gl = drawable.getGL().getGL2();

		FunctionUtils.doWithBindedArrays(vertices, drawable, () -> {
			drawElements(gl);
			return null;
		});
	}
}