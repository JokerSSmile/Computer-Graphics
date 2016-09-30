import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class CoordinateParser {

    private final String COORDINATE_PAIR_SEPARATOR = ";";
    private final String COORDINATE_SEPARATOR = " ";

    public Vector<Vector<Double>> ReadData(int sideSize, String filePath)
    {
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

                    coordinatePair.add((Double.parseDouble(coordinate[0]) - sideSize / 2) / sideSize);
                    coordinatePair.add(-(Double.parseDouble(coordinate[1]) - sideSize / 2) / sideSize);

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

    public void Draw(GLAutoDrawable glAutoDrawable, Vector<Vector<Double>> coordinates)
    {

        final GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glBegin(GL2.GL_TRIANGLE_FAN);

        gl.glColor3f(0f,1f,0f);
        for (Vector<Double> coordinatePair : coordinates)
        {
            gl.glVertex2d(coordinatePair.get(0), coordinatePair.get(1));
        }

        gl.glEnd();



        gl.glBegin(GL2.GL_LINE_STRIP);

        for (Vector<Double> coordinatePair : coordinates)
        {
            gl.glColor3f(1f,0f,0f);
            gl.glVertex2d(coordinatePair.get(0), coordinatePair.get(1));
        }

        gl.glEnd();

    }

}
