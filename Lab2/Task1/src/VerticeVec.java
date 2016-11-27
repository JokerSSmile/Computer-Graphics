import com.sun.javafx.geom.Vec3f;
import com.sun.javafx.geom.Vec4f;

class VerticeVec {

    int x;
    int y;
    int z;
    int w;
    Vec4f color;

    VerticeVec(int x, int y, int z, int w, Vec4f color){

        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.color = color;
    }

    static Vec3f normalize(Vec3f v) {
        float length_of_v = (float)Math.sqrt((v.x * v.x) + (v.y * v.y) + (v.z * v.z));
        return new Vec3f(v.x / length_of_v, v.y / length_of_v, v.z / length_of_v);
    }

    static Vec3f cross(Vec3f v1, Vec3f v2) {
        return new Vec3f(
                (v1.y * v2.z) - (v1.z * v2.y),
                (v1.z * v2.x) - (v1.x * v2.z),
                (v1.x * v2.y) - (v1.y * v2.x)
        );
    }

    static Vec3f difference(Vec3f v1, Vec3f v2){

        return new Vec3f(
                v1.x - v2.x,
                v1.y - v2.y,
                v1.z - v2.z
        );
    }
}

