package dev.plagarizers.klotski.game.state;


import com.google.gson.Gson;
import dev.plagarizers.klotski.game.block.*;
import dev.plagarizers.klotski.game.util.Coordinate;
import dev.plagarizers.klotski.game.util.Direction;
import dev.plagarizers.klotski.game.util.Level;
import dev.plagarizers.klotski.game.util.SavesManager;

import java.util.*;
import java.util.stream.Collectors;

public class State implements Cloneable {

    public final static int ROWS = 5, COLS = 4, NUM_PIECES = 10;
    public final static Coordinate GOAL = Coordinate.of(3, 1);
    private static final SavesManager savesManager = new SavesManager();
    private final HashMap<Coordinate, Block> blocks;
    private int moves = 0;


    /**
     * Constructs a new State object with an empty block collection.
     */
    private State() {
        this.blocks = new HashMap<>();
    }

    /**
     * Creates a new State object with the default configuration of blocks.
     *
     * @return a new State object with the default block configuration
     */
    public static State fromDefaultConfiguration() {
        Block[] base = new Block[NUM_PIECES];
        base[0] = new BigBlock(new Coordinate(0, 1));
        base[1] = new HorizontalBlock(new Coordinate(2, 1));
        base[2] = new VerticalBlock(new Coordinate(0, 3));
        base[3] = new VerticalBlock(new Coordinate(0, 0));
        base[4] = new VerticalBlock(new Coordinate(3, 0));
        base[5] = new VerticalBlock(new Coordinate(3, 3));
        base[6] = new SmallBlock(new Coordinate(3, 1));
        base[7] = new SmallBlock(new Coordinate(3, 2));
        base[8] = new SmallBlock(new Coordinate(4, 1));
        base[9] = new SmallBlock(new Coordinate(4, 2));

        State state = new State();

        state.setBlocks(base);
        return state;
    }

    /**
     * Creates a new State object with a random configuration loaded from the levels file.
     *
     * @return a new State object with a random configuration
     */
    public static State fromRandomConfiguration() {
        return fromRandomLevel().toState();
    }

    public static Level fromRandomLevel() {
        List<Level> levels = savesManager.loadLevelsFromDefaultPath();

        Random random = new Random();
        int index = random.nextInt(levels.size());
        return levels.get(index);
    }

    /**
     * Checks if the given coordinate is a valid coordinate within the board boundaries.
     *
     * @param coordinate The coordinate to check.
     * @return {@code true} if the coordinate is valid, {@code false} otherwise.
     */
    public static boolean isValidCoordinate(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < ROWS && coordinate.getY() >= 0 && coordinate.getY() < COLS;
    }

    /**
     * Creates a new State object from a JSON string representation.
     *
     * @param json the JSON string representation of the state
     * @return a new State object created from the JSON string representation
     */
    @SuppressWarnings("unchecked")
    public static State fromJson(String json) {
        Gson gson = new Gson();

        HashMap<String, Object> save = gson.fromJson(json, HashMap.class);
        int moves = ((Double) save.get("moves")).intValue();
        List<Block> blockList = ((ArrayList<Block>) save.get("blocks"));
        State state = new State();

        Block[] blocks = gson.fromJson(gson.toJson(blockList), Block[].class);
        state.setBlocks(blocks);
        state.setMoves(moves);
        return state;
    }

    /**
     * Converts an array of bit board representations to a State object.
     *
     * @param bitBoard The array of bit board representations.
     * @return The corresponding State object.
     */
    public static State fromBitBoard(int[] bitBoard) {
        State state = new State();

        Block[] blocks = new Block[NUM_PIECES];
        for (int i = 0; i < NUM_PIECES; i++) {
            blocks[i] = createBlockFromBitMask(bitBoard[i]);
        }
        state.setBlocks(blocks);
        return state;
    }

    /**
     * Converts a block object to a bit mask representation.
     *
     * @param block The block object to convert.
     * @return The bit mask representation of the block.
     */
    public static int createBitMaskForBlock(Block block) {

        int mask = 0b0000_0000_0000_0000_0000;
        int x = block.getLocation().getX();
        int y = block.getLocation().getY();
        for (int row = x; row < x + block.getHeight(); row++) {
            for (int col = y; col < y + block.getWidth(); col++) {
                int index = 16 - (row * State.COLS) + col;
                // Set the corresponding bit to 1
                mask |= (1 << index);
            }
        }
        return mask;
    }

    /**
     * Converts a bit mask representation to a block object.
     *
     * @param mask The bit mask representation of the block.
     * @return The corresponding block object.
     */

    public static Block createBlockFromBitMask(int mask) {


        if (mask <= 0) {
            return null;
        }
        int y = -1, x = -1, width = -1, height = -1;

        for (int row = 0; row < State.ROWS; row++) {
            for (int col = 0; col < State.COLS; col++) {
                int bit = (mask >> (State.ROWS - row - 1) * State.COLS + col) & 1;

                if (bit == 1) {
                    if (y == -1) {
                        // Found the first cell of the block
                        y = col;
                        x = row;
                        width = 1;
                        height = 1;
                    } else {
                        // Expand the dimensions of the block if necessary
                        width = Math.max(width, col - y + 1);
                        height = Math.max(height, row - x + 1);
                    }
                }
            }
        }

        return new Block(Coordinate.of(x, y), height, width);
    }

