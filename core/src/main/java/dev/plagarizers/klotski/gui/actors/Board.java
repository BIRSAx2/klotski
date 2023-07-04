package dev.plagarizers.klotski.gui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Level;
import dev.plagarizers.klotski.gui.listeners.BoardListener;
import dev.plagarizers.klotski.gui.state.GameState;
import dev.plagarizers.klotski.gui.util.FontHandler;

import java.util.List;

/**
 * The `Board` class represents a game board that extends the `Actor` class.
 * It is responsible for rendering the game board, managing the game state, and handling user input.
 */
public class Board extends Actor implements Disableable {
    public static final float ITEM_WIDTH = 64;
    public static final float ITEM_HEIGHT = 64;
    private boolean isDisabled;
    private final Texture boardTexture;
    private final Label movesLabel;
    private final Label levelLabel;
    private final BoardListener boardListener;
    private final GameState gameState;
    private final String boardTexturePath = "textures/board.png";

    /**
     * Constructs a new `Board` object with the specified initial state and label style.
     *
     * @param level the initial game state
     */
    public Board(Level level) {
        this.gameState = new GameState(level.toState());
        boardListener = new BoardListener(this, gameState);
        boardTexture = new Texture(Gdx.files.internal(boardTexturePath));

        movesLabel = new Label("Moves: ", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        levelLabel = new Label("Level: " + level.getName(), FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
    }

    /**
     * Renders the game board and its components.
     *
     * @param batch       the sprite batch to render to
     * @param parentAlpha the parent alpha value
     */
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

        batch.draw(boardTexture, getX() - ITEM_WIDTH * 3, getY() - ITEM_HEIGHT * 3 - ITEM_HEIGHT / 2f, (State.COLS + 2) * ITEM_WIDTH, (State.ROWS + 2.5f) * ITEM_HEIGHT);

        movesLabel.setPosition(getX() - ITEM_WIDTH, getY() - ITEM_HEIGHT * 4);
        movesLabel.draw(batch, parentAlpha);

        levelLabel.setPosition(getX() - ITEM_WIDTH, getY() + ITEM_HEIGHT * 4);
        levelLabel.draw(batch, parentAlpha);


    }

    /**
     * Returns the current game state.
     *
     * @return the game state
     */
    public State getState() {
        return gameState.getState();
    }

    /**
     * Returns a list of tiles on the game board.
     *
     * @return the list of tiles
     */
    public List<Tile> getTiles() {
        return gameState.getTiles();
    }

    /**
     * Returns the game state object.
     *
     * @return the game state object
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Returns the board listener associated with this board.
     *
     * @return the board listener
     */
    public BoardListener getBoardListener() {
        return boardListener;
    }

    @Override
    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Override
    public boolean isDisabled() {
        return this.isDisabled;
    }
}
