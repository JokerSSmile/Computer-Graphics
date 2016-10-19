import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseControl implements MouseMotionListener, MouseListener {

    private int startX;
    private int startY;
    private int deltaX;
    private int deltaY;
    private int oldX;
    private int oldY;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        startX = e.getX() - oldX;
        startY = e.getY() - oldY;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        oldX = deltaX;
        oldY = deltaY;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        deltaX = e.getX() - startX;
        deltaY = e.getY() - startY;

        System.out.println(deltaX);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public int getDeltaX() {

        return deltaX;
    }

    public int getDeltaY() {

        return deltaY;
    }
}
