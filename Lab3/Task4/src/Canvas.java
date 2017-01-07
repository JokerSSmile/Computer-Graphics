import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.sun.javafx.geom.Vec2f;
import shader.utils.ShaderProgram;
import shader.utils.ShaderType;

import java.io.*;

@SuppressWarnings("serial")
public class Canvas extends GLCanvas implements GLEventListener {

	private static final Vec2f QUAD_TOPLEFT  = new Vec2f(-1, 1);
	private static final Vec2f QUAD_SIZE   = new Vec2f(2, 2);

	private ShaderProgram shaderProgram;
	private GLU glu;
	//private Quad function;
	private UniformValues values;
	private  KeyController keyController;

	Canvas() {
		this.addGLEventListener(this);
		this.addKeyListener(keyController = new KeyController());
		this.setFocusable(true);
	}

	@Override
	public void init(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();
		values = new UniformValues();
		checkOpenGLVersion(gl);

		// ----- Your OpenGL initialization code here -----
		//function = new Quad(QUAD_TOPLEFT, QUAD_SIZE);
		initShaderProgram(gl);
		initGLContext(gl);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL().getGL2();

		if (height <= 0) {

			height = 1;
		}
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(0, 1, 0, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		// ----- Drawing shape -----
		updateValues(gl);
		keyController.validateValues(values);
		shaderProgram.use(gl);
		gl.glLoadIdentity();

		//function.draw(drawable);


		gl.glBegin(GL2.GL_QUADS);
		{
			gl.glTexCoord2f(0.0f, 0.0f);
			gl.glVertex3f(0.0f, 1.0f, 1.0f);
			gl.glTexCoord2f(1.0f, 0.0f);
			gl.glVertex3f(1.0f, 1.0f, 1.0f);
			gl.glTexCoord2f(1.0f, 1.0f);
			gl.glVertex3f(1.0f, 0.0f, 1.0f);
			gl.glTexCoord2f(0.0f, 1.0f);
			gl.glVertex3f(0.0f, 0.0f, 1.0f);
		}
		gl.glEnd();
		gl.glFlush();

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {

		shaderProgram.dispose(drawable.getGL().getGL2());
	}

	private void initGLContext(GL2 gl){

		gl.glClearColor(0.25f, 0.25f, 0.4f, 0.0f);     // Background => dark blue
		gl.glDisable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_TEXTURE_2D);

		gl.setSwapInterval(1);
		gl.glShadeModel(GL2.GL_FLAT);
	}

	private void initShaderProgram(GL2 gl){


		String vSource = loadStringFileFromCurrentPackage("source/glslFunction.vert");
		String fSource = loadStringFileFromCurrentPackage("source/glslFunction.frag");


		shaderProgram = new ShaderProgram(gl);
		shaderProgram.compileShader(gl, vSource, ShaderType.Vertex);
		shaderProgram.compileShader(gl, fSource, ShaderType.Fragment);

		shaderProgram.link(gl);
	}

	private void checkOpenGLVersion(GL2 gl)
	{
		if (!gl.isExtensionAvailable("GL_VERSION_3_2"))
		{
			throw new RuntimeException("Sorry, but OpenGL 3.2 is not available");
		}
	}

	private String loadStringFileFromCurrentPackage( String fileName){
		InputStream stream = this.getClass().getResourceAsStream(fileName);

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder strBuilder = new StringBuilder();

		try {
			String line = reader.readLine();

			while(line != null){
				strBuilder.append(line + "\n");
				line = reader.readLine();
			}
			reader.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strBuilder.toString();
	}

	private void updateValues(GL2 gl){


		try {
			int mandel_pos = shaderProgram.findUniform(gl, "mandel_pos");
			int mandel_size = shaderProgram.findUniform(gl, "mandel_size");
			int mandel_iterations = shaderProgram.findUniform(gl, "mandel_iterations");

			gl.glUniform2f(mandel_pos, values.x, values.y);
			gl.glUniform2f(mandel_size, values.width, values.height);
			gl.glUniform1f(mandel_iterations, values.iterations);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
