package dev.plagarizers.klotski.gui.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;
import dev.plagarizers.klotski.gui.screens.GameScreen;


public class StartFromSaveClickListener extends ClickListener {
    private String saveName;
    private SavesManager savesManager;
    private KlotskiGame game;

    public StartFromSaveClickListener(String saveName, KlotskiGame game) {
        this.saveName = saveName;
        this.game = game;
        this.savesManager = new SavesManager(Gdx.files.getExternalStoragePath());
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        Gdx.app.log("LoadMenuScreen", "Clicked on " + event.getTarget());
        game.buttonPressedPlay();
        State save = savesManager.loadStateByName(saveName);
        Gdx.app.log("LoadMenuScreen", "Loaded state from save: " + saveName);
        game.setScreen(new GameScreen(game, save));
    }
}
