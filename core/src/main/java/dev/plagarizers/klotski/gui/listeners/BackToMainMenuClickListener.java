package dev.plagarizers.klotski.gui.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.gui.screens.MainMenuScreen;

public class BackToMainMenuClickListener extends ClickListener {

    private final KlotskiGame game;

    public BackToMainMenuClickListener(KlotskiGame game) {
        this.game = game;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        game.buttonPressedPlay();
        game.setScreen(new MainMenuScreen(game));
    }
}
