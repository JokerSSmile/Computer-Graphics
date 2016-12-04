package utils;

import com.sun.javafx.geom.Vec2f;

public class SVertexP2T2 {

	public Vec2f position;
	public Vec2f texCoord;

	public SVertexP2T2(Vec2f position, Vec2f texCoord){

		this.position = position;
		this.texCoord = texCoord;
	}
};