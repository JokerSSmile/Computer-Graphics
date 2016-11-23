import com.sun.javafx.geom.Vec3f;
import com.sun.javafx.geom.Vec4f;
import com.sun.prism.impl.BufferUtil;

import java.nio.FloatBuffer;

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

    static Vec3f divideByInt(Vec3f v, int divider){

        return new Vec3f(v.x / divider, v.y / divider, v.z / divider);
    }
}

class MathVec4f {

    static FloatBuffer toBuffer(Vec4f v){

        FloatBuffer buffer = BufferUtil.newFloatBuffer(4);
        buffer.put(v.x);
        buffer.put(v.y);
        buffer.put(v.z);
        buffer.put(v.w);

        return buffer;
    }
}