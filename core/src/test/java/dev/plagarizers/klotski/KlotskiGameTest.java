package dev.plagarizers.klotski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KlotskiGameTest {

    private static HeadlessApplication headlessApplication;
    private static KlotskiGame game;

    @BeforeAll
    public static void setUp() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        headlessApplication = new HeadlessApplication(new KlotskiGame(), config);
        game = (KlotskiGame) headlessApplication.getApplicationListener();
    }

    @AfterAll
    public static void tearDown() {
        // Dispose the headless environment after running the tests
        headlessApplication.exit();
        headlessApplication = null;
    }

    @Test
    void testCreate() {
        // Perform assertions to test the initialization in the create() method
//        assertNotNull(game.getCamera());
//        assertNotNull(game.getSkin());
        // Add more assertions as needed
    }

    @Test
    void testToggleDebug() {
        // Test the toggleDebug() method
        boolean initialDebugState = game.debug();
        game.toggleDebug();
        boolean toggledDebugState = game.debug();
        assertNotEquals(initialDebugState, toggledDebugState);
    }

    @Test
    void testButtonPressedPlay() {

    }

    // Add more test methods for other functionalities of the KlotskiGame class

}

