package engine.graph;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import engine.entity.GameItem;

class Transformation {

    private final Matrix4f projectionMatrix;
    private final Matrix4f modelMatrix;
    private final Matrix4f modelViewMatrix;
    private final Matrix4f viewMatrix;
    private final Matrix4f orthoMatrix;
    private final Matrix4f orthoModelMatrix;

    Transformation() {
        projectionMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
        modelViewMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        orthoMatrix = new Matrix4f();
        orthoModelMatrix = new Matrix4f();
    }

    Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
    
    Matrix4f updateProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRatio = width / height;        
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    Matrix4f getViewMatrix() {
        return viewMatrix;
    }
    
    Matrix4f updateViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();
        
        viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }

    final Matrix4f getOrthoProjectionMatrix(float left, float right, float bottom, float top) {
        orthoMatrix.identity();
        orthoMatrix.setOrtho2D(left, right, bottom, top);
        return orthoMatrix;
    }
    
    Matrix4f buildModelViewMatrix(GameItem gameItem, Matrix4f viewMatrix) {
        Vector3f rotation = gameItem.getRotation();
        modelMatrix.identity().translate(gameItem.getPosition()).
                rotateX((float)Math.toRadians(-rotation.x)).
                rotateY((float)Math.toRadians(-rotation.y)).
                rotateZ((float)Math.toRadians(-rotation.z)).
                scale(gameItem.getScale());
        modelViewMatrix.set(viewMatrix);
        return modelViewMatrix.mul(modelMatrix);
    }

    Matrix4f buildOrtoProjModelMatrix(GameItem gameItem, Matrix4f orthoMatrix) {
        Vector3f rotation = gameItem.getRotation();
        modelMatrix.identity().translate(gameItem.getPosition()).
                rotateX((float) Math.toRadians(-rotation.x)).
                rotateY((float) Math.toRadians(-rotation.y)).
                rotateZ((float) Math.toRadians(-rotation.z)).
                scale(gameItem.getScale());
        orthoModelMatrix.set(orthoMatrix);
        orthoModelMatrix.mul(modelMatrix);
        return orthoModelMatrix;
    }
}
