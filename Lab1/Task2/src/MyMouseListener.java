import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListener implements MouseListener {

    public int x;
    public int y;
    public int startX;
    public int startY;
    public int deltaX;
    public int deltaY;


    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("CLICKED");

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("PRESSED");

        startX = e.getX();
        startY = e.getY();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        //System.out.println("RELEASED");

        deltaX = e.getX() - startX;
        deltaY = e.getY() - startY;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
