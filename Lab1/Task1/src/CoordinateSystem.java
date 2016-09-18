import com.jogamp.opengl.*;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class CoordinateSystem implements GLEventListener{


    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

        final GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glColor3f(1f,1f,1f); //white
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(-1f, 0f, 0);
        gl.glVertex3f(1f, 0f, 0);
        gl.glVertex3f(0f, -1f, 0);
        gl.glVertex3f(0f, 1f, 0);

        for (float position = -1; position < 1; position+=0.125)
        {
            gl.glVertex3f(position, 0.02f, 0);
            gl.glVertex3f(position, -0.02f, 0);

            gl.glVertex3f(0.02f, position, 0);
            gl.glVertex3f(-0.02f, position, 0);
        }
        gl.glEnd();

        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3f(0f,1f,0.0f);
        gl.glVertex3f(-0.02f, 0.98f, 0.0f);
        gl.glVertex3f(0.02f, 0.98f, 0.0f);

        gl.glVertex3f(1f,0f,0.0f);
        gl.glVertex3f(0.98f, -0.02f, 0.0f);
        gl.glVertex3f(0.98f, 0.02f, 0.0f);
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
