package dev.plagarizers.klotski.gui.state;

import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.KlotskiSolver;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Direction;
import dev.plagarizers.klotski.gui.actors.Board;
import dev.plagarizers.klotski.gui.actors.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameState {
    private State state;
    private List<Tile> tiles;
    private Tile selectedTile;
    private State startingConfiguration;
    private Stack<State> previousStates;
    private List<State> pathToSolution;


    public GameState(State state) {
        this.startingConfiguration = state.clone();
        this.state = state.clone();
        this.tiles = new ArrayList<>();

        previousStates = new Stack<>();
        previousStates.push(state.clone());

        pathToSolution = new ArrayList<>();
        updateTiles();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }

    public int getMoves() {
        return state.getMoves();
    }

    public boolean moveBlock(Block block, Direction direction) {

        previousStates.push(state.clone());

        boolean result = state.moveBlock(block, direction);

        if (!result) previousStates.pop();


        if (result) {
            updateTiles();
        }


        return result;
    }

    public void undoMove() {
        if (previousStates.size() <= 1) return;
        state = previousStates.pop().clone();
        updateTiles();
    }

    public void selectNextTile() {
        if (selectedTile == null) {
            // If no tile is currently selected, select the first tile
            if (!tiles.isEmpty()) {
                selectedTile = tiles.get(0);
            }
        } else {
            // Find the index of the currently selected tile
            int selectedBlockIndex = tiles.indexOf(selectedTile);

            // Select the next tile in the list, or wrap around to the first tile
            int nextIndex = (selectedBlockIndex + 1) % tiles.size();
            selectedTile = tiles.get(nextIndex);
        }
        updateTiles();
    }

    public void reset() {
        previousStates.clear();
        previousStates.push(startingConfiguration.clone());
        previousStates.push(state.clone());
        state = startingConfiguration.clone();
        resetMoves();
        updateTiles();
    }

    public void resetMoves() {
        state.setMoves(0);
    }

    public void updateTiles() {
        tiles.clear();
        for (Block block : state.getBlocks()) {
            tiles.add(createTile(block));
        }

    }

    public Tile createTile(Block block) {
        int x = block.getY(), y = block.getX(), width = block.getWidth(), height = block.getHeight();
        // need to swap x and y, to convert coordinate from grid based to screen based
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

    public boolean isSolved() {
        return state.isSolved();
    }

    public void playBestMove() {
        if (isSolved()) return;

        // recalculate the path to solution

        calculatePathToSolution();

        if (pathToSolution.isEmpty()) return;

        previousStates.push(state.clone());
        State next = pathToSolution.remove(0);
        next.setMoves(state.getMoves() + 1);
        state = next.clone();
        selectedTile = null;

        updateTiles();
    }

    private void calculatePathToSolution() {
        KlotskiSolver solver = new KlotskiSolver(state.clone());
        pathToSolution.clear();
        pathToSolution.addAll(solver.getPathToSolution());
    }

}
