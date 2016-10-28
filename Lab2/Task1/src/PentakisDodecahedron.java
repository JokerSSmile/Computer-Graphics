import com.jogamp.opengl.GL2;
import com.sun.javafx.geom.Vec3f;

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

    private final static int[] DODECAHEDRON_INDICIES = {

            12,  0,  2,
            12,  2, 26,
            12, 26,  4,
            12,  4, 24,
            12, 24,  0,
            13,  3,  1,
            13,  1, 25,
            13, 25,  5,
            13,  5, 27,
            13, 27,  3,
            14,  2,  0,
            14,  0, 28,
            14, 28,  6,
            14,  6, 30,
            14, 30,  2,
            15,  1,  3,
            15,  3, 31,
            15, 31,  7,
            15,  7, 29,
            15, 29,  1,
            16,  4,  5,
            16,  5, 25,
            16, 25,  8,
            16,  8, 24,
            16, 24,  4,
            17,  5,  4,
            17,  4, 26,
            17, 26,  9,
            17,  9, 27,
            17, 27,  5,
            18,  7,  6,
            18,  6, 28,
            18, 28, 10,
            18, 10, 29,
            18, 29,  7,
            19,  6,  7,
            19,  7, 31,
            19, 31, 11,
            19, 11, 30,
            19, 30,  6,
            20,  8, 10,
            20, 10, 28,
            20, 28,  0,
            20,  0, 24,
            20, 24,  8,
            21, 10,  8,
            21,  8, 25,
            21, 25,  1,
            21,  1, 29,
            21, 29, 10,
            22, 11,  9,
            22,  9, 26,
            22, 26,  2,
            22,  2, 30,
            22, 30, 11,
            23,  9, 11,
            23, 11, 31,
            23, 31,  3,
            23,  3, 27,
            23, 27,  9

    };

    public void draw(GL2 gl){


        gl.glBegin(GL2.GL_TRIANGLES);

        gl.glDepthMask(false);


        gl.glEnable(GL2.GL_CULL_FACE);
        gl.glCullFace(GL2.GL_FRONT);

        for (int i : DODECAHEDRON_INDICIES){

            float[] color1 = {1, 0, 0, 0.5f};
            float[] color2 = {0, 1, 0, 0.5f};
            if (i % 2 == 0){
                gl.glColor4f(0, 0, 1, 0.5f);
            }
            else{
                gl.glColor4f(0, 1, 0, 0.5f);
            }


            final Vec3f vec = DODECAHEDRON_VERTICIES[i];
            gl.glVertex3f(vec.x, vec.y, vec.z);
        }

        gl.glCullFace(GL2.GL_BACK);

        for (int i : DODECAHEDRON_INDICIES){

            float[] color1 = {1, 0, 0, 0.5f};
            float[] color2 = {0, 1, 0, 0.5f};
            if (i % 2 == 0){
                //gl.glColor4f(0, 0, 1, 0.5f);
            }
            else{
                //gl.glColor4f(0, 1, 0, 0.5f);
            }


            final Vec3f vec = DODECAHEDRON_VERTICIES[i];
            gl.glVertex3f(vec.x, vec.y, vec.z);
        }

        gl.glEnd();
    }
}