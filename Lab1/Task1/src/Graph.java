
import com.jogamp.opengl.*;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class Graph implements GLEventListener {

    private final int SCALE = 35;
    private final float STEP = 0.1f;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

        final GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glColor3f(0.5f,0.5f,0.5f); //red
        gl.glBegin(GL2.GL_LINE_STRIP);
        for (double angle = 0; angle <= 10 * Math.PI; angle += STEP) {
            gl.glVertex3d(angle / SCALE * Math.cos(angle), angle / SCALE * Math.sin(angle), 0);
        }
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }
}
