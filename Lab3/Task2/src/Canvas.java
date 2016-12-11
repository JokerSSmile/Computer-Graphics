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
import java.nio.IntBuffer;
import java.util.Scanner;

import static com.sun.org.apache.bcel.internal.util.SecuritySupport.getResourceAsStream;

@SuppressWarnings("serial")
public class Canvas extends GLCanvas implements GLEventListener {

    private static final Vec2f QUAD_TOPLEFT  = new Vec2f(-300, -150);
    private static final Vec2f QUAD_SIZE   = new Vec2f(600, 300);

    private ShaderProgram shaderProgram;

    private Quad function;

    Canvas() {
        this.addGLEventListener(this);
        this.setFocusable(true);
    }

    @Override
    public void init(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
        checkOpenGLVersion(gl);

        // ----- Your OpenGL initialization code here -----
		function = new Quad(QUAD_TOPLEFT, QUAD_SIZE);
        initShaderProgram(gl);
        initGLContext(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL().getGL2();

        final float halfWidth = width * 0.5f;
        final float halfHeight = height * 0.5f;
        final Matrix4f matrix = new Matrix4f().ortho(-halfWidth, halfWidth, -halfHeight, halfHeight, 0.1f, 100.f);
        //gl.glViewport(0, 0, width, height);
        //gl.glMatrixMode(GL2.GL_PROJECTION);
        //gl.glLoadMatrixf(matrix.get(BufferUtil.newFloatBuffer(16)));
        //gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

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

        //gl.glClearColor(1f, 1f, 0f, 0f);

        //gl.glEnable(GL2.GL_DEPTH_TEST);
        //gl.glEnable(GL2.GL_CCW);
        //gl.glEnable(GL2.GL_BACK);
        //gl.glEnable(GL2.GL_CULL_FACE);
    }

    private void initShaderProgram(GL2 gl){


        String vSource = loadStringFileFromCurrentPackage("source/glslFunction.vert");
        String fSource = loadStringFileFromCurrentPackage("source/glslFunction.frag");


        shaderProgram = new ShaderProgram(gl);
        //shaderProgram.compileShader(gl, vSource, ShaderType.Vertex);
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
