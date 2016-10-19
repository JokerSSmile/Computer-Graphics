
public class BasicFrame {

    private final static int WINDOW_HEIGHT = 610;
    private final static int WINDOW_WIDTH = 570;

    public static void main(String[] args) {

        Frame frame = new Frame(WINDOW_WIDTH, WINDOW_HEIGHT, "Smeshariki");
        while (frame.isAlive) {

            frame.repaint();
        }
    }
}