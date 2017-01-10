package engine.entity;

import engine.Scene;
import engine.graph.Mesh;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.Random;

public class MillenuimFalcon extends GameItem {

	private static final float ONE_SIDE_MOVE_TIME = 4;
	private static final float SHOOT_CHANCE = 0.995f;

	private float oneSideMoveTime;

	public MillenuimFalcon(Mesh mesh) {
		super(mesh);
		size = new Vector2i(20, 20);
	}

	@Override
	public void update(float interval, Scene scene) {

		oneSideMoveTime += interval;
		if (oneSideMoveTime < ONE_SIDE_MOVE_TIME){
			setPosition(getPosition().x + 0.5f, getPosition().y, getPosition().z);
		}
		else if (oneSideMoveTime < ONE_SIDE_MOVE_TIME * 2){
			setPosition(getPosition().x - 0.5f, getPosition().y, getPosition().z);
		}
		else {
			oneSideMoveTime = 0;
		}

		Random r = new Random(System.nanoTime());
		float rand = r.nextFloat();

		if (rand > SHOOT_CHANCE){

			Vector3f bulletPos = new Vector3f(getPosition().x, getPosition().y, getPosition().z - 6);
			Bullet bullet = new Bullet(bulletPos, false);
			scene.addGameItem(bullet);


		}

	}
}
