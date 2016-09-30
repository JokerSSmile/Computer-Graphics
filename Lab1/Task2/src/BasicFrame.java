import com.jogamp.opengl.*;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import javax.swing.JFrame;
import java.util.Vector;


public class BasicFrame implements GLEventListener{

    private CoordinateParser coordinateParser;

    Vector<Vector<Double>> coordinates;

    @Override
    public void display(GLAutoDrawable drawable) {

        coordinateParser.Draw(drawable, coordinates);
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body
    }

    @Override
    public void init(GLAutoDrawable arg0) {
        coordinateParser = new CoordinateParser();

        coordinates = coordinateParser.ReadData(190, "src/dots.txt");

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y,
                        int width, int height) {


    }

    public static void main(String[] args) {

        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        BasicFrame b = new BasicFrame();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(800, 600);

        //creating frame
        final JFrame frame = new JFrame ("Smeshariki");

        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }
}