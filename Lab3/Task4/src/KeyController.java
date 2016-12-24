import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {

	private final static float changeRate = 1f;
	private final static float changeIterationsRate = 1f;
	private final static float scale = 0.1f
	//private static final Vec2f size = new Vec2f(5, 5);
	//private static final Vec2f position = new Vec2f(-2, -2);
	//private static final int iterations = 64;

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
			//if (values.height >= changeRate) {
				values.height -= changeRate / 10;
				values.width -= changeRate/ 10;
				values.iterations += changeIterationsRate;
			//}
		}
		else if (isZoomOut) {
			values.height += changeRate/ 10;
			values.width += changeRate/ 10;
			values.iterations -= changeIterationsRate;
		}

		printInfo(values);
	}

	private void printInfo(UniformValues values){

		System.out.println("start---------------------------------");
		System.out.println("x " + values.x + " y " + values.y);
		System.out.println("w " + values.width + " h " + values.height);
		System.out.println("i " + values.iterations);
		//System.out.println("end-----------------------------------");
	}
}
