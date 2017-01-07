import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {

	private final static float changeRate = 0.05f;
	private final static float zoomChangeRate = 1.2f;
	private final static float changeIterationsRate = 5f;

	private boolean isUp;
	private boolean isDown;
	private boolean isLeft;
	private boolean isRight;
	private boolean isZoomIn;
	private boolean isZoomOut;

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			isDown = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN){
			isUp = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			isLeft = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			isRight = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			isZoomIn = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			isZoomOut = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		isUp = false;
		isDown = false;
		isLeft = false;
		isRight = false;
		isZoomIn = false;
		isZoomOut = false;
	}

	void validateValues(UniformValues values) {

		if (isUp){

			values.y += changeRate * values.height;
		}
		else if (isDown){

			values.y -= changeRate * values.height;
		}
		else if (isLeft){

			values.x -= changeRate * values.height;
		}
		else if (isRight){

			values.x += changeRate * values.height;
		}
		else if (isZoomIn){
			values.height /= zoomChangeRate;
			values.width /= zoomChangeRate;
			values.iterations += changeIterationsRate;
		}
		else if (isZoomOut && values.height < 5) {
			values.height *= zoomChangeRate;
			values.width *= zoomChangeRate;
			values.iterations -= changeIterationsRate;
		}

		printInfo(values);
	}

	private void printInfo(UniformValues values){

		System.out.println("start---------------------------------");
		System.out.println("x " + values.x + " y " + values.y);
		System.out.println("w " + values.width + " h " + values.height);
		System.out.println("i " + values.iterations);
	}
}
