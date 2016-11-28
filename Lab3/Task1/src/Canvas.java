import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Scanner;

@SuppressWarnings("serial")
public class Canvas extends GLCanvas implements GLEventListener {

    private static final float LENGTH = 1.f;
    private static final int VERTICIES = (int)LENGTH * 500 * (int)Math.PI;

    private GLU glu;
    private ShaderProgram shaderProgram;
    private TwistValueController twistController;
    private Function2d function;

    Canvas() {
        this.addGLEventListener(this);
        this.setFocusable(true);
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        glu = new GLU();

		twistController = new TwistValueController();
		this.addKeyListener(twistController);

        // ----- Your OpenGL initialization code here -----
		function = new Function2d(LENGTH, VERTICIES);
        initShaderProgram(gl);
        initLight(gl);
        initGLContext(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        GL2 gl = drawable.getGL().getGL2();

        if (height == 0){
            height = 1;
        }

        final float fieldOfView = 60.f;
        final float aspect = (float)width / height;
        final float zNear = 0.1f;
        final float zFar = 100.f;

        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(fieldOfView, aspect, zNear, zFar);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
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
        try {
            int twist = shaderProgram.findUniform(gl, "TWIST");
            gl.glUniform1f(twist, twistController.getCurrentValue());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        function.draw(drawable);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        twistController.update();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

        shaderProgram.dispose(drawable.getGL().getGL2());
    }

    private void initGLContext(GL2 gl){

        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1f);
        gl.glEnable(GL2.GL_DEPTH_TEST);

        gl.glEnable(GL2.GL_CCW);
        gl.glEnable(GL2.GL_BACK);
        gl.glEnable(GL2.GL_CULL_FACE);
    }

    private void initLight(GL2 gl){

        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_LIGHTING);

        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);

        final float[] AMBIENT = { 0.1f, 0.1f, 0.1f, 0.1f };
        final float[] DIFFUSE = { 1, 1, 1, 1 };
        final float[] SPECULAR = { 1, 1, 1, 1 };
        final float[] LIGHT_POSITION = {-1.f, 0.2f, 0.7f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, AMBIENT, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, DIFFUSE, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, SPECULAR, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, LIGHT_POSITION, 0);
    }

    private void initShaderProgram(GL2 gl){

        String source = null;
        try {
             source = new Scanner(new File("src/source/glslFunction.vert")).useDelimiter("\\Z").next();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        shaderProgram = new ShaderProgram(gl);
        shaderProgram.compileShader(gl, source, ShaderType.Vertex);
        shaderProgram.link(gl);
    }


}
