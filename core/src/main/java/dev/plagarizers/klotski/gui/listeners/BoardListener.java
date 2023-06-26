package dev.plagarizers.klotski.gui.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import dev.plagarizers.klotski.game.util.Direction;
import dev.plagarizers.klotski.gui.actors.Board;
import dev.plagarizers.klotski.gui.actors.Tile;

public class BoardListener extends InputListener {

    public static final int MIN_DRAG_DISTANCE = 10;

    private Board board;
    private Vector2 dragStartPos = null;

    public BoardListener(Board board) {
        this.board = board;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        System.out.println("Key pressed: " + keycode);
        if (keycode == Input.Keys.TAB) {
            board.selectNextTile();
        } else if (keycode == Input.Keys.Z && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            board.undoMove();
        } else if (keycode == Input.Keys.R && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            board.reset();
        } else if (keycode == Input.Keys.SPACE) {
            board.playBestMove();
        } else if (keycode == Input.Keys.UP) {
            board.moveBlock(board.getSelectedTile().getBlock(), Direction.UP);
        } else if (keycode == Input.Keys.DOWN) {
            board.moveBlock(board.getSelectedTile().getBlock(), Direction.DOWN);
        } else if (keycode == Input.Keys.LEFT) {
            board.moveBlock(board.getSelectedTile().getBlock(), Direction.LEFT);
        } else if (keycode == Input.Keys.RIGHT) {
            board.moveBlock(board.getSelectedTile().getBlock(), Direction.RIGHT);
        }
        return true;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        // Handle touch down event
        dragStartPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        float localX = Gdx.input.getX();
        float localY = Gdx.input.getY();
        Vector2 localCoords = board.screenToLocalCoordinates(new Vector2(localX, localY));
        localX = localCoords.x;
        localY = localCoords.y;
        for (int i = 0; i < board.getTiles().size(); i++) {
            Tile tile = board.getTiles().get(i);
            if (tile.contains(localX, localY)) {
                board.setSelectedTile(tile);
                break;
            }
        }

        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        Vector2 dragEndPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());

        float dragDistance = dragEndPos.dst(dragStartPos);

        if (dragDistance >= MIN_DRAG_DISTANCE) {

            // Calculate the drag direction based on the start and end positions
            Direction dragDirection = calculateDragDirection(dragStartPos, dragEndPos);

            if (dragDirection != null && board.getSelectedTile() != null) {
                board.moveBlock(board.getSelectedTile().getBlock(), dragDirection);
            }
        }
    }

    private Direction calculateDragDirection(Vector2 start, Vector2 end) {
        float deltaX = end.x - start.x;
        float deltaY = end.y - start.y;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } else {
            if (deltaY > 0) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        }
    }
}

