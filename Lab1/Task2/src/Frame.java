import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.util.Vector;

public class Frame implements GLEventListener {

    private GLCanvas glcanvas;
    private JFrame frame;
    private GLU glu;
    private MyMouseListener mouseListener;
    private int wWidth;
    private int wHeight;
    public boolean isAlive;

    private CoordinateParser coordinateParser;

    private Vector<Vector<Double>> body;
    private Vector<Vector<Double>> leftArm;
    private Vector<Vector<Double>> rightArm;
    private Vector<Vector<Double>> rightEar;
    private Vector<Vector<Double>> leftEar;
    private Vector<Vector<Double>> rightLeg;
    private Vector<Vector<Double>> leftLeg;
    private Vector<Vector<Double>> eyes;
    private Vector<Vector<Double>> teeth;
    private Vector<Vector<Double>> mouth;
    private Vector<Vector<Double>> rightBrow;
    private Vector<Vector<Double>> leftBrow;
    private Vector<Vector<Double>> leftPupil;
    private Vector<Vector<Double>> rightPupil;
    private Vector<Vector<Double>> nose;

    public Frame(int windowWidth, int windowHeight, String title)
    {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        mouseListener = new MyMouseListener();
        glu = new GLU();
        glcanvas = new GLCanvas(capabilities);
        isAlive = true;

        glcanvas.addGLEventListener(this);
        glcanvas.setSize(windowWidth, windowHeight);
        glcanvas.addMouseMotionListener(mouseListener);
        glcanvas.addMouseListener(mouseListener);
        glcanvas.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);

        frame = new JFrame (title);
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE );
        frame.setVisible(true);

    }

    @Override
    public void init(GLAutoDrawable drawable) {

        coordinateParser = new CoordinateParser();

        body = coordinateParser.ReadData("src/dots/body.txt");
        leftArm = coordinateParser.ReadData("src/dots/leftArm.txt");
        rightArm = coordinateParser.ReadData("src/dots/rightArm.txt");
        rightEar = coordinateParser.ReadData("src/dots/rightEar.txt");
        leftEar = coordinateParser.ReadData("src/dots/leftEar.txt");
        rightLeg = coordinateParser.ReadData("src/dots/rightLeg.txt");
        leftLeg = coordinateParser.ReadData("src/dots/leftLeg.txt");
        eyes = coordinateParser.ReadData("src/dots/eyes.txt");
        teeth = coordinateParser.ReadData("src/dots/teeth.txt");
        mouth = coordinateParser.ReadData("src/dots/mouth.txt");
        rightBrow = coordinateParser.ReadData("src/dots/rightBrow.txt");
        leftBrow = coordinateParser.ReadData("src/dots/leftBrow.txt");
        leftPupil = coordinateParser.ReadData("src/dots/leftPupil.txt");
        rightPupil = coordinateParser.ReadData("src/dots/rightPupil.txt");
        nose = coordinateParser.ReadData("src/dots/nose.txt");
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

        isAlive = false;
        glcanvas.disposeGLEventListener(this, true);
        frame.dispose();
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl2 = drawable.getGL().getGL2();

        gl2.glClearColor(0, 0, 0, 0);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
        coordinateParser.Draw(gl2, body, new ColorVec(0.28f, 0.85f, 0.93f));
        coordinateParser.Draw(gl2, leftArm, new ColorVec(0.28f, 0.85f, 0.93f));
        coordinateParser.Draw(gl2, rightArm, new ColorVec(0.28f, 0.85f, 0.93f));
        coordinateParser.Draw(gl2, rightEar, new ColorVec(0.28f, 0.85f, 0.93f));
        coordinateParser.Draw(gl2, leftEar, new ColorVec(0.28f, 0.85f, 0.93f));
        coordinateParser.Draw(gl2, rightLeg, new ColorVec(0.28f, 0.85f, 0.93f));
        coordinateParser.Draw(gl2, leftLeg, new ColorVec(0.28f, 0.85f, 0.93f));
        coordinateParser.Draw(gl2, eyes, new ColorVec(1, 1, 1));
        coordinateParser.Draw(gl2, teeth, new ColorVec(1, 1, 1));
        coordinateParser.Draw(gl2, leftBrow, new ColorVec(0.07f, 0.4f, 0.7f));
        coordinateParser.Draw(gl2, rightBrow, new ColorVec(0.07f, 0.4f, 0.7f));
        coordinateParser.Draw(gl2, leftPupil, new ColorVec(0, 0, 0));
        coordinateParser.Draw(gl2, rightPupil, new ColorVec(0, 0, 0));
        coordinateParser.Draw(gl2, nose, new ColorVec(0.6f, 0, 0));
        coordinateParser.DrawLines(gl2, mouth);

        DragNDrop(gl2);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        wWidth = width;
        wHeight = height;
    }

    private void DragNDrop(GL2 gl2){

        gl2.glMatrixMode( GL2.GL_PROJECTION );
        gl2.glLoadIdentity();
        glu.gluOrtho2D( -wWidth/2, wWidth/2, wHeight/2, -wHeight/2);
        gl2.glMatrixMode( GL2.GL_MODELVIEW );
        gl2.glLoadIdentity();
        gl2.glTranslatef(mouseListener.getDeltaX(), mouseListener.getDeltaY(), 0);
        gl2.glViewport( 0, 0, wWidth, wHeight );
        gl2.glFlush();
    }

    public void repaint() {

        glcanvas.repaint();
    }
}
