import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.JFrame;


public class BasicFrame implements GLEventListener{

    public final int SCALE = 35;

    private CoordinateSystem coordinateSystem;
    private Graph graph;

    @Override
    public void display(GLAutoDrawable drawable) {

        coordinateSystem.display(drawable);
        graph.display(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body
    }

    @Override
    public void init(GLAutoDrawable arg0) {
        coordinateSystem = new CoordinateSystem();
        graph = new Graph();
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
        glcanvas.setSize(800, 800);

        //creating frame
        final JFrame frame = new JFrame ("Graph");

        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }
}