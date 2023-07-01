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

public class BoardPreview extends Actor {


    private int rows;

    private int columns;

    private float itemWidth = 32;
    private float itemHeight = 32;

    private Label levelLabel;

    private Skin skin;

    private Level level;

    public BoardPreview(Level level, Skin skin, Label.LabelStyle labelStyle) {
        this.level = level;
        this.rows = State.ROWS;
        this.columns = State.COLS;
        this.skin = skin;
        levelLabel = new Label("Level: " + level.getName(), labelStyle);
        this.setWidth((columns + 1) * itemWidth);
        this.setHeight((rows + 2) * itemHeight);

    }

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
