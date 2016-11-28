import com.jogamp.opengl.GL2;

public class ShaderRaii {

	private int id;

	private int MapShaderType(ShaderType type){
		switch (type){
			case Vertex:
				return GL2.GL_VERTEX_SHADER;
			case Fragment:
				return GL2.GL_FRAGMENT_SHADER;
		}
		return -1;
	}

	ShaderRaii(GL2 gl, ShaderType type){

		id = gl.glCreateShader(MapShaderType(type));
	}

	int getId(){

		return id;
	}

	int release(){

		int id = this.id;
		this.id = 0;

		return id;
	}

	void dispose(GL2 gl){

		gl.glDeleteShader(id);
	}

}
