package engine.graph;

import org.joml.Vector3f;

public class Material {

    private static final Vector3f DEFAULT_COLOUR = new Vector3f(1.0f, 1.0f, 1.0f);

    private Vector3f colour;
    
    private float reflectance;

    private Texture texture;
    
    private Material() {
        colour = DEFAULT_COLOUR;
        reflectance = 0;
    }
    
    public Material(Vector3f colour, float reflectance) {
        this();
        this.colour = colour;
        this.reflectance = reflectance;
    }

    public Material(Texture texture) {
        this();
        this.texture = texture;
    }

    public Material(Texture texture, float reflectance) {
        this();
        this.texture = texture;
        this.reflectance = reflectance;
    }

    Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    float getReflectance() {
        return reflectance;
    }

    boolean isTextured() {
        return this.texture != null;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}