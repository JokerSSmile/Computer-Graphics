package game;

import java.awt.Font;

import org.joml.Vector3f;
import engine.entity.GameItem;
import engine.IHud;
import engine.entity.TextItem;
import engine.Window;
import engine.graph.FontTexture;

public class Hud implements IHud {

    private static final Font FONT = new Font("Arial", Font.PLAIN, 20);
    private static final String CHARSET = "ISO-8859-1";

    private final GameItem[] gameItems;
    private final TextItem scoreTextItem;
    private final TextItem resultTextItem;

    Hud(String statusText, String resultText) throws Exception {
        FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        scoreTextItem = new TextItem(statusText, fontTexture);
        scoreTextItem.getMesh().getMaterial().setColour(new Vector3f(1, 1, 1));
        resultTextItem = new TextItem(resultText, fontTexture);
        resultTextItem.getMesh().getMaterial().setColour(new Vector3f(1, 1, 1));
        resultTextItem.setScale(1.5f);

        // Create list that holds the items that compose the HUD
        gameItems = new GameItem[]{scoreTextItem, resultTextItem};
    }

    void setStatusText(String statusText) {
        scoreTextItem.setText(statusText);
    }

    void setResultText(String resultText){
        resultTextItem.setText(resultText);
    }

    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }
   
    void updateSize(Window window) {
        this.scoreTextItem.setPosition(10f, window.getHeight() - 50f, 0);
        this.resultTextItem.setPosition(window.getWidth() / 2 - 100, window.getHeight() / 2, 0);
    }
}
