import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class MouseControl implements MouseMotionListener, MouseListener {

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
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    int getDeltaX() {

        return deltaX;
    }

    int getDeltaY() {

        return deltaY;
    }
}
