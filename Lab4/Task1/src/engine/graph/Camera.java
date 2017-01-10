package engine.graph;

import org.joml.Vector3f;

public class Camera {

    private static final Vector3f CAMERA_INIT_POSITION = new Vector3f(0, 120, 50);;
    private static final Vector3f CAMERA_INIT_ROTATION = new Vector3f(45, 0, 0);;

    private final Vector3f position;
    private final Vector3f rotation;
    
    public Camera() {
        position = new Vector3f(CAMERA_INIT_POSITION.x, CAMERA_INIT_POSITION.y, CAMERA_INIT_POSITION.z);
        rotation = new Vector3f(CAMERA_INIT_ROTATION.x, CAMERA_INIT_ROTATION.y, CAMERA_INIT_ROTATION.z);

    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }
    
    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public Vector3f getRotation() {
        return rotation;
    }
    
    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }
}