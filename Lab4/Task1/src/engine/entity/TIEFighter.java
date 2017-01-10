package engine.entity;

import engine.Scene;
import engine.Window;
import engine.graph.Mesh;
import org.joml.Vector2i;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class TIEFighter extends GameItem {

	private static final int PLAYER_POSITION_LIMIT = 100;
	private static final float TIME_BETWEEN_SHOOTS = 1.5f;
	private static final float MOVE_SPEED = 1;
	private static final int START_HEALTH = 3;
	private static final Vector2i SIZE = new Vector2i(20, 20);

	private float timeSinceLastShoot;
	private boolean isShoot;
	private int health;

	public TIEFighter(Mesh mesh) {
		super(mesh);
		size = new Vector2i(SIZE);
		health = START_HEALTH;
	}

	@Override
	public void update(float interval, Scene scene) {

		timeSinceLastShoot += interval;

		if (timeSinceLastShoot > TIME_BETWEEN_SHOOTS){
			timeSinceLastShoot = 0;
			isShoot = true;
		}
	}

	@Override
	public void hit(){
		health -= 1;

		if (health == 0){
			isAlive = false;
		}
	}

	public void input(Window window, Scene scene){

		if (window.isKeyPressed(GLFW_KEY_LEFT)) {
			if (getPosition().x > -PLAYER_POSITION_LIMIT) {

				setPosition(getPosition().x - MOVE_SPEED, getPosition().y, getPosition().z);
			}
			else {
				setPosition(-PLAYER_POSITION_LIMIT, getPosition().y, getPosition().z);
			}
		} else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {

			if (getPosition().x < PLAYER_POSITION_LIMIT) {
				setPosition(getPosition().x + MOVE_SPEED, getPosition().y, getPosition().z);
			}
			else {
				setPosition(PLAYER_POSITION_LIMIT, getPosition().y, getPosition().z);
			}
		}
		if (window.isKeyPressed(GLFW_KEY_SPACE)){

			if (isShoot){
				Vector3f bulletPos = new Vector3f(getPosition().x, getPosition().y, getPosition().z - 6);
				Bullet bullet = new Bullet(bulletPos, true);
				scene.addGameItem(bullet);
				isShoot = false;
			}
		}
	}
}
