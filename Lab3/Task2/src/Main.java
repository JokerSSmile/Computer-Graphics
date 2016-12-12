import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    private static String TITLE = "JOGL 2.0 Setup (GLCanvas)";   // window's title
    private static final int CANVAS_WIDTH = 800;                 // width of the drawable
    private static final int CANVAS_HEIGHT = 600;                // height of the drawable
    private static final int FPS = 60;                            // animator's target frames per second

    public static void main(String argc[]) {
        // Run the GUI codes in the event-dispatching thread for thread safety
        SwingUtilities.invokeLater(() -> {
			// Create the OpenGL rendering canvas
			Canvas canvas = new Canvas();
			canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

			// Create a animator that drives canvas' display() at the specified FPS.
			final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

			// Create the top-level container
			final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
			frame.getContentPane().add(canvas);
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					//Use a dedicate thread to run the stop() to ensure that the
					//animator stops before program exits.
					new Thread(() -> {
						if (animator.isStarted()) {
							animator.stop();
						}
						System.exit(0);
					}).start();
				}
			});
			frame.setTitle(TITLE);
			frame.pack();
			frame.setVisible(true);
			animator.start();
		});
    }
}
