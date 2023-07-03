package dev.plagarizers.klotski.game.util;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.State;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * The `Level` class represents a game level with a name and a game board.
 */
public class Level {

    @SerializedName("level")
    private String name;

    @SerializedName("board")
    private Block[] blocks;

    private transient boolean isCompleted;

    /**
     * Constructs a `Level` object with the specified name and game board.
     *
     * @param name   the name of the level
     * @param blocks the game board
     */
    public Level(String name, Block[] blocks) {
        this.name = name;
        this.blocks = blocks;
    }

    public Level(State state, String name) {
        this.name = name;
        this.blocks = state.getBlocks();
    }

    /**
     * Converts a JSON string representation of a list of `Level` objects into a list of `Level` instances.
     *
     * @param json the JSON string representing the levels
     * @return a list of `Level` objects deserialized from the JSON string
     */
    public static List<Level> fromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Level>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /**
     * Retrieves the name of the level.
     *
     * @return the name of the level
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the level.
     *
     * @param name the name of the level
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the game board of the level.
     *
     * @return the game board
     */
    public Block[] getBlocks() {
        return blocks;
    }

    /**
     * Sets the game board of the level.
     *
     * @param blocks the game board
     */
    public void setBlocks(Block[] blocks) {
        this.blocks = blocks;
    }

    /**
     * Returns a string representation of the `Level` object.
     *
     * @return a string representation of the `Level` object
     */
    @Override
    public String toString() {
        return "Level{" + "name='" + name + '\'' + ", board=" + Arrays.toString(blocks) + '}';
    }

    /**
     * Converts the `Level` object to a `State` object representing the game state.
     *
     * @return a `State` object representing the game state
     */
    public State toState() {
        State state = State.fromDefaultConfiguration();
        state.setBlocks(blocks);
        return state;
    }


    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