    /**
     * Returns the number of moves made in the current state.
     *
     * @return the number of moves made
     */
    public int getMoves() {
        return moves;
    }

    /**
     * Sets the number of moves made in the current state.
     *
     * @param moves the number of moves to set
     */
    public void setMoves(int moves) {
        this.moves = moves;
    }

    /**
     * Checks if the current state is solved, i.e., if the big block is in the goal position.
     *
     * @return true if the state is solved, false otherwise
     */
    public boolean isSolved() {
        if (!blocks.containsKey(GOAL)) return false;
        return blocks.get(GOAL).equals(new BigBlock(GOAL));
    }

    /**
     * Checks if a block can be moved in the given direction in the current state.
     *
     * @param block     the block to move
     * @param direction the direction to move the block
     * @return true if the block can be moved, false otherwise
     */
    public boolean canMoveBlock(Block block, Direction direction) {

        Coordinate newTopLeft = block.getLocation().move(direction);

        if (!isValidBlock(newTopLeft, block.getWidth(), block.getHeight())) {
            return false;
        }
        for (Coordinate coordinate : block.getOccupiedLocations(newTopLeft)) {
            if (!blocks.containsKey(coordinate)) continue;
            if (!blocks.get(coordinate).equals(block)) return false;
        }
        return true;
    }

    /**
     * Moves a block in the given direction in the current state.
     *
     * @param block     the block to move
     * @param direction the direction to move the block
     * @return true if the block was moved, false otherwise
     */
    public boolean moveBlock(Block block, Direction direction) {


        if (!canMoveBlock(block, direction)) return false;

        for (Coordinate coordinate : block.getOccupiedLocations()) {
            blocks.remove(coordinate);
        }

        Coordinate newTopLeft = block.getLocation().move(direction);
        block.setLocation(newTopLeft);

        for (Coordinate coordinate : block.getOccupiedLocations()) {
            blocks.put(coordinate, block);
        }

        moves++;

        return true;
    }

    /**
     * Checks if the given topLeft is a valid coordinate within the board boundaries for a block with the given width and height.
     *
     * @param topLeft the topLeft to check
     * @param width   the width of the block
     * @param height  the height of the block
     * @return true if the topLeft is valid for the block, false otherwise
     */
    private boolean isValidBlock(Coordinate topLeft, int width, int height) {
        int x = topLeft.getX();
        int y = topLeft.getY();

        // Check if the topLeft is within the boundaries of the puzzle
        if (x >= 0 && y >= 0 && x <= ROWS && y <= COLS) {
            // Check if the topLeft is within the boundaries of the block
            return y + width <= COLS && x + height <= ROWS;
        }
        return false;
    }

    /**
     * Gets an array of blocks representing the current state.
     *
     * @return an array of blocks representing the current state
     */
    @SuppressWarnings("NewApi")
    public Block[] getBlocks() {
        HashSet<Block> set = new HashSet<>(blocks.values());
        List<Block> list = set.stream().sorted(Comparator.comparing(Block::getWidth).thenComparing(Block::getHeight)).collect(Collectors.toList());
        Collections.reverse(list);
        return list.toArray(new Block[0]);
    }

    /**
     * Sets the blocks in the state based on the given array of blocks.
     *
     * @param base the array of blocks to set
     * @throws IllegalArgumentException if the number of blocks is invalid
     */
    public void setBlocks(Block[] base) {
        blocks.clear();
        if (base.length != NUM_PIECES) throw new IllegalArgumentException("Invalid number of blocks");
        for (Block block : base) {
            if (block == null) throw new IllegalArgumentException("Invalid block");
            for (Coordinate coordinate : block.getOccupiedLocations()) {
                this.blocks.put(coordinate, block);
            }
        }
    }

    /**
     * Converts the state to a JSON string representation.
     *
     * @return the JSON string representation of the state
     */
    public String toJson() {
        Gson gson = new Gson();

        HashMap<String, Object> save = new HashMap<>();

        save.put("moves", this.moves);
        save.put("blocks", this.getBlocks());
        return gson.toJson(save);
    }

    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public State clone() {
        State clone = new State();

        clone.moves = this.moves;

        for (Map.Entry<Coordinate, Block> entry : this.blocks.entrySet()) {
            Coordinate coordinate = entry.getKey();
            Block block = entry.getValue();

            clone.blocks.put(coordinate.clone(), block.clone());
        }
        return clone;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ROWS; i++) {
            sb.append("|");
            for (int j = 0; j < COLS; j++) {
                Coordinate coordinate = Coordinate.of(i, j);
                if (blocks.containsKey(coordinate)) {
                    sb.append(blocks.get(coordinate).getIcon());
                } else {
                    sb.append(" ");
                }
                sb.append(" | ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Converts the state to a bit board representation.
     *
     * @return The bit board representation of the state.
     */
    public int[] toBitBoard() {
        int[] bitBoard = new int[NUM_PIECES];
        int j = 0;
        for (Block block : this.getBlocks()) {
            bitBoard[j++] = createBitMaskForBlock(block);
        }
        return bitBoard;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State state = (State) obj;

            return Arrays.equals(this.getBlocks(), state.getBlocks());
        }
        return false;
    }

    public void incrementMoves() {
        this.moves++;
    }

    public void decrementMoves() {
        this.moves--;
        if (this.moves < 0) {
            this.moves = 0;
        }
    }
}
