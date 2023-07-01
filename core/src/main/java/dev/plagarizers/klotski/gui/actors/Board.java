package dev.plagarizers.klotski.gui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.gui.listeners.BoardListener;
import dev.plagarizers.klotski.gui.state.GameState;

import java.util.List;

public class Board extends Actor {
    public static final float ITEM_WIDTH = 64;
    public static final float ITEM_HEIGHT = 64;
    private final Texture boardTexture;
    private final Label movesLabel;
    private final BoardListener boardListener;
    private final GameState gameState;
    private final String boardTexturePath = "textures/board.png";

    public Board(State state, Label.LabelStyle labelStyle) {
        this.gameState = new GameState(state);
        boardListener = new BoardListener(this, gameState);
        boardTexture = new Texture(Gdx.files.internal(boardTexturePath));
        
        movesLabel = new Label("Moves: ", labelStyle);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        movesLabel.setText("Moves: " + gameState.getMoves());

        for (Tile tile : gameState.getTiles()) {
            float tileX = getX() + tile.getX();
            float tileY = getY() + tile.getY();

            batch.draw(tile.getTexture(), tileX, tileY, tile.getWidth(), tile.getHeight());
            if (gameState.getSelectedTile() == tile) {
                batch.draw(tile.getContourTexture(), tileX, tileY, tile.getWidth(), tile.getHeight());
            }
        }

        movesLabel.setPosition(getX() - ITEM_WIDTH, getY() - ITEM_HEIGHT * 4);
        movesLabel.draw(batch, parentAlpha);
        batch.draw(boardTexture, getX() - ITEM_WIDTH * 3, getY() - ITEM_HEIGHT * 3 - ITEM_HEIGHT / 2f, (State.COLS + 2) * ITEM_WIDTH, (State.ROWS + 2.5f) * ITEM_HEIGHT);

    }

    public State getState() {
        return gameState.getState();
    }


    public List<Tile> getTiles() {
        return gameState.getTiles();
    }

    public GameState getGameState() {
        return gameState;
    }

    public BoardListener getBoardListener() {
        return boardListener;
    }
}
