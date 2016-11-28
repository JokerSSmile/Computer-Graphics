import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class TwistValueController implements KeyListener {

	private static final float MIN_TWIST = 0f;
	private static final float MAX_TWIST  = 2 * (float)Math.PI;
	private static final float NEXT_TWIST_STEP  = (float)Math.PI / 100;
	private static final float TWIST_CHANGE_SPEED   = 0.5f;

	private float currentTwistValue = 0;
	private float nextTwistValue = 0;

	@Override
	public void keyTyped(java.awt.event.KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

		if (keyEvent.getKeyChar() == '+' || keyEvent.getKeyChar() == '='){
			nextTwistValue = Math.min(nextTwistValue + NEXT_TWIST_STEP, MAX_TWIST);
		}
		if (keyEvent.getKeyChar() == '-' || keyEvent.getKeyChar() == '_'){
			nextTwistValue = Math.max(nextTwistValue - NEXT_TWIST_STEP, MIN_TWIST);
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {

	}

	void update(){

		final float twistDiff = Math.abs(nextTwistValue - currentTwistValue);
		if (twistDiff > Float.MIN_VALUE)
		{
			final float sign = (nextTwistValue > currentTwistValue) ? 1 : -1;
			final float growth = TWIST_CHANGE_SPEED;

			if (growth > twistDiff)
			{
				currentTwistValue = nextTwistValue;
			}
			else
			{
				currentTwistValue += sign * growth;
			}
		}
	}

	final float getCurrentValue(){

		return currentTwistValue;
	}

}
