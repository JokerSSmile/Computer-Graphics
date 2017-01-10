package engine.entity;

import engine.Scene;
import engine.graph.Mesh;
import org.joml.Vector2i;

public class XWing extends GameItem {

	private static final float ONE_SIDE_MOVE_TIME = 4;
	private static final int ROTATION_ANGLE = 15;
	private static final float ROTATION_SPEED = 1f;
	private static final float MOVE_SPEED = 0.5f;

	private float oneSideMoveTime;

	public XWing(Mesh mesh) {
		super(mesh);
		size = new Vector2i(20, 20);
	}

	@Override
	public void update(float interval, Scene scene) {

		oneSideMoveTime += interval;
		if (oneSideMoveTime < ONE_SIDE_MOVE_TIME){
			setPosition(getPosition().x + MOVE_SPEED, getPosition().y, getPosition().z);
			if (getRotation().z < ROTATION_ANGLE){
				setRotation(0, 0, getRotation().z + ROTATION_SPEED);
			}
		}
		else if (oneSideMoveTime < ONE_SIDE_MOVE_TIME * 2){
			setPosition(getPosition().x - MOVE_SPEED, getPosition().y, getPosition().z);
			if (getRotation().z > -ROTATION_ANGLE){
				setRotation(0, 0, getRotation().z - ROTATION_SPEED);
			}
		}
		else {
			oneSideMoveTime = 0;
		}
	}
}
