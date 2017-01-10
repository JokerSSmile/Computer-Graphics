package engine.entity;

import engine.Scene;
import org.joml.Vector2i;
import org.joml.Vector3f;
import engine.graph.Mesh;

import java.awt.*;

public abstract class GameItem {

    protected Mesh mesh;
    protected Vector3f position;
    protected Vector2i size;
    private float scale;
    private Vector3f rotation;
    boolean isAlive;


    public GameItem() {
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
        isAlive = true;
    }
    
    public GameItem(Mesh mesh) {
        this();
        this.mesh = mesh;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }
    
    public Mesh getMesh() {
        return mesh;
    }
    
    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public boolean isNeedToRemove(){
        return !isAlive;
    }

    public void hit(){
        isAlive = false;
    }

    public boolean isCollision(GameItem other){

        java.awt.geom.Rectangle2D thisRect = new Rectangle((int)position.x, (int)position.z, size.x, size.y);
        java.awt.geom.Rectangle2D otherRect = new Rectangle((int)other.position.x - 10, (int)other.position.z, other.size.x, other.size.y);

        return thisRect.intersects(otherRect);
    }

    public abstract void update(float interval, Scene scene);
}