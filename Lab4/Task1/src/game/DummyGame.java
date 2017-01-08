package game;

import engine.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

import engine.graph.Camera;
import engine.graph.DirectionalLight;
import engine.graph.Material;
import engine.graph.Mesh;
import engine.graph.OBJLoader;
import engine.graph.PointLight;
import engine.graph.SpotLight;
import engine.graph.Texture;

import java.util.Vector;

public class DummyGame implements IGameLogic {

    private static final Vector3f CAMERA_INIT_POSITION = new Vector3f(30, 80, 0);
    private static final Vector3f CAMERA_INIT_ROTATION = new Vector3f(40, 0, 0);
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private static final int PLAYER_MOVE_BORDER = 80;

    private final Vector3f cameraInc;
    private final Renderer renderer;
    private final Camera camera;
    private Vector<GameItem> gameItems;
    private Vector3f ambientLight;
    private PointLight[] pointLightList;
    private SpotLight[] spotLightList;
    private DirectionalLight directionalLight;
    private Timer timer;
    float switchDirTime;

    public DummyGame() {
        renderer = new Renderer();
        camera = new Camera();
        gameItems = new Vector<>();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
        timer = new Timer();
    }

    @Override
    public void init(Window window) throws Exception {

        timer.init();
        renderer.init(window);
        camera.moveRotation(CAMERA_INIT_ROTATION);
        camera.movePosition(CAMERA_INIT_POSITION);

        float reflectance = 1f;
        //Mesh tieFighterMesh = OBJLoader.loadMesh("src/resources/models/TIE-fighter.obj");
        Mesh tieFighterMesh = OBJLoader.loadMesh("src/resources/models/TIE-fighter.obj");
        Mesh xWingMesh = OBJLoader.loadMesh("src/resources/models/X-wing.obj");
        Material material = new Material(new Vector3f(0.2f, 0.5f, 0.5f), reflectance);

        tieFighterMesh.setMaterial(material);
        GameItem gameItem = new GameItem(tieFighterMesh, new Vector3f(0, 0, 0));
        gameItem.setScale(0.2f);

        gameItems.add(gameItem);

        xWingMesh.setMaterial(material);
        for (int i = 0; i < 6; i++) {
            for (int k = 0; k < 3; k++) {
                GameItem xWingEnemy = new GameItem(xWingMesh, new Vector3f(i * 40 - 180, 0 , k * 50 - 200));
                xWingEnemy.setScale(0.05f);
                gameItems.add(xWingEnemy);
            }
        }

        ambientLight = new Vector3f(0.3f, 0.3f, 0.3f);

        // Point Light
        Vector3f lightPosition = new Vector3f(0, 0, 1);
        float lightIntensity = 1.0f;
        PointLight pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);
        pointLightList = new PointLight[]{pointLight};

        lightPosition = new Vector3f(-1, 0, 0);
        directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_A)) {
            if (gameItems.get(0).getPosition().x > -PLAYER_MOVE_BORDER) {
                gameItems.get(0).setPosition(gameItems.get(0).getPosition().x - 1, gameItems.get(0).getPosition().y, gameItems.get(0).getPosition().z);
            }
            else {
                gameItems.get(0).setPosition(-PLAYER_MOVE_BORDER, gameItems.get(0).getPosition().y, gameItems.get(0).getPosition().z);
            }
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            if (gameItems.get(0).getPosition().x < PLAYER_MOVE_BORDER) {
                gameItems.get(0).setPosition(gameItems.get(0).getPosition().x + 1, gameItems.get(0).getPosition().y, gameItems.get(0).getPosition().z);
            }
            else {
                gameItems.get(0).setPosition(PLAYER_MOVE_BORDER, gameItems.get(0).getPosition().y, gameItems.get(0).getPosition().z);
            }
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {

        // Update camera based on mouse            
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }

        switchDirTime += timer.getElapsedTime();
        for (int i = 1; i < gameItems.size(); i++){
            Vector3f pos = gameItems.get(i).getPosition();
            if (switchDirTime < 2.5){
                gameItems.get(i).setPosition(pos.x + 1, pos.y, pos.z);
            }
            else if (switchDirTime < 5){
                gameItems.get(i).setPosition(pos.x - 1, pos.y, pos.z);
            }
            else{
                switchDirTime = 0;
            }
        }
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, gameItems, ambientLight,
                pointLightList, spotLightList, directionalLight);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }
}
