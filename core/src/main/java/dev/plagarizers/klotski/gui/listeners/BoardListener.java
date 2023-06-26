package dev.plagarizers.klotski.gui.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.plagarizers.klotski.game.util.Direction;
import dev.plagarizers.klotski.gui.actors.Board;
import dev.plagarizers.klotski.gui.actors.Tile;
import dev.plagarizers.klotski.gui.state.GameState;

public class BoardListener extends InputListener {

    public static final int MIN_DRAG_DISTANCE = 10;

    private Board board;
    private Vector2 dragStartPos = null;
    private GameState gameState;

    public BoardListener(Board board, GameState gameState) {
        this.board = board;
        this.gameState = gameState;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {

        if (keycode == Input.Keys.TAB) {
            gameState.selectNextTile();
        } else if (keycode == Input.Keys.Z && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            gameState.undoMove();
        } else if (keycode == Input.Keys.R && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            gameState.reset();
        } else if (keycode == Input.Keys.SPACE) {
            gameState.playBestMove();
        } else if (keycode == Input.Keys.UP) {
            gameState.moveBlock(gameState.getSelectedTile().getBlock(), Direction.UP);
        } else if (keycode == Input.Keys.DOWN) {
            gameState.moveBlock(gameState.getSelectedTile().getBlock(), Direction.DOWN);
        } else if (keycode == Input.Keys.LEFT) {
            gameState.moveBlock(gameState.getSelectedTile().getBlock(), Direction.LEFT);
        } else if (keycode == Input.Keys.RIGHT) {
            gameState.moveBlock(gameState.getSelectedTile().getBlock(), Direction.RIGHT);
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
                gameState.setSelectedTile(tile);
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

            if (dragDirection != null && gameState.getSelectedTile() != null) {
                gameState.moveBlock(gameState.getSelectedTile().getBlock(), dragDirection);
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

    public EventListener getNextMoveListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Next move button pressed");
                gameState.playBestMove();
            }
        };
    }
}


