package dev.plagarizers.klotski.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import dev.plagarizers.klotski.actors.BoardWidget;
import dev.plagarizers.klotski.game.util.Direction;
import dev.plagarizers.klotski.ui.TileWidget;

public class BoardInputListener implements InputProcessor {
  BoardWidget boardWidget;

  TileWidget selectedTile;

  public BoardInputListener(BoardWidget boardWidget) {
    this.boardWidget = boardWidget;
    this.selectedTile = boardWidget.getTiles().get(1);
  }

  @Override
  public boolean keyDown(int keycode) {

    if (selectedTile == null) return false;

    switch (keycode) {
      case Input.Keys.UP:
        boardWidget.moveTile(selectedTile, Direction.UP);
        break;
      case Input.Keys.DOWN:
        boardWidget.moveTile(selectedTile, Direction.DOWN);
        break;
      case Input.Keys.LEFT:
        boardWidget.moveTile(selectedTile, Direction.LEFT);
        break;
      case Input.Keys.RIGHT:
        boardWidget.moveTile(selectedTile, Direction.RIGHT);
        break;
      case Input.Keys.TAB:
        int index = boardWidget.getTiles().indexOf(selectedTile);
        index = (index + 1) % (boardWidget.getTiles().size() - 1);
        selectedTile = boardWidget.getTiles().get(index);
        break;
      default:
        break;
    }

    System.out.println("key down");
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    System.out.println("key up");
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    System.out.println("key typed");
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }
}
