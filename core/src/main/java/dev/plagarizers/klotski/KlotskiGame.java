package dev.plagarizers.klotski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;
import dev.plagarizers.klotski.gui.screens.MainMenuScreen;
import dev.plagarizers.klotski.gui.screens.TutorialScreen;
import dev.plagarizers.klotski.gui.util.FontHandler;
import dev.plagarizers.klotski.gui.util.SoundHandler;

/**
 * The KlotskiGame class represents the main game class that extends the Game class from libGDX.
 * It manages the game's initialization, resources, screens, and settings.
 */
public class KlotskiGame extends Game {
    public static final String SKIN_PATH = "skins/default/uiskin.json";
    public static final String PREVIEWS_PATH = "levels/previews/";
    private final String backgroundImageTexturePath = "textures/background.png";
    private OrthographicCamera camera;
    private Skin gameSkin;

    @Override
    public void create() {
        // Load the game's skin
        gameSkin = new Skin(Gdx.files.internal(SKIN_PATH));
        // Setup the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        Preferences prefs = Gdx.app.getPreferences("tutorial_preferences");
        if(prefs.getBoolean("done")) {
            // Set the initial screen to the main menu screen
            this.setScreen(new MainMenuScreen(this));
        } else {
            this.setScreen(new TutorialScreen(this, prefs));
        }

    }

    /**
     * Returns the background image of the game as an Image widget.
     *
     * @return The background Image.
     */
    public Image getBackground() {
        Image background = new Image(new Texture(Gdx.files.internal(backgroundImageTexturePath)));
        background.setFillParent(true);
        background.setScaling(Scaling.fill);
        return background;
    }

    /**
     * Returns the game's camera.
     *
     * @return The OrthographicCamera instance.
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Returns the game's skin.
     *
     * @return The Skin instance representing the game's skin.
     */
    public Skin getSkin() {
        return gameSkin;
    }

    @Override
    public void dispose() {
        // Dispose of resources when the game is closing
        gameSkin.dispose();
        FontHandler.getInstance().dispose();
        SoundHandler.getInstance().dispose();
    }
}
