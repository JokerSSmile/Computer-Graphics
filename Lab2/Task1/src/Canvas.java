import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import java.nio.FloatBuffer;

@SuppressWarnings("serial")
public class Canvas extends GLCanvas implements GLEventListener {

    // Setup OpenGL Graphics Renderer
    private GLU glu;                                                    // for the GL Utility
    private MouseControl mouseListener;
    private PentakisDodecahedron pentakisDodecahedron;

    /** Constructor to setup the GUI for this Component */
    public Canvas() {
        this.addGLEventListener(this);
    }

    /**
     * Called back immediately after the OpenGL context is initialized. Can be used
     * to perform one-time initialization. Run only once.
     */
    @Override
    public void init(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();                             // get the OpenGL graphics context
        glu = new GLU();                                                // get GL Utilities

        // ----- Your OpenGL initialization code here -----

        mouseListener = new MouseControl();
        this.addMouseMotionListener(mouseListener);
        this.addMouseListener(mouseListener);
        pentakisDodecahedron = new PentakisDodecahedron();

        initLight(gl);
        initGLContext(gl);
    }

    /**
     * Call-back handler for window re-size event. Also called when the drawable is
     * first set to visible.
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        GL2 gl = drawable.getGL().getGL2();                     // get the OpenGL 2 graphics context

        if (height == 0){
            height = 1;                            // prevent divide by zero
        }

        final float fieldOfView = 45.f;
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
    }

    /**
     * Called back by the animator to perform rendering.
     */
    @Override
    public void display(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();                             // get the OpenGL 2 graphics context
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);  // clear color and depth buffers
        gl.glLoadIdentity();                                            // reset the model-view matrix

        // ----- Your OpenGL rendering code here (Render a white triangle for testing) -----

        gl.glTranslatef(0.0f, 0.0f, -10.0f);                            // translate into the screen
        gl.glRotatef(mouseListener.getDeltaX(), 0.0f, 1.0f, 0.0f);
        gl.glRotatef(mouseListener.getDeltaY(), 1.0f, 0.0f, 0.0f);


        // ----- Drawing shape -----

        pentakisDodecahedron.draw(gl);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) { }

    private void initGLContext(GL2 gl){

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                        // set background (clear) color
        gl.glClearDepth(1.0f);                                          // set clear depth value to farthest
        gl.glEnable(GL2.GL_DEPTH_TEST);                                 // enables depth testing
        gl.glDepthFunc(GL2.GL_LEQUAL);                                  // the type of depth test to do
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);   // best perspective correction
        gl.glShadeModel(GL2.GL_SMOOTH);                                 // blends colors nicely, and smoothes out lighting

        gl.glEnable(GL2.GL_CULL_FACE);
        gl.glEnable(GL2.GL_ALPHA_TEST);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        // включаем систему освещения
        gl.glEnable(GL2.GL_LIGHTING);

        // включаем применение цветов вершин как цвета материала.
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);


        gl.glEnable(GL2.GL_BLEND);
        //gl.glFrontFace(GL2.GL_CCW);
        //gl.glCullFace(GL2.GL_BACK);
        //gl.glEnable(GL2.GL_LIGHT0);
        //gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_NORMALIZE);
    }

    private void initLight(GL2 gl){

        float[] noAmbient =
                { 0.1f, 0.1f, 0.1f, 1f }; // low ambient light
        float[] spec =
                { 1f, 0.6f, 0f, 1f }; // low ambient light
        float[] diffuse =
                { 0f, 0f, 0f, 1f };
        float[] lightPos =
                {50005, 30000, 50000, 1};
        // properties of the light
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, noAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, spec, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);


        /*
        float[] mat_specular = { 1, 1, 1, 1 };
        float[] mat_shininess = { 50.f };
        float[] light_position = { 1, 1, 1, 0 };
        gl.glClearColor(0, 0, 0, 0);
        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, FloatBuffer.wrap(mat_specular));
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, FloatBuffer.wrap(mat_shininess));
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, FloatBuffer.wrap(light_position));

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        */
    }
}
