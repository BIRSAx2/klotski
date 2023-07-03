package dev.plagarizers.klotski.gui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Level;
import dev.plagarizers.klotski.gui.util.FontHandler;

/**
 * The `BoardPreview` class represents a preview of a game board.
 * It extends the `Actor` class and is responsible for rendering the board preview and its components.
 */
public class BoardPreview extends Actor {

    private int rows;
    private int columns;
    private float itemWidth = 32;
    private float itemHeight = 32;
    private Label levelLabel;
    private Level level;
    private Texture boardPreview;

    /**
     * Constructs a new `BoardPreview` object with the specified level, skin, and label style.
     *
     * @param level      the level to preview
     * @param skin       the skin for UI components
     */
    public BoardPreview(Level level, Skin skin) {
        this.level = level;
        this.rows = State.ROWS;
        this.columns = State.COLS;
        levelLabel = new Label("Level: " + level.getName(), FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        this.setWidth((columns + 1) * itemWidth);
        this.setHeight((rows + 2) * itemHeight);
        boardPreview = new Texture(Gdx.files.internal(KlotskiGame.PREVIEWS_PATH + level.getName() + ".png"));
    }

    /**
     * Renders the board preview and its components.
     *
     * @param batch       the sprite batch to render to
     * @param parentAlpha the parent alpha value
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        levelLabel.setFillParent(true);
        levelLabel.setPosition(getX(), getY() + getHeight() - levelLabel.getHeight());
        levelLabel.draw(batch, parentAlpha);

        batch.draw(boardPreview, getX(), getY(), getWidth() - 2, getHeight() - itemHeight);

        if (level.isCompleted()) {
            BitmapFont font = FontHandler.getInstance().getFont(FontHandler.FontType.Menu);
            font.setColor(Color.GREEN);
            font.draw(batch, "COMPLETED", getX() + 10, getY() + getHeight() / 2);

        }
    }
}
