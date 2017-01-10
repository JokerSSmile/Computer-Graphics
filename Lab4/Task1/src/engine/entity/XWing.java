package engine.entity;

import engine.Scene;
import engine.Window;
import engine.graph.Mesh;
import javafx.geometry.Rectangle2D;
import org.joml.Vector2i;

import java.util.List;

public class XWing extends GameItem {

	private static final float ONE_SIDE_MOVE_TIME = 4;

	private float oneSideMoveTime;

	public XWing(Mesh mesh) {
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
	}
}
