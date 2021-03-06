package engine;

import java.util.*;

import engine.entity.GameItem;
import engine.entity.SkyBox;
import engine.graph.Mesh;

public class Scene {

    private Map<Mesh, List<GameItem>> meshMap;
    private List<GameItem> gameItems;
    private SkyBox skyBox;
    private SceneLight sceneLight;
    private SceneLight backgroundLight;

    public Scene() {
        meshMap = new HashMap<>();
        gameItems = new Vector<>();
    }
    public Map<Mesh, List<GameItem>> getGameMeshes() {
        return meshMap;
    }

    public void addGameItem(GameItem gameItem){

        gameItems.add(gameItem);
        Mesh mesh = gameItem.getMesh();
        List<GameItem> list = meshMap.computeIfAbsent(mesh, k -> new ArrayList<>());
        list.add(gameItem);
    }

    public void update(){

        for (Map.Entry<Mesh, List<GameItem>> entry : meshMap.entrySet()) {
            List<GameItem> list = entry.getValue();
            list.removeIf(GameItem::isNeedToRemove);
        }
    }

    public SkyBox getSkyBox() {
        return skyBox;
    }

    public void setSkyBox(SkyBox skyBox) {
        this.skyBox = skyBox;
    }

    public SceneLight getSceneLight() {
        return sceneLight;
    }

	public SceneLight getBackGroundLight() {
		return backgroundLight;
	}

    public void setSceneLight(SceneLight sceneLight) {
        this.sceneLight = sceneLight;
    }

	public void setBackgroundLight(SceneLight sceneLight) {
		this.backgroundLight = sceneLight;
	}

    public List<GameItem> getGameItems() {
        return gameItems;
    }
}
