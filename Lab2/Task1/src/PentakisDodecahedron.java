import com.jogamp.opengl.GL2;
import com.sun.javafx.geom.Vec3f;
import com.sun.javafx.geom.Vec4f;

import java.util.Random;

class PentakisDodecahedron {

    //private final static Vec4f RED = new Vec4f(1, 0, 0, 0.6f);
    //private final static Vec4f GREEN = new Vec4f(0, 1, 0, 0.6f);
    //private final static Vec4f BLUE = new Vec4f(0, 0, 1, 0.6f);
    //private final static Vec4f YELLOW = new Vec4f(1, 1, 0, 0.6f);
    //private final static Vec4f CYAN = new Vec4f(0, 1, 1, 0.6f);
    //private final static Vec4f PURPLE = new Vec4f(1, 0, 1, 0.6f);
	//private final static Vec4f DARKSLATEBLUE = new Vec4f(0.28f, 0.24f, 0.5f, 0.6f);

	static Vec4f setColor(){

		Random random = new Random();
		return new Vec4f(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0.6f);
	}

    //Дельтоидальный гексеконтаэдр
    private final static float C0 = (5 - (float)Math.sqrt(5)) / 4;
    private final static float C1 = (15 + (float)Math.sqrt(5)) / 22;
    private final static float C2 = (float)Math.sqrt(5) / 2;
    private final static float C3 = (5 + (float)Math.sqrt(5)) / 6;
    private final static float C4 = (5 + 4 * (float)Math.sqrt(5)) / 11;
    private final static float C5 = (5 + (float)Math.sqrt(5)) / 4;
    private final static float C6 = (5 + 3 * (float)Math.sqrt(5)) / 6;
    private final static float C7 = (25 + 9 * (float)Math.sqrt(5)) / 22;
    private final static float C8 = (float)Math.sqrt(5);

    private final static Vec3f[] DODECAHEDRON_VERTICIES = {

            new Vec3f(0.0f, 0.0f,  C8),
            new Vec3f(0.0f, 0.0f, -C8),
            new Vec3f( C8, 0.0f, 0.0f),
            new Vec3f(-C8, 0.0f, 0.0f),
            new Vec3f(0.0f,  C8, 0.0f),
            new Vec3f(0.0f, -C8, 0.0f),
            new Vec3f(0.0f,  C1,  C7),
            new Vec3f(0.0f,  C1, -C7),
            new Vec3f(0.0f, -C1,  C7),
            new Vec3f(0.0f, -C1, -C7),
            new Vec3f(C7, 0.0f,  C1),
            new Vec3f(C7, 0.0f, -C1),
            new Vec3f(-C7, 0.0f,  C1),
            new Vec3f(-C7, 0.0f, -C1),
            new Vec3f(C1,  C7, 0.0f),
            new Vec3f(C1, -C7, 0.0f),
            new Vec3f(-C1,  C7, 0.0f),
            new Vec3f(-C1, -C7, 0.0f),
            new Vec3f(C3, 0.0f,  C6),
            new Vec3f(C3, 0.0f, -C6),
            new Vec3f(-C3, 0.0f,  C6),
            new Vec3f(-C3, 0.0f, -C6),
            new Vec3f(C6,  C3, 0.0f),
            new Vec3f(C6, -C3, 0.0f),
            new Vec3f(-C6,  C3, 0.0f),
            new Vec3f(-C6, -C3, 0.0f),
            new Vec3f(0.0f,  C6,  C3),
            new Vec3f(0.0f,  C6, -C3),
            new Vec3f(0.0f, -C6,  C3),
            new Vec3f(0.0f, -C6, -C3),
            new Vec3f(C0,  C2,  C5),
            new Vec3f(C0,  C2, -C5),
            new Vec3f(C0, -C2,  C5),
            new Vec3f(C0, -C2, -C5),
            new Vec3f(-C0,  C2,  C5),
            new Vec3f(-C0,  C2, -C5),
            new Vec3f(-C0, -C2,  C5),
            new Vec3f(-C0, -C2, -C5),
            new Vec3f(C5,  C0,  C2),
            new Vec3f(C5,  C0, -C2),
            new Vec3f(C5, -C0,  C2),
            new Vec3f(C5, -C0, -C2),
            new Vec3f(-C5,  C0,  C2),
            new Vec3f(-C5,  C0, -C2),
            new Vec3f(-C5, -C0,  C2),
            new Vec3f(-C5, -C0, -C2),
            new Vec3f(C2,  C5,  C0),
            new Vec3f(C2,  C5, -C0),
            new Vec3f(C2, -C5,  C0),
            new Vec3f(C2, -C5, -C0),
            new Vec3f(-C2,  C5,  C0),
            new Vec3f(-C2,  C5, -C0),
            new Vec3f(-C2, -C5,  C0),
            new Vec3f(-C2, -C5, -C0),
            new Vec3f(C4,  C4,  C4),
            new Vec3f(C4,  C4, -C4),
            new Vec3f(C4, -C4,  C4),
            new Vec3f(C4, -C4, -C4),
            new Vec3f(-C4,  C4,  C4),
            new Vec3f(-C4,  C4, -C4),
            new Vec3f(-C4, -C4,  C4),
            new Vec3f(-C4, -C4, -C4),
    };

