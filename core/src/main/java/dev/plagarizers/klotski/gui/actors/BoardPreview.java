package dev.plagarizers.klotski.gui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Level;

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
    private Skin skin;
    private Level level;

    /**
     * Constructs a new `BoardPreview` object with the specified level, skin, and label style.
     *
     * @param level      the level to preview
     * @param skin       the skin for UI components
     * @param labelStyle the style for the level label
     */
    public BoardPreview(Level level, Skin skin, Label.LabelStyle labelStyle) {
        this.level = level;
        this.rows = State.ROWS;
        this.columns = State.COLS;
        this.skin = skin;
        levelLabel = new Label("Level: " + level.getName(), labelStyle);
        this.setWidth((columns + 1) * itemWidth);
        this.setHeight((rows + 2) * itemHeight);
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
        ImageButton levelButton = new ImageButton(skin);
        Texture image = new Texture(Gdx.files.internal(KlotskiGame.PREVIEWS_PATH + level.getName() + ".png"));

        levelButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(image));
        levelButton.setBackground(new Image(image).getDrawable());
        batch.draw(image, getX(), getY(), getWidth() - 2, getHeight() - itemHeight);
    }
}
