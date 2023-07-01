package dev.plagarizers.klotski.gui.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * The SoundHandler class is responsible for managing the game's sounds and music.
 * It provides methods to play button pressed sounds, control music volume, and effects volume.
 */
public class SoundHandler {

    private static SoundHandler instance = null;
    private final String backgroundMusicPath = "sounds/background_music.mp3";
    private final String buttonPressedSoundPath = "sounds/button_pressed_sound.mp3";

    private final String pieceMoveSoundPath = "sounds/piece_move_sound.mp3";
    private final float defaultVolume = 0.5f;

    private float effectsVolume = 0.5f;
    private Music backgroundMusic;
    private Sound buttonPressedSound;
    private Sound pieceMovedSound;

    private SoundHandler() {
        // Load and play the background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(backgroundMusicPath));
        backgroundMusic.setVolume(defaultVolume);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        // Load the button pressed sound
        buttonPressedSound = Gdx.audio.newSound(Gdx.files.internal(buttonPressedSoundPath));

        // Load the piece moved sound
        pieceMovedSound = Gdx.audio.newSound(Gdx.files.internal(pieceMoveSoundPath));
    }

    /**
     * Returns the singleton instance of the SoundHandler.
     *
     * @return The SoundHandler instance.
     */
    public static SoundHandler getInstance() {
        if (instance == null) instance = new SoundHandler();
        return instance;
    }

    /**
     * Plays the button pressed sound with the current effects volume.
     */
    public void playButtonClick() {
        buttonPressedSound.play(effectsVolume);
    }


    /**
     * Plays the piece moved sound with the current effects volume.
     */
    public void playPieceMoved() {
        pieceMovedSound.play(effectsVolume);
    }

    /**
     * Returns the current volume of the background music.
     *
     * @return The volume of the background music, ranging from 0 to 100.
     */
    public float getMusicVolume() {
        return backgroundMusic.getVolume() * 100f;
    }

    /**
     * Sets the volume of the background music.
     *
     * @param musicVolume The volume of the background music, ranging from 0 to 100.
     */
    public void setMusicVolume(float musicVolume) {
        backgroundMusic.setVolume(musicVolume / 100f);
    }

    /**
     * Returns the current volume of the sound effects.
     *
     * @return The volume of the sound effects, ranging from 0 to 100.
     */
    public float getEffectsVolume() {
        return effectsVolume * 100f;
    }

    /**
     * Sets the volume of the sound effects.
     *
     * @param effectsVolume The volume of the sound effects, ranging from 0 to 100.
     */
    public void setEffectsVolume(float effectsVolume) {
        this.effectsVolume = effectsVolume / 100f;
    }


}
