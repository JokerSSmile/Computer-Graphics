import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class CoordinateParser {

    private final String COORDINATE_PAIR_SEPARATOR = ";";
    private final String COORDINATE_SEPARATOR = " ";

    public Vector<Vector<Double>> ReadData(String filePath) {
        String line;
        Vector<Vector<Double>> scaledCoordinates = new Vector<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null)
            {
                String coordinates[] = line.split(COORDINATE_PAIR_SEPARATOR);

                for (String pair: coordinates)
                {
                    String coordinate[] = pair.split(COORDINATE_SEPARATOR);
                    Vector<Double> coordinatePair = new Vector<>();

                    coordinatePair.add(Double.parseDouble(coordinate[0]));
                    coordinatePair.add(Double.parseDouble(coordinate[1]));

                    scaledCoordinates.add(coordinatePair);
                }
            }
        }
        catch (IOException err)
        {
            err.printStackTrace();
        }

        return scaledCoordinates;
    }

    public void Draw(GL2 gl, Vector<Vector<Double>> coordinates, ColorVec color) {

        gl.glBegin(GL2.GL_TRIANGLE_FAN);

        gl.glColor3f( color.r, color.g, color.b );
        for (Vector<Double> coordinatePair : coordinates)
        {
            gl.glVertex2d(coordinatePair.get(0), coordinatePair.get(1));
        }

        gl.glEnd();


        DrawLines(gl, coordinates);

    }

    public void DrawLines(GL2 gl, Vector<Vector<Double>> coordinates) {


        gl.glBegin(GL2.GL_LINE_STRIP);

        gl.glColor3f( 0.07f, 0.4f, 0.7f );
        for (Vector<Double> coordinatePair : coordinates)
        {
            gl.glVertex2d(coordinatePair.get(0), coordinatePair.get(1));
        }
        gl.glEnd();

    }

}
