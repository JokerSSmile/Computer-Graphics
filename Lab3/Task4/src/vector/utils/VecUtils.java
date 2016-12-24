package vector.utils;
import com.sun.javafx.geom.Vec2f;

public class VecUtils {

	public static Vec2f sum(Vec2f v1, Vec2f v2){

		return new Vec2f(v1.x + v2.x, v1.y + v2.y);
	}
}