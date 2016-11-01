import com.jogamp.opengl.GL2;
import com.sun.javafx.geom.Vec3f;
import com.sun.javafx.geom.Vec4f;

class PentakisDodecahedron {

    private final static Vec4f RED = new Vec4f(1, 0, 0, 0.6f);
    private final static Vec4f GREEN = new Vec4f(0, 1, 0, 0.6f);
    private final static Vec4f BLUE = new Vec4f(0, 0, 1, 0.6f);
    private final static Vec4f YELLOW = new Vec4f(1, 1, 0, 0.6f);
    private final static Vec4f CYAN = new Vec4f(0, 1, 1, 0.6f);
    private final static Vec4f PURPLE = new Vec4f(1, 0, 1, 0.6f);

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

    private final static VerticeVec[] DODECAHEDRON_FACES = {

            new VerticeVec(12,  0,  2, RED),
            new VerticeVec(12,  2, 26, GREEN),
            new VerticeVec(12, 26,  4, CYAN),
            new VerticeVec(12,  4, 24, RED),
            new VerticeVec(12, 24,  0, YELLOW),
            new VerticeVec(13,  3,  1, BLUE),
            new VerticeVec(13,  1, 25, RED),
            new VerticeVec(13, 25,  5, GREEN),
            new VerticeVec(13,  5, 27, CYAN),
            new VerticeVec(13, 27,  3, RED),
            new VerticeVec(14,  2,  0, PURPLE),
            new VerticeVec(14,  0, 28, BLUE),
            new VerticeVec(14, 28,  6, RED),
            new VerticeVec(14,  6, 30, GREEN),
            new VerticeVec(14, 30,  2, CYAN),
            new VerticeVec(15,  1,  3, RED),
            new VerticeVec(15,  3, 31, YELLOW),
            new VerticeVec(15, 31,  7, BLUE),
            new VerticeVec(15,  7, 29, RED),
            new VerticeVec(15, 29,  1, GREEN),
            new VerticeVec(16,  4,  5, CYAN),
            new VerticeVec(16,  5, 25, RED),
            new VerticeVec(16, 25,  8, PURPLE),
            new VerticeVec(16,  8, 24, BLUE),
            new VerticeVec(16, 24,  4, RED),
            new VerticeVec(17,  5,  4, GREEN),
            new VerticeVec(17,  4, 26, CYAN),
            new VerticeVec(17, 26,  9, RED),
            new VerticeVec(17,  9, 27, YELLOW),
            new VerticeVec(17, 27,  5, BLUE),
            new VerticeVec(18,  7,  6, RED),
            new VerticeVec(18,  6, 28, GREEN),
            new VerticeVec(18, 28, 10, CYAN),
            new VerticeVec(18, 10, 29, RED),
            new VerticeVec(18, 29,  7, PURPLE),
            new VerticeVec(19,  6,  7, BLUE),
            new VerticeVec(19,  7, 31, RED),
            new VerticeVec(19, 31, 11, GREEN),
            new VerticeVec(19, 11, 30, CYAN),
            new VerticeVec(19, 30,  6, RED),
            new VerticeVec(20,  8, 10, YELLOW),
            new VerticeVec(20, 10, 28, BLUE),
            new VerticeVec(20, 28,  0, RED),
            new VerticeVec(20,  0, 24, GREEN),
            new VerticeVec(20, 24,  8, CYAN),
            new VerticeVec(21, 10,  8, RED),
            new VerticeVec(21,  8, 25, GREEN),
            new VerticeVec(21, 25,  1, BLUE),
            new VerticeVec(21,  1, 29, RED),
            new VerticeVec(21, 29, 10, GREEN),
            new VerticeVec(22, 11,  9, CYAN),
            new VerticeVec(22,  9, 26, RED),
            new VerticeVec(22, 26,  2, YELLOW),
            new VerticeVec(22,  2, 30, BLUE),
            new VerticeVec(22, 30, 11, RED),
            new VerticeVec(23,  9, 11, GREEN),
            new VerticeVec(23, 11, 31, CYAN),
            new VerticeVec(23, 31,  3, RED),
            new VerticeVec(23,  3, 27, PURPLE),
            new VerticeVec(23, 27,  9, BLUE)

    };

    private void outputFaces(GL2 gl){

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glBegin(GL2.GL_TRIANGLES);

        for (VerticeVec i : DODECAHEDRON_FACES) {

            final Vec3f vec1 = DODECAHEDRON_VERTICIES[i.x];
            final Vec3f vec2 = DODECAHEDRON_VERTICIES[i.y];
            final Vec3f vec3 = DODECAHEDRON_VERTICIES[i.z];

            Vec3f normal = VerticeVec.normalize(VerticeVec.cross(VerticeVec.difference(vec2, vec1), VerticeVec.difference(vec3, vec1)));

            gl.glColor4f(i.color.x, i.color.y, i.color.z, i.color.w);
            gl.glNormal3f(normal.x, normal.y, normal.z);
            gl.glVertex3f(vec1.x, vec1.y, vec1.z);
            gl.glVertex3f(vec2.x, vec2.y, vec2.z);
            gl.glVertex3f(vec3.x, vec3.y, vec3.z);
        }

        gl.glEnd();
    }

    void drawVerticies(GL2 gl){

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glBegin(GL2.GL_TRIANGLES);

        for (VerticeVec i : DODECAHEDRON_FACES) {

            final Vec3f vec1 = DODECAHEDRON_VERTICIES[i.x];
            final Vec3f vec2 = DODECAHEDRON_VERTICIES[i.y];
            final Vec3f vec3 = DODECAHEDRON_VERTICIES[i.z];


            gl.glColor4f(0, 0, 0, 1);
            gl.glVertex3f(vec1.x, vec1.y, vec1.z);
            gl.glVertex3f(vec2.x, vec2.y, vec2.z);
            gl.glVertex3f(vec3.x, vec3.y, vec3.z);
        }

        gl.glEnd();
    }

    void drawDodecahedron(GL2 gl){

        gl.glFrontFace(GL2.GL_CW);
        outputFaces(gl);
        gl.glFrontFace(GL2.GL_CCW);
        outputFaces(gl);
    }
}