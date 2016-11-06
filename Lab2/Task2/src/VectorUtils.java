import com.sun.javafx.geom.Vec3f;

class SVertexP3N{

    Vec3f position;
    Vec3f normal;

    SVertexP3N(){};

    SVertexP3N(Vec3f position){

        this.position = position;
    }
}

class MathVec3f {

    static Vec3f normalize(Vec3f v) {
        float length_of_v = (float) Math.sqrt((v.x * v.x) + (v.y * v.y) + (v.z * v.z));
        return new Vec3f(v.x / length_of_v, v.y / length_of_v, v.z / length_of_v);
    }

    static Vec3f cross(Vec3f v1, Vec3f v2) {
        return new Vec3f(
                (v1.y * v2.z) - (v1.z * v2.y),
                (v1.z * v2.x) - (v1.x * v2.z),
                (v1.x * v2.y) - (v1.y * v2.x)
        );
    }
}