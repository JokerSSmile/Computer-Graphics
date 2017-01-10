package engine.entity;

import engine.Scene;
import engine.graph.Material;
import engine.graph.Mesh;
import engine.graph.OBJLoader;
import engine.graph.Texture;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.List;

public class Bullet extends GameItem{

	private static final int SPEED = 40;
	private static final int RANGE_OF_FLIGHT = 500;

	private boolean isPlayers;
	private float distance;

	public Bullet(Vector3f position, boolean isPlayers){
		Mesh bulletMesh = null;
		try {
			bulletMesh = OBJLoader.loadMesh("src/resources/models/LightSaber1.obj");
			Texture textureRed = new Texture("src/resources/textures/lightsaber.png");

			Material bulletMaterial = new Material(textureRed);
			bulletMesh.setMaterial(bulletMaterial);

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
			setPosition(this.getPosition().x, this.getPosition().y, this.getPosition().z - SPEED);
		} else {
			setPosition(this.getPosition().x, this.getPosition().y, this.getPosition().z + SPEED);
		}

		distance += SPEED;

		if (distance > RANGE_OF_FLIGHT){
			isAlive = false;
		}
	}
}
