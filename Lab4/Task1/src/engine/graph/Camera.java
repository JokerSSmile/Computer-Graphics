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

    Vector3f getRotation() {
        return rotation;
    }
}