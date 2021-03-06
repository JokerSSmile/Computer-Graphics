import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.sun.javafx.geom.Vec2f;
import com.sun.prism.impl.BufferUtil;
import org.joml.Matrix4f;
import shader.utils.ShaderProgram;
import shader.utils.ShaderType;

import java.io.*;

@SuppressWarnings("serial")
public class Canvas extends GLCanvas implements GLEventListener {

    private static final Vec2f QUAD_TOPLEFT  = new Vec2f(-300, -150);
    private static final Vec2f QUAD_SIZE   = new Vec2f(600, 300);

    private ShaderProgram shaderProgram;
    private GLU glu;

    private Quad function;

    Canvas() {
        this.addGLEventListener(this);
    }

    @Override
    public void init(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();
        checkOpenGLVersion(gl);

        // ----- Your OpenGL initialization code here -----
		function = new Quad(QUAD_TOPLEFT, QUAD_SIZE);
        initShaderProgram(gl);
        initGLContext(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {


		GL2 gl = drawable.getGL().getGL2();

		gl.glViewport(0, 0, width, height);

        final float halfWidth = width * 0.5f;
        final float halfHeight = height * 0.5f;
        final Matrix4f matrix = new Matrix4f().ortho(-halfWidth, halfWidth, -halfHeight, halfHeight, 0.1f, 100.f);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadMatrixf(matrix.get(BufferUtil.newFloatBuffer(16)));
        gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();


    	/*
		GL2 gl = drawable.getGL().getGL2();                     // get the OpenGL 2 graphics context

		if (height == 0){
			height = 1;                            // prevent divide by zero
		}

		final float fieldOfView = 60.f;
		final float aspect = (float)width / height;
		final float zNear = 0.1f;
		final float zFar = 100.f;

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL2.GL_PROJECTION);                     // choose projection matrix
		gl.glLoadIdentity();                                    // reset projection matrix
		glu.gluPerspective(fieldOfView, aspect, zNear, zFar);           // fovy, aspect, zNear, zFar

		// Enable the model-view transform
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();                                    // reset
		*/
    }

    @Override
    public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		// ----- OpenGL rendering code -----
        gl.glTranslatef(0.0f, 0.0f, -10.0f);

        // ----- Drawing shape -----
        shaderProgram.use(gl);
        function.draw(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

        shaderProgram.dispose(drawable.getGL().getGL2());
    }

    private void initGLContext(GL2 gl){

		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1f);
		gl.glEnable(GL2.GL_DEPTH_TEST);

    }

    private void initShaderProgram(GL2 gl){


        String vSource = loadStringFileFromCurrentPackage("source/glslFunction.vert");
        String fSource = loadStringFileFromCurrentPackage("source/glslFunction.frag");


        shaderProgram = new ShaderProgram(gl);
        shaderProgram.compileShader(gl, vSource, ShaderType.Vertex);
        shaderProgram.compileShader(gl, fSource, ShaderType.Fragment);

		//IntBuffer resultVertex = BufferUtil.newIntBuffer(1);
		//resultVertex.put(1);
		//gl.glGetObjectParameterivARB(shaderProgram.getProgramId(), GL2.GL_OBJECT_COMPILE_STATUS_ARB, resultVertex);

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
		// allocate a string builder to add line per line
		StringBuilder strBuilder = new StringBuilder();

		try {
			String line = reader.readLine();
			// get text from file, line per line

			while(line != null){
				strBuilder.append(line + "\n");
				line = reader.readLine();
			}
			// close resources
			reader.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strBuilder.toString();
	}
}
