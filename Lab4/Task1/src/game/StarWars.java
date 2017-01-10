package game;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import engine.entity.*;
import engine.graph.Renderer;
import org.joml.Vector3f;

import engine.Scene;
import engine.SceneLight;
import engine.Window;
import engine.graph.Camera;
import engine.graph.DirectionalLight;
import engine.graph.Material;
import engine.graph.Mesh;
import engine.graph.OBJLoader;

public class StarWars {

    private final Renderer renderer;
    private final Camera camera;
    private Scene scene;
    private Hud hud;
    private int score;
    private boolean isGameEnd;

    StarWars() {
        renderer = new Renderer();
        camera = new Camera();
    }

    public void init() throws Exception {
        renderer.init();

        scene = new Scene();
        
        // Setup  GameItems
        float reflectance = 1f;
        Mesh tieFighterMesh = OBJLoader.loadMesh("src/resources/models/TIE-fighter1.obj");
        Mesh deathStarMesh = OBJLoader.loadMesh("src/resources/models/deathstar.obj");
        Material darkMaterial = new Material(new Vector3f(0.2f, 0.2f, 0.2f), reflectance);
        tieFighterMesh.setMaterial(darkMaterial);

        //Init Death Star
        DeathStar deathStar = new DeathStar(deathStarMesh);

        //Initialize player
        TIEFighter tieFighter = new TIEFighter(tieFighterMesh);

        //Fill game items array
        scene.addGameItem(tieFighter);
        scene.addGameItem(deathStar);
        initializeEnemies();

        // Setup  SkyBox
        float skyBoxScale = 500f;
        SkyBox skyBox = new SkyBox("src/resources/models/skybox.obj", "src/resources/textures/skybox1.png");
        skyBox.setScale(skyBoxScale);
        scene.setSkyBox(skyBox);
        
        // Setup Lights
        setupLights();
        
        // Create HUD
        hud = new Hud(Integer.toString(score), "", "");
    }

    private void initializeEnemies(){

        float reflectance = 1f;

        try {
            Mesh xWingMesh = OBJLoader.loadMesh("src/resources/models/X-wing1.obj");
            Mesh mFalconMesh = OBJLoader.loadMesh("src/resources/models/MILLENIUM-falcon.obj");
            Material lightMaterial = new Material(new Vector3f(0.5f, 0.5f, 0.5f), reflectance);
            xWingMesh.setMaterial(lightMaterial);
            mFalconMesh.setMaterial(lightMaterial);

            int enemyRows = 4;
            int enemyColumns = 6;
            for (int i = 0; i < enemyColumns; i++) {
                for (int k = 0; k < enemyRows; k++) {
                    if (k != 0) {
                        XWing xWing = new XWing(xWingMesh);
                        xWing.setPosition(i * 32 - 100, -5, k * 40 - 250);
                        scene.addGameItem(xWing);
                    } else {
                        MillenuimFalcon mFalcon = new MillenuimFalcon(mFalconMesh);
                        mFalcon.setPosition(i * 32 - 100, -5, k * 40 - 250);
                        scene.addGameItem(mFalcon);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error while initialize enemies");
        }
    }
    
    private void setupLights() {
        SceneLight sceneLight = new SceneLight();
        SceneLight bgLight = new SceneLight();
        scene.setSceneLight(sceneLight);
        scene.setBackgroundLight(bgLight);

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(1.0f, 1.0f, 1.0f));
        bgLight.setAmbientLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 1.0f;
        Vector3f lightPosition = new Vector3f(100, 0, 0);
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity));
        Vector3f bgLightPosition = new Vector3f(-100, 100, -100);
        bgLight.setDirectionalLight(new DirectionalLight(new Vector3f(1,  0, 0), bgLightPosition, lightIntensity));
    }

    public void input(Window window) {

        //Player input
        TIEFighter player = null;
        for (GameItem item : scene.getGameItems()){
            if (item.getClass() == TIEFighter.class){
                player = (TIEFighter)item;
                break;
            }
        }
        if (player != null) {
            player.input(window, scene);
            hud.setHealthText("Lifes: " + Integer.toString(player.getHealth()));
        } else {
            isGameEnd = true;
            hud.setHealthText("");
        }
    }

    public void update(float interval) {

        //Update scene
        scene.update();

        //Update
        for (int i = 0; i < scene.getGameItems().size(); i++) {
            GameItem currElement = scene.getGameItems().get(i);
            currElement.update(interval, scene);
        }
        updateItems();

        //Update hud
        hud.setStatusText( "Score: " + Integer.toString(score));
        if (isGameEnd){
            hud.setResultText("Your score is " + Integer.toString(score));
            hud.setStatusText("");
        }


    }

    private void updateItems(){

        int enemiesLeft = 0;
        Iterator<GameItem> it = scene.getGameItems().iterator();
        while (it.hasNext()){
            GameItem item = it.next();
            if (item.getClass() == XWing.class || item.getClass() == MillenuimFalcon.class){
                enemiesLeft++;
            }
            if (item.getClass() == Bullet.class) {
                for (GameItem item_ : scene.getGameItems()) {
                    if (item.isCollision(item_) && item_ != item && item_.getClass() != DeathStar.class) {
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

        if (enemiesLeft == 0){
            initializeEnemies();
        }
    }

    public void render(Window window) {
        hud.updateSize(window);
        renderer.render(window, camera, scene, hud);
    }

    public void cleanup() {
        renderer.cleanup();
        Map<Mesh, List<GameItem>> mapMeshes = scene.getGameMeshes();
        for (Mesh mesh : mapMeshes.keySet()) {
            mesh.cleanUp();
        }
        hud.cleanup();
    }

}
