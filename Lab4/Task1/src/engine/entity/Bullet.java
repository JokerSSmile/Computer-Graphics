package engine.entity;

import engine.Scene;
import engine.graph.Material;
import engine.graph.Mesh;
import engine.graph.OBJLoader;
import engine.graph.Texture;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.List;

public class Bullet extends GameItem{

	private static final int PLAYER_BULLET_SPEED = 40;
	private static final int ENEMY_BULLET_SPEED = 20;
	private static final int RANGE_OF_FLIGHT = 500;

	private boolean isPlayers;
	private float distance;
	private static Texture textureRed;
	private static Texture textureWhite;

	Bullet(Vector3f position, boolean isPlayers){
		Mesh bulletMesh = null;
		try {
			bulletMesh = OBJLoader.loadMesh("src/resources/models/LightSaber.obj");
			textureRed = new Texture("src/resources/textures/lightsaberRed.png");
			textureWhite = new Texture("src/resources/textures/lightsaberLight.png");
			if (isPlayers){
				bulletMesh.setMaterial(new Material(textureRed));
			} else {
				bulletMesh.setMaterial(new Material(textureWhite));
			}

		} catch (Exception e) {
			System.out.println("Error loading bullet mesh");
		}
		setMesh(bulletMesh);
		this.position = new Vector3f(position.x, position.y, position.z);
		this.isPlayers = isPlayers;
		size = new Vector2i(1, 20);
	}

	public boolean isPlayers(){
		return isPlayers;
	}

	@Override
	public void update(float interval, Scene scene) {

		if (isPlayers) {
			setPosition(this.getPosition().x, this.getPosition().y, this.getPosition().z - PLAYER_BULLET_SPEED);
		} else {
			setPosition(this.getPosition().x, this.getPosition().y, this.getPosition().z + ENEMY_BULLET_SPEED);
		}
		distance += ENEMY_BULLET_SPEED;

		if (distance > RANGE_OF_FLIGHT){
			isAlive = false;
		}
	}
}
