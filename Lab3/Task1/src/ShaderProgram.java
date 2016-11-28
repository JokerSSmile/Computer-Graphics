import com.jogamp.opengl.GL2;
import com.sun.prism.impl.BufferUtil;

import java.nio.IntBuffer;
import java.util.Vector;

public class ShaderProgram {

	private int programId = 0;
	private Vector<Integer> shaders;

	ShaderProgram(GL2 gl){

		shaders = new Vector<>();
		programId = gl.glCreateProgram();
	}

	ShaderProgram(GL2 gl, int a){

		programId = 0;
	}

	private void freeShaders(GL2 gl){

		for (int shaderId : shaders)
		{
			gl.glDetachShader(programId, shaderId);
			gl.glDeleteShader(shaderId);
		}
		shaders.clear();
	}


	void compileShader(GL2 gl, String source, ShaderType type){

		final String[] pSourceLines= { source };
		final IntBuffer pSourceLengths = BufferUtil.newIntBuffer(1);
		pSourceLengths.put(source.length());


		ShaderRaii shader = new ShaderRaii(gl, type);
		gl.glShaderSource(shader.getId(), 1, pSourceLines, null);
		gl.glCompileShader(shader.getId());

		IntBuffer compileStatus = BufferUtil.newIntBuffer(1);
		compileStatus.put(0);
		gl.glGetShaderiv(shader.getId(), GL2.GL_COMPILE_STATUS, compileStatus);

		shaders.add(shader.release());
		gl.glAttachShader(programId, shaders.lastElement());

	}

	void link(GL2 gl){

		gl.glLinkProgram(programId);
		IntBuffer linkStatus =  BufferUtil.newIntBuffer(1);
		linkStatus.put(0);

		gl.glGetProgramiv(programId, GL2.GL_LINK_STATUS, linkStatus);

		freeShaders(gl);
	}

	void use(GL2 gl){

		gl.glUseProgram(programId);
	}

	final int findUniform(GL2 gl, String name) throws Exception{

		int location = gl.glGetUniformLocation(programId, name);
		if (location == -1){

			throw new Exception("Wrong shader variable name: " + name);
		}

		return location;
	}

	void dispose(GL2 gl){

		freeShaders(gl);
		gl.glDeleteProgram(programId);
	}
}
