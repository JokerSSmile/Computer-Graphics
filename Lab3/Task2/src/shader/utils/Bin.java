package shader.utils;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.sun.prism.impl.BufferUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Bin {

	/*
	* createVertShader is handed a String defining where to find the file
	* that contains the shader code. It creates and compiles the shader
	* and returns a unique int that GL associates with it
	*/

	public static int createVertShader(GL2 gl, String filename){

		//get the unique id
		int vertShader = (int) gl.glCreateShaderObjectARB(GL2.GL_VERTEX_SHADER);

		if(vertShader==0){
			return 0;
		}

		//create a single String array index to hold the shader code
		String[] vertCode=new String[1];
		vertCode[0]="";
		String line;

		//open the file and read the contents into the String array.
		try{

			BufferedReader reader=new BufferedReader(new FileReader(filename));

			while((line=reader.readLine())!=null){

				vertCode[0] += line + "\n";
			}

		}catch(Exception e){

			System.out.println("Fail reading vert shader");
		}

		//Associate the code string with the unique id
		gl.glShaderSourceARB(vertShader, 1, vertCode, null);
		//compile the vertex shader

		gl.glCompileShaderARB(vertShader);

		//print out log
		if(!printLogInfo(gl, vertShader))vertShader=0;

		//the int returned is now associated with the compiled shader
		return vertShader;
	}

	/*
	* Essentially the same as the vertex shader
	*/
	public static int createFragShader(GL2 gl, String filename) {

		int fragShader = (int) gl.glCreateShaderObjectARB(GL2.GL_FRAGMENT_SHADER);
		if (fragShader == 0){
			return 0;
		}

		String[] fragCode = new String[1];
		fragCode[0] = "";
		String line;

		try {

			BufferedReader reader = new BufferedReader(new FileReader(filename));

			while ((line = reader.readLine()) != null) {

				fragCode[0] += line + "\n";
			}

		} catch (Exception e) {

			System.out.println("Fail reading vert shader");
		}

		gl.glShaderSourceARB(fragShader, 1, fragCode, null);
		gl.glCompileShaderARB(fragShader);

		if (!printLogInfo(gl, fragShader)){
			fragShader = 0;
		}

		return fragShader;

	}

	/*
	* Prints the log to System.out
	*/
	public static boolean printLogInfo(GL2 gl, int obj){

		IntBuffer ival= BufferUtil.newIntBuffer(1);

		gl.glGetObjectParameterivARB(obj, GL2.GL_OBJECT_INFO_LOG_LENGTH_ARB, ival);

		int size=ival.get();

		if(size>1){

			ByteBuffer log=BufferUtil.newByteBuffer(size);

			ival.flip();

			gl.glGetInfoLogARB(obj, size, ival, log);

			byte[] infoBytes=new byte[size];

			log.get(infoBytes);

			System.out.println("Info log: " + new String(infoBytes));

			return true;

		}

		return false;

	}

}
