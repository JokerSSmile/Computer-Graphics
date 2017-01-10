package engine.entity;

import engine.Scene;
import engine.graph.Material;
import engine.graph.Mesh;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class DeathStar extends GameItem {

	private static final Vector2i SIZE = new Vector2i(0, 0);
	private static final Vector3f POSITION = new Vector3f(-200, -200, -400);

	public DeathStar(Mesh mesh){
		super(mesh);
		Material darkMaterial = new Material(new Vector3f(0.1f, 0.1f, 0.1f), 1);
		this.mesh.setMaterial(darkMaterial);
		position = new Vector3f(POSITION);
		size = new Vector2i(SIZE);
		setScale(3);
		setRotation(30, 20, 0);
	}

	@Override
	public void update(float interval, Scene scene) {

	}
}