    private final static VerticeVec[] DODECAHEDRON_FACES = {

            new VerticeVec(18,  0,  8, 32, setColor()),
            new VerticeVec(18, 32, 56, 40, setColor()),
            new VerticeVec(18, 40, 10, 38, setColor()),
            new VerticeVec(18, 38, 54, 30, setColor()),
            new VerticeVec(18, 30,  6,  0, setColor()),
            new VerticeVec(19,  1,  7, 31, setColor()),
            new VerticeVec(19, 31, 55, 39, setColor()),
            new VerticeVec(19, 39, 11, 41, setColor()),
            new VerticeVec(19, 41, 57, 33, setColor()),
            new VerticeVec(19, 33,  9,  1, setColor()),
            new VerticeVec(20,  0,  6, 34, setColor()),
            new VerticeVec(20, 34, 58, 42, setColor()),
            new VerticeVec(20, 42, 12, 44, setColor()),
            new VerticeVec(20, 44, 60, 36, setColor()),
            new VerticeVec(20, 36,  8,  0, setColor()),
            new VerticeVec(21,  1,  9, 37, setColor()),
            new VerticeVec(21, 37, 61, 45, setColor()),
            new VerticeVec(21, 45, 13, 43, setColor()),
            new VerticeVec(21, 43, 59, 35, setColor()),
            new VerticeVec(21, 35,  7,  1, setColor()),
            new VerticeVec(22,  2, 11, 39, setColor()),
            new VerticeVec(22, 39, 55, 47, setColor()),
            new VerticeVec(22, 47, 14, 46, setColor()),
            new VerticeVec(22, 46, 54, 38, setColor()),
            new VerticeVec(22, 38, 10,  2, setColor()),
            new VerticeVec(23,  2, 10, 40, setColor()),
            new VerticeVec(23, 40, 56, 48, setColor()),
            new VerticeVec(23, 48, 15, 49, setColor()),
            new VerticeVec(23, 49, 57, 41, setColor()),
			new VerticeVec(23, 41, 11,  2, setColor()),
			new VerticeVec(24,  3, 12, 42, setColor()),
            new VerticeVec(24, 42, 58, 50, setColor()),
            new VerticeVec(24, 50, 16, 51, setColor()),
            new VerticeVec(24, 51, 59, 43, setColor()),
            new VerticeVec(24, 43, 13,  3, setColor()),
            new VerticeVec(25,  3, 13, 45, setColor()),
            new VerticeVec(25, 45, 61, 53, setColor()),
            new VerticeVec(25, 53, 17, 52, setColor()),
            new VerticeVec(25, 52, 60, 44, setColor()),
            new VerticeVec(25, 44, 12,  3, setColor()),
            new VerticeVec(26,  4, 16, 50, setColor()),
            new VerticeVec(26, 50, 58, 34, setColor()),
            new VerticeVec(26, 34,  6, 30, setColor()),
            new VerticeVec(26, 30, 54, 46, setColor()),
            new VerticeVec(26, 46, 14,  4, setColor()),
            new VerticeVec(27,  4, 14, 47, setColor()),
            new VerticeVec(27, 47, 55, 31, setColor()),
            new VerticeVec(27, 31,  7, 35, setColor()),
            new VerticeVec(27, 35, 59, 51, setColor()),
            new VerticeVec(27, 51, 16,  4, setColor()),
            new VerticeVec(28,  5, 15, 48, setColor()),
            new VerticeVec(28, 48, 56, 32, setColor()),
            new VerticeVec(28, 32,  8, 36, setColor()),
            new VerticeVec(28, 36, 60, 52, setColor()),
            new VerticeVec(28, 52, 17,  5, setColor()),
            new VerticeVec(29,  5, 17, 53, setColor()),
            new VerticeVec(29, 53, 61, 37, setColor()),
            new VerticeVec(29, 37,  9, 33, setColor()),
            new VerticeVec(29, 33, 57, 49, setColor()),
            new VerticeVec(29, 49, 15,  5, setColor()),
    };

    private void outputFaces(GL2 gl){

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glBegin(GL2.GL_QUADS);

        for (VerticeVec i : DODECAHEDRON_FACES) {

            final Vec3f vec1 = DODECAHEDRON_VERTICIES[i.x];
            final Vec3f vec2 = DODECAHEDRON_VERTICIES[i.y];
            final Vec3f vec3 = DODECAHEDRON_VERTICIES[i.z];
            final Vec3f vec4 = DODECAHEDRON_VERTICIES[i.w];

            Vec3f normal = VerticeVec.normalize(VerticeVec.cross(VerticeVec.difference(vec2, vec1), VerticeVec.difference(vec3, vec1)));

            gl.glColor4f(i.color.x, i.color.y, i.color.z, i.color.w);
            gl.glNormal3f(normal.x, normal.y, normal.z);
            gl.glVertex3f(vec1.x, vec1.y, vec1.z);
            gl.glVertex3f(vec2.x, vec2.y, vec2.z);
            gl.glVertex3f(vec3.x, vec3.y, vec3.z);
            gl.glVertex3f(vec4.x, vec4.y, vec4.z);
        }

        gl.glEnd();
    }

    void drawVerticies(GL2 gl){

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glBegin(GL2.GL_QUADS);

        for (VerticeVec i : DODECAHEDRON_FACES) {

            final Vec3f vec1 = DODECAHEDRON_VERTICIES[i.x];
            final Vec3f vec2 = DODECAHEDRON_VERTICIES[i.y];
            final Vec3f vec3 = DODECAHEDRON_VERTICIES[i.z];
            final Vec3f vec4 = DODECAHEDRON_VERTICIES[i.w];

            gl.glColor4f(0, 0, 0, 1);
            gl.glVertex3f(vec1.x, vec1.y, vec1.z);
            gl.glVertex3f(vec2.x, vec2.y, vec2.z);
            gl.glVertex3f(vec3.x, vec3.y, vec3.z);
            gl.glVertex3f(vec4.x, vec4.y, vec4.z);
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