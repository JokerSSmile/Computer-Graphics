package game;

import java.awt.Font;

import org.joml.Vector3f;
import engine.entity.GameItem;
import engine.entity.TextItem;
import engine.Window;
import engine.graph.FontTexture;

public class Hud  {

    private static final Font FONT = new Font("Arial", Font.PLAIN, 20);
    private static final String CHARSET = "ISO-8859-1";

    private final GameItem[] gameItems;
    private TextItem scoreTextItem;
    private TextItem resultTextItem;
    private TextItem healthText;

    Hud(String statusText, String resultText, String hpText) throws Exception {
        FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        scoreTextItem = new TextItem(statusText, fontTexture);
        scoreTextItem.getMesh().getMaterial().setColour(new Vector3f(1, 1, 1));
        resultTextItem = new TextItem(resultText, fontTexture);
        resultTextItem.getMesh().getMaterial().setColour(new Vector3f(1, 1, 1));
        resultTextItem.setScale(1.5f);
        healthText = new TextItem(hpText, fontTexture);
        healthText.getMesh().getMaterial().setColour(new Vector3f(1, 0, 0));

        // Create list that holds the items that compose the HUD
        gameItems = new GameItem[]{scoreTextItem, resultTextItem, healthText};
    }

    void setStatusText(String statusText) {
        scoreTextItem.setText(statusText);
    }

    void setResultText(String resultText){
        resultTextItem.setText(resultText);
    }

    void setHealthText(String hpText) {
        healthText.setText(hpText);
    }

    public GameItem[] getGameItems() {
        return gameItems;
    }
   
    void updateSize(Window window) {
        this.scoreTextItem.setPosition(10f, window.getHeight() - 50f, 0);
        this.resultTextItem.setPosition(window.getWidth() / 2 - 100, window.getHeight() / 2, 0);
        this.healthText.setPosition(window.getWidth() - 75, 20, 0);
    }

    void cleanup() {
        GameItem[] gameItems = getGameItems();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }
}
