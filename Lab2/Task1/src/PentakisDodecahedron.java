import com.jogamp.opengl.GL2;
import com.sun.javafx.geom.Vec3f;

class Vec3i{

    public int x;
    public int y;
    public int z;

    Vec3i(int x, int y, int z){

        this.x = x;
        this.y = y;
        this.z = z;
    }
};

public class PentakisDodecahedron {

    private final static float C0 = 3 * ((float)Math.sqrt(5) - 1) / 4;
    private final static float C1 =  9 * (9 + (float)Math.sqrt(5)) / 76;
    private final static float C2 = 9 * (7 + 5 * (float)Math.sqrt(5)) / 76;
    private final static float C3 = 3 * (1 + (float)Math.sqrt(5)) / 4;

    private final static Vec3f[] DODECAHEDRON_VERTICIES = {

            new Vec3f(0,   C0,   C3),
            new Vec3f(0,   C0,  -C3),
            new Vec3f(0,  -C0,   C3),
            new Vec3f(0,  -C0,   -C3),
            new Vec3f( C3,  0,   C0),
            new Vec3f( C3,  0,  -C0),
            new Vec3f(-C3,  0,   C0),
            new Vec3f(-C3,  0,  -C0),
            new Vec3f( C0,   C3,  0),
            new Vec3f( C0,  -C3,  0),
            new Vec3f(-C0,   C3,  0),
            new Vec3f(-C0,  -C3,  0),
            new Vec3f( C1,  0,   C2),
            new Vec3f( C1,  0,  -C2),
            new Vec3f(-C1,  0,   C2),
            new Vec3f(-C1,  0,  -C2),
            new Vec3f( C2,   C1,  0),
            new Vec3f( C2,  -C1,  0),
            new Vec3f(-C2,   C1,  0),
            new Vec3f(-C2,  -C1,  0),
            new Vec3f(0,   C2,   C1),
            new Vec3f(0,   C2,  -C1),
            new Vec3f(0,  -C2,   C1),
            new Vec3f(0,  -C2,  -C1),
            new Vec3f( 1.5f,  1.5f,  1.5f),
            new Vec3f( 1.5f,  1.5f, -1.5f),
            new Vec3f( 1.5f, -1.5f,  1.5f),
            new Vec3f( 1.5f, -1.5f, -1.5f),
            new Vec3f(-1.5f,  1.5f,  1.5f),
            new Vec3f(-1.5f,  1.5f, -1.5f),
            new Vec3f(-1.5f, -1.5f,  1.5f),
            new Vec3f(-1.5f, -1.5f, -1.5f)
    };

    private final static Vec3i[] DODECAHEDRON_FACES = {

            new Vec3i(12,  0,  2),
            new Vec3i(12,  2, 26),
            new Vec3i(12, 26,  4),
            new Vec3i(12,  4, 24),
            new Vec3i(12, 24,  0),
            new Vec3i(13,  3,  1),
            new Vec3i(13,  1, 25),
            new Vec3i(13, 25,  5),
            new Vec3i(13,  5, 27),
            new Vec3i(13, 27,  3),
            new Vec3i(14,  2,  0),
            new Vec3i(14,  0, 28),
            new Vec3i(14, 28,  6),
            new Vec3i(14,  6, 30),
            new Vec3i(14, 30,  2),
            new Vec3i(15,  1,  3),
            new Vec3i(15,  3, 31),
            new Vec3i(15, 31,  7),
            new Vec3i(15,  7, 29),
            new Vec3i(15, 29,  1),
            new Vec3i(16,  4,  5),
            new Vec3i(16,  5, 25),
            new Vec3i(16, 25,  8),
            new Vec3i(16,  8, 24),
            new Vec3i(16, 24,  4),
            new Vec3i(17,  5,  4),
            new Vec3i(17,  4, 26),
            new Vec3i(17, 26,  9),
            new Vec3i(17,  9, 27),
            new Vec3i(17, 27,  5),
            new Vec3i(18,  7,  6),
            new Vec3i(18,  6, 28),
            new Vec3i(18, 28, 10),
            new Vec3i(18, 10, 29),
            new Vec3i(18, 29,  7),
            new Vec3i(19,  6,  7),
            new Vec3i(19,  7, 31),
            new Vec3i(19, 31, 11),
            new Vec3i(19, 11, 30),
            new Vec3i(19, 30,  6),
            new Vec3i(20,  8, 10),
            new Vec3i(20, 10, 28),
            new Vec3i(20, 28,  0),
            new Vec3i(20,  0, 24),
            new Vec3i(20, 24,  8),
            new Vec3i(21, 10,  8),
            new Vec3i(21,  8, 25),
            new Vec3i(21, 25,  1),
            new Vec3i(21,  1, 29),
            new Vec3i(21, 29, 10),
            new Vec3i(22, 11,  9),
            new Vec3i(22,  9, 26),
            new Vec3i(22, 26,  2),
            new Vec3i(22,  2, 30),
            new Vec3i(22, 30, 11),
            new Vec3i(23,  9, 11),
            new Vec3i(23, 11, 31),
            new Vec3i(23, 31,  3),
            new Vec3i(23,  3, 27),
            new Vec3i(23, 27,  9)

    };

    private Vec3f normalize(Vec3f v) {
        float length_of_v = (float)Math.sqrt((v.x * v.x) + (v.y * v.y) + (v.z * v.z));
        return new Vec3f(v.x / length_of_v, v.y / length_of_v, v.z / length_of_v);
    }

    private Vec3f cross(Vec3f v1, Vec3f v2) {
        return new Vec3f(
                (v1.y * v2.z) - (v1.z * v2.y),
                (v1.z * v2.x) - (v1.x * v2.z),
                (v1.x * v2.y) - (v1.y * v2.x)
        );
    }

    private Vec3f difference(Vec3f v1, Vec3f v2){

        return new Vec3f(
                v1.x - v2.x,
                v1.y - v2.y,
                v1.z - v2.z
        );
    }

    private void outputFaces(GL2 gl){

        gl.glBegin(GL2.GL_TRIANGLES);

        for (Vec3i i : DODECAHEDRON_FACES) {

            final Vec3f vec1 = DODECAHEDRON_VERTICIES[i.x];
            final Vec3f vec2 = DODECAHEDRON_VERTICIES[i.y];
            final Vec3f vec3 = DODECAHEDRON_VERTICIES[i.z];

            Vec3f normal = normalize(cross(difference(vec2, vec1), difference(vec3, vec1)));

            gl.glColor4f(0, 1, 0, 0.5f);
            gl.glNormal3f(normal.x, normal.y, normal.z);
            gl.glVertex3f(vec1.x, vec1.y, vec1.z);
            gl.glVertex3f(vec2.x, vec2.y, vec2.z);
            gl.glVertex3f(vec3.x, vec3.y, vec3.z);
        }

        gl.glEnd();
    }

    public void drawDodecahedron(GL2 gl){

        gl.glFrontFace(GL2.GL_CW);
        outputFaces(gl);
        gl.glFrontFace(GL2.GL_CCW);
        outputFaces(gl);
    };


}