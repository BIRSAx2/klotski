package dev.plagarizers.klotski.gui.state;

import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.KlotskiSolver;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Direction;
import dev.plagarizers.klotski.gui.actors.Board;
import dev.plagarizers.klotski.gui.actors.Tile;
import dev.plagarizers.klotski.gui.util.SoundHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The `GameState` class represents the state of the Klotski game.
 * It contains information about the current state, moves, selected tile, and handles game logic.
 */
public class GameState {

    private State state;
    private List<Tile> tiles;
    private Tile selectedTile;
    private State startingConfiguration;
    private Stack<State> previousStates;
    private List<State> pathToSolution;

    /**
     * Constructs a new `GameState` with the specified initial state.
     *
     * @param state the initial state of the game
     */
    public GameState(State state) {
        this.startingConfiguration = state.clone();
        this.state = state.clone();
        this.tiles = new ArrayList<>();
        previousStates = new Stack<>();
        previousStates.push(state.clone());
        pathToSolution = new ArrayList<>();
        updateTiles();
    }

    /**
     * Returns the current state of the game.
     *
     * @return the current state
     */
    public State getState() {
        return state;
    }

    /**
     * Sets the state of the game.
     *
     * @param state the new state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Returns the list of tiles in the game.
     *
     * @return the list of tiles
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Returns the currently selected tile.
     *
     * @return the selected tile
     */
    public Tile getSelectedTile() {
        return selectedTile;
    }

    /**
     * Sets the currently selected tile.
     *
     * @param selectedTile the selected tile
     */
    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }

    /**
     * Returns the number of moves made in the game.
     *
     * @return the number of moves
     */
    public int getMoves() {
        return state.getMoves();
    }

    /**
     * Moves the specified block in the given direction.
     *
     * @param block     the block to move
     * @param direction the direction to move the block
     * @return true if the block was moved successfully, false otherwise
     */
    public boolean moveBlock(Block block, Direction direction) {
        previousStates.push(state.clone());
        boolean result = state.moveBlock(block, direction);
        if (!result) previousStates.pop();
        if (result) {
            updateTiles();
            SoundHandler.getInstance().playPieceMoved();
        }
        return result;
    }

    /**
     * Undoes the last move made in the game.
     */
    public void undoMove() {
        if (previousStates.size() <= 1) return;
        state = previousStates.pop().clone();
        updateTiles();
    }

    /**
     * Selects the next tile in the game.
     */
    public void selectNextTile() {
        if (selectedTile == null) {
            if (!tiles.isEmpty()) {
                selectedTile = tiles.get(0);
            }
        } else {
            int selectedBlockIndex = tiles.indexOf(selectedTile);
            int nextIndex = (selectedBlockIndex + 1) % tiles.size();
            selectedTile = tiles.get(nextIndex);
        }
        updateTiles();
    }

    /**
     * Resets the game to its starting configuration.
     */
    public void reset() {
        previousStates.clear();
        previousStates.push(startingConfiguration.clone());
        previousStates.push(state.clone());
        state = startingConfiguration.clone();
        resetMoves();
        updateTiles();
    }

    /**
     * Resets the number of moves made in the game.
     */
    public void resetMoves() {
        state.setMoves(0);
    }

    /**
     * Updates the tiles based on the current state.
     */
    public void updateTiles() {
        tiles.clear();
        for (Block block : state.getBlocks()) {
            tiles.add(createTile(block));
        }
    }

    /**
     * Creates a tile based on the specified block.
     *
     * @param block the block to create a tile for
     * @return the created tile
     */
    public Tile createTile(Block block) {
        int x = block.getY(), y = block.getX(), width = block.getWidth(), height = block.getHeight();
        y = State.ROWS - y - height;
        float gridOffsetX = (State.COLS * Board.ITEM_WIDTH) / 2;
        float gridOffsetY = (State.ROWS * Board.ITEM_HEIGHT) / 2;
        float tileX = (x * Board.ITEM_WIDTH) - gridOffsetX;
        float tileY = (y * Board.ITEM_HEIGHT) - gridOffsetY;
        float tileWidth = width * Board.ITEM_WIDTH;
        float tileHeight = height * Board.ITEM_HEIGHT;
        Tile tile = new Tile(tileX, tileY, tileWidth, tileHeight);
        tile.setBlock(block);
        if (selectedTile != null && selectedTile.getBlock().equals(block)) {
            selectedTile = tile;
        }
        return tile;
    }

    /**
     * Checks if the game is solved.
     *
     * @return true if the game is solved, false otherwise
     */
    public boolean isSolved() {
        return state.isSolved();
    }

    /**
     * Plays the best move based on the calculated path to the solution.
     */
    public void playBestMove() {
        if (isSolved()) return;
        calculatePathToSolution();
        if (pathToSolution.isEmpty()) return;
        previousStates.push(state.clone());
        State next = pathToSolution.remove(0);
        next.setMoves(state.getMoves() + 1);
        state = next.clone();
        selectedTile = null;
        updateTiles();
    }

    /**
     * Calculates the path to the solution using a solver.
     */
    private void calculatePathToSolution() {
        KlotskiSolver solver = new KlotskiSolver(state.clone());
        pathToSolution.clear();
        pathToSolution.addAll(solver.getPathToSolution());
    }
}
