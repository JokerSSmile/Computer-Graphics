package game;

import engine.entity.GameEngine;
import org.joml.Vector2i;

public class Main {

    private static final String TITLE = "GAME";
    private static final Vector2i WINDOW_SIZE = new Vector2i(1280, 720);
 
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            StarWars starWars = new StarWars();
            GameEngine gameEng = new GameEngine(TITLE, WINDOW_SIZE.x, WINDOW_SIZE.y, vSync, starWars);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}