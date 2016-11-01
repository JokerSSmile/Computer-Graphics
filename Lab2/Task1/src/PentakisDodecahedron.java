import com.jogamp.opengl.GL2;
import com.sun.javafx.geom.Vec3f;

class PentakisDodecahedron {

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

            new VerticeVec(12,  0,  2),
            new VerticeVec(12,  2, 26),
            new VerticeVec(12, 26,  4),
            new VerticeVec(12,  4, 24),
            new VerticeVec(12, 24,  0),
            new VerticeVec(13,  3,  1),
            new VerticeVec(13,  1, 25),
            new VerticeVec(13, 25,  5),
            new VerticeVec(13,  5, 27),
            new VerticeVec(13, 27,  3),
            new VerticeVec(14,  2,  0),
            new VerticeVec(14,  0, 28),
            new VerticeVec(14, 28,  6),
            new VerticeVec(14,  6, 30),
            new VerticeVec(14, 30,  2),
            new VerticeVec(15,  1,  3),
            new VerticeVec(15,  3, 31),
            new VerticeVec(15, 31,  7),
            new VerticeVec(15,  7, 29),
            new VerticeVec(15, 29,  1),
            new VerticeVec(16,  4,  5),
            new VerticeVec(16,  5, 25),
            new VerticeVec(16, 25,  8),
            new VerticeVec(16,  8, 24),
            new VerticeVec(16, 24,  4),
            new VerticeVec(17,  5,  4),
            new VerticeVec(17,  4, 26),
            new VerticeVec(17, 26,  9),
            new VerticeVec(17,  9, 27),
            new VerticeVec(17, 27,  5),
            new VerticeVec(18,  7,  6),
            new VerticeVec(18,  6, 28),
            new VerticeVec(18, 28, 10),
            new VerticeVec(18, 10, 29),
            new VerticeVec(18, 29,  7),
            new VerticeVec(19,  6,  7),
            new VerticeVec(19,  7, 31),
            new VerticeVec(19, 31, 11),
            new VerticeVec(19, 11, 30),
            new VerticeVec(19, 30,  6),
            new VerticeVec(20,  8, 10),
            new VerticeVec(20, 10, 28),
            new VerticeVec(20, 28,  0),
            new VerticeVec(20,  0, 24),
            new VerticeVec(20, 24,  8),
            new VerticeVec(21, 10,  8),
            new VerticeVec(21,  8, 25),
            new VerticeVec(21, 25,  1),
            new VerticeVec(21,  1, 29),
            new VerticeVec(21, 29, 10),
            new VerticeVec(22, 11,  9),
            new VerticeVec(22,  9, 26),
            new VerticeVec(22, 26,  2),
            new VerticeVec(22,  2, 30),
            new VerticeVec(22, 30, 11),
            new VerticeVec(23,  9, 11),
            new VerticeVec(23, 11, 31),
            new VerticeVec(23, 31,  3),
            new VerticeVec(23,  3, 27),
            new VerticeVec(23, 27,  9)

    };

    private void outputFaces(GL2 gl){

        gl.glBegin(GL2.GL_TRIANGLES);

        for (VerticeVec i : DODECAHEDRON_FACES) {

            final Vec3f vec1 = DODECAHEDRON_VERTICIES[i.x];
            final Vec3f vec2 = DODECAHEDRON_VERTICIES[i.y];
            final Vec3f vec3 = DODECAHEDRON_VERTICIES[i.z];

            Vec3f normal = VerticeVec.normalize(VerticeVec.cross(VerticeVec.difference(vec2, vec1), VerticeVec.difference(vec3, vec1)));

            gl.glColor4f(0, 1, 0, 0.5f);
            //gl.glColor4d(Math.random(), Math.random(), Math.random(), Math.random());

            gl.glNormal3f(normal.x, normal.y, normal.z);
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

        /*
        gl.glBegin(GL2.GL_LINES);


        for (int i = 0; i < 32; i++ ){

            //System.out.println( i + " " + DODECAHEDRON_FACES.length);
            gl.glColor4f(1, 0, 0, 1);

            final Vec3f vec = DODECAHEDRON_VERTICIES[i];


            gl.glVertex3f(vec.x, vec.y, vec.z);

        }

        gl.glEnd();
        */
    }
}