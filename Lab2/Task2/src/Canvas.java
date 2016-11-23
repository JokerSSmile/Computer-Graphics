import com.jogamp.common.util.Function;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.sun.javafx.geom.Vec2f;
import com.sun.javafx.geom.Vec3f;
import com.sun.javafx.geom.Vec4f;

@SuppressWarnings("serial")
public class Canvas extends GLCanvas implements GLEventListener {

    private GLU glu;                                                    // for the GL Utility
    private MouseControl mouseListener;
    private SolidMoebiusStrip solidMoebiusStrip;

    Canvas() {
        this.addGLEventListener(this);
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        glu = new GLU();

        //dottedKleinBottle = new DottedKleinBottle(getMoebiusPoint);
        solidMoebiusStrip = new SolidMoebiusStrip(getMoebiusPoint);
        mouseListener = new MouseControl();
        this.addMouseMotionListener(mouseListener);
        this.addMouseListener(mouseListener);

        initMaterial(gl);
        initLight(gl);
        initGLContext(gl);

        //solidMoebiusStrip.tesselate(new Vec2f(-10.f, 10.f), new Vec2f(-10.f, 10.f), 0.07f);
        solidMoebiusStrip.tesselate(new Vec2f(-3f * (float)Math.PI, 3f * (float)Math.PI), new Vec2f(-10.f, 10.f), 0.07f);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        GL2 gl = drawable.getGL().getGL2();

        if (height == 0){
            height = 1;
        }

        final float FIELD_OF_VIEW = 35.f;
        final float ASPECT = (float)width / height;
        final float Z_NEAR = 0.1f;
        final float Z_FAR = 100.f;

        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(FIELD_OF_VIEW, ASPECT, Z_NEAR, Z_FAR);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -10.0f);
        gl.glRotatef(mouseListener.getDeltaX(), 0.0f, 1.0f, 0.0f);
        gl.glRotatef(mouseListener.getDeltaY(), 1.0f, 0.0f, 0.0f);

        solidMoebiusStrip.draw(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) { }

    private void initGLContext(GL2 gl){

        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1f);
        gl.glEnable(GL2.GL_DEPTH_TEST);

        gl.glEnable(GL2.GL_CULL_FACE);
        gl.glFrontFace(GL2.GL_CCW);
        gl.glCullFace(GL2.GL_BACK);
    }

    private void initLight(GL2 gl){

        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        //gl.glShadeModel(GL2.GL_SMOOTH);
        //gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        //gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);
        //gl.glLightModeli( GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE );

        //gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_FALSE);
        //gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, GL2.GL_FALSE);

        final float[] AMBIENT = { 0.1f, 0.1f, 0.1f, 0.1f }; //0.1 * white
        final float[] DIFFUSE = { 1, 1, 1, 1 };             //white
        final float[] SPECULAR = { 1, 1, 1, 1 };            //white
        final float[] LIGHT_POSITION = {-1.f, 0.2f, 0.7f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, LIGHT_POSITION, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, DIFFUSE, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, AMBIENT, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, SPECULAR, 0);
    }

    private void initMaterial(GL2 gl){

        //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, MathVec4f.toBuffer(new Vec4f(1, 1, 0, 1)));
        //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, MathVec4f.toBuffer(new Vec4f(1, 1, 0, 1)));
        //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, MathVec4f.toBuffer(new Vec4f(0.3f, 0.3f, 0.3f, 1.f)));
        //gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 30f);
    }

     private Vec3f getPoint(float u, float v){

         final float r = 1f;

         Vec3f point = new Vec3f();

         if (u >= 0 && u <= Math.PI) {
             point.x = 6 * (float) Math.cos(u) * (1 + (float) Math.sin(u)) + 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.cos(u) * (float) Math.cos(v);
             point.y = 16 * (float) Math.sin(u) + 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.sin(u) * (float) Math.cos(v);
             point.z = 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.sin(v);
         }
         else{

             point.x = 6 * (float) Math.cos(u) * (1 + (float) Math.sin(u)) - 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.cos(v);
             point.y = 16 * (float) Math.sin(u);
             point.z = 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.sin(v);
         }

         return MathVec3f.divideByInt(point, 10);
    }

    private Function<Vec3f, Float> getMoebiusPoint = new Function<Vec3f, Float>() {

        @Override
        public Vec3f eval(Float... floats) {

            return getPoint(floats[0], floats[1]);
        }
    };

}
