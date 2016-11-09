import com.jogamp.common.util.Function;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.sun.javafx.geom.Vec2f;
import com.sun.javafx.geom.Vec3f;

@SuppressWarnings("serial")
public class Canvas extends GLCanvas implements GLEventListener {

    // Setup OpenGL Graphics Renderer
    private GLU glu;                                                    // for the GL Utility
    private MouseControl mouseListener;
    //private DottedMoebiusStrip dottedMoebiusStrip;
    private SolidMoebiusStrip solidMoebiusStrip;

    /** Constructor to setup the GUI for this Component */
    Canvas() {
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
        //dottedMoebiusStrip = new DottedMoebiusStrip(getMoebiusPoint);
        solidMoebiusStrip = new SolidMoebiusStrip(getMoebiusPoint);
        mouseListener = new MouseControl();
        this.addMouseMotionListener(mouseListener);
        this.addMouseListener(mouseListener);

        initLight(gl);
        initGLContext(gl);

        //dottedMoebiusStrip.tesselate(new Vec2f(-10.f, 10.f), new Vec2f(-10.f, 10.f), 0.5f);
        solidMoebiusStrip.tesselate(new Vec2f(-10.f, 10.f), new Vec2f(-10.f, 10.f), 0.5f);
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

        final float FIELD_OF_VIEW = 35.f;
        final float ASPECT = (float)width / height;
        final float Z_NEAR = 0.1f;
        final float Z_FAR = 100.f;

        // Set the view port (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL2.GL_PROJECTION);                     // choose projection matrix
        gl.glLoadIdentity();                                    // reset projection matrix
        glu.gluPerspective(FIELD_OF_VIEW, ASPECT, Z_NEAR, Z_FAR);           // fovy, aspect, zNear, zFar

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

        // ----- OpenGL rendering code -----
        gl.glTranslatef(0.0f, 0.0f, -10.0f);                            // translate into the screen
        gl.glRotatef(mouseListener.getDeltaX(), 0.0f, 1.0f, 0.0f);
        gl.glRotatef(mouseListener.getDeltaY(), 1.0f, 0.0f, 0.0f);


        // ----- Drawing shape -----
        //dottedMoebiusStrip.draw(drawable);
        solidMoebiusStrip.draw(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) { }

    private void initGLContext(GL2 gl){

        gl.glClearColor(0f, 0f, 0f, 0f);                                    // set background (clear) color to black
        gl.glClearDepth(1f);                                                // set clear depth value to farthest
        gl.glEnable(GL2.GL_DEPTH_TEST);                                     // enables depth testing

        gl.glEnable(GL2.GL_CCW);
        gl.glEnable(GL2.GL_BACK);
        gl.glEnable(GL2.GL_CULL_FACE);
    }

    private void initLight(GL2 gl){

        //gl.glEnable(GL2.GL_LIGHT0);
        //gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_LIGHTING);

        // включаем применение цветов вершин как цвета материала.
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);
        gl.glLightModeli( GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE );

        final float[] AMBIENT = { 0.1f, 0.1f, 0.1f, 0.1f }; //0.1 * white
        final float[] DIFFUSE = { 1, 1, 1, 1 };             //white
        final float[] SPECULAR = { 1, 1, 1, 1 };            //white
        final float[] LIGHT_POSITION = {-1.f, 0.2f, 0.7f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, AMBIENT, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, DIFFUSE, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, SPECULAR, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, LIGHT_POSITION, 0);
    }

     private Vec3f getPoint(float u, float v){

         //float x = (1 + v / 2 * (float)Math.cos(u / 2)) * (float)Math.cos(u);
         //float y = (1 + v / 2 * (float)Math.cos(u / 2)) * (float)Math.sin(u);
         //float z = v / 2 * (float)Math.sin(u / 2);

         final float r = 1.5f;

         float x;
         float y;
         float z;

         if (u >= 0 && u <= Math.PI) {
             x = 6 * (float) Math.cos(u) * (1 + (float) Math.sin(u)) + 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.cos(u) * (float) Math.cos(v);
             y = 16 * (float) Math.sin(u) + 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.sin(u) * (float) Math.cos(v);
             z = 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.sin(v);
         }
         else{

             x = 6 * (float) Math.cos(u) * (1 + (float) Math.sin(u)) - 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.cos(v);
             y = 16 * (float) Math.sin(u);
             z = 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.sin(v);
         }

         return new Vec3f(x / 10, z / 10, y / 10);
    }

    private Function<Vec3f, Float> getMoebiusPoint = new Function<Vec3f, Float>() {

        @Override
        public Vec3f eval(Float... floats) {

            return getPoint(floats[0], floats[1]);
        }
    };

}
