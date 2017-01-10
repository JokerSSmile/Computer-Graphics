package game;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import engine.entity.*;
import engine.graph.Renderer;
import org.joml.Vector2f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

import engine.IGameLogic;
import engine.MouseInput;
import engine.Scene;
import engine.SceneLight;
import engine.Window;
import engine.graph.Camera;
import engine.graph.DirectionalLight;
import engine.graph.Material;
import engine.graph.Mesh;
import engine.graph.OBJLoader;

public class StarWars implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private final Vector3f cameraInc;
    private final Renderer renderer;
    private final Camera camera;
    private Scene scene;
    private Hud hud;
    private int score;

    private static final float CAMERA_POS_STEP = 0.05f;

    public StarWars() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        scene = new Scene();
        
        // Setup  GameItems
        float reflectance = 1f;
        Mesh tieFighterMesh = OBJLoader.loadMesh("src/resources/models/TIE-fighter1.obj");
        Mesh xWingMesh = OBJLoader.loadMesh("src/resources/models/X-wing1.obj");
        Mesh mFalconMesh = OBJLoader.loadMesh("src/resources/models/MILLENIUM-falcon.obj");
        Material darkMaterial = new Material(new Vector3f(0.2f, 0.2f, 0.2f), reflectance);
        Material lightMaterial = new Material(new Vector3f(0.5f, 0.5f, 0.5f), reflectance);
        tieFighterMesh.setMaterial(darkMaterial);
        xWingMesh.setMaterial(lightMaterial);
        mFalconMesh.setMaterial(lightMaterial);
        
        float blockScale = 0.5f;
        float extension = 2.0f;

        //Initialize player
        TIEFighter tieFighter = new TIEFighter(tieFighterMesh);
        tieFighter.setScale(1f);

        //Fill game items array
        List<GameItem> gameItems = new Vector<>();
        gameItems.add(tieFighter);
        int enemyRows = 4;
        int enemyColumns = 6;
        for (int i = 0; i < enemyColumns; i++) {
            for (int k = 0; k < enemyRows; k++) {
                if (k != 0) {
                    XWing xWing = new XWing(xWingMesh);
                    xWing.setPosition(i * 32 - 100, -5, k * 40 - 250);
                    gameItems.add(xWing);
                } else {
                    MillenuimFalcon mFalcon = new MillenuimFalcon(mFalconMesh);
                    mFalcon.setPosition(i * 32 - 100, -5, k * 40 - 250);
                    gameItems.add(mFalcon);
                }
            }
        }

        scene.setGameItems(gameItems);

        // Setup  SkyBox
        float skyBoxScale = 600.0f;
        SkyBox skyBox = new SkyBox("src/resources/models/skybox.obj", "src/resources/textures/4.png");
        skyBox.setScale(skyBoxScale);
        scene.setSkyBox(skyBox);
        
        // Setup Lights
        setupLights();
        
        // Create HUD
        hud = new Hud(Integer.toString(score));
    }

    private void initializeEnemies(){

    }
    
    private void setupLights() {
        SceneLight sceneLight = new SceneLight();
        scene.setSceneLight(sceneLight);

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 1.0f;
        Vector3f lightPosition = new Vector3f(-1, 0, 0);
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity));
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -50;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 50;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -50;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 50;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -50;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 50;
        }

        //Player input
        TIEFighter player = null;
        for (GameItem item : scene.getGameItems()){
            if (item.getClass() == TIEFighter.class){
                player = (TIEFighter)item;
                break;
            }
        }
        assert player != null;
        player.input(window, scene);
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {

        // Update camera based on mouse            
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }

        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        //Update scene
        scene.update();

        //Update
        for (int i = 0; i < scene.getGameItems().size(); i++) {
            GameItem currElement = scene.getGameItems().get(i);
            currElement.update(interval, scene);
        }

        Iterator<GameItem> it = scene.getGameItems().iterator();
        while (it.hasNext()){
            GameItem item = it.next();
            if (item.getClass() == Bullet.class) {
                for (GameItem item_ : scene.getGameItems()) {
                    if (item.isCollision(item_) && item_ != item) {
                        if (item_.getClass() != TIEFighter.class && ((Bullet)item).isPlayers()){
                            score += 10;
                        }
                        if (!((Bullet)item).isPlayers() && item_.getClass() != TIEFighter.class){
                            break;
                        }
                        item.hit();
                        item_.hit();
                    }
                }
            }
            if (item.isNeedToRemove()){
                it.remove();
            }
        }

        //Update hud
        hud.setStatusText(Integer.toString(score));
    }

    @Override
    public void render(Window window) {
        hud.updateSize(window);
        renderer.render(window, camera, scene, hud);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        Map<Mesh, List<GameItem>> mapMeshes = scene.getGameMeshes();
        for (Mesh mesh : mapMeshes.keySet()) {
            mesh.cleanUp();
        }
        hud.cleanup();
    }

}
