package dev.plagarizers.klotski.game.util;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.State;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level {
  @SerializedName("level")
  private String name;

  @SerializedName("board")
  private Block[] board;

  public Level(String name, Block[] board) {
    this.name = name;
    this.board = board;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Block[] getBoard() {
    return board;
  }

  public void setBoard(Block[] board) {
    this.board = board;
  }


  public static List<Level> fromJson(String json) {
    Gson gson = new Gson();
    List<Level> levels = new ArrayList<>();
    Type type = new TypeToken<List<Level>>() {
    }.getType();
    return gson.fromJson(json, type);
  }

  @Override
  public String toString() {
    return "Level{" +
      "name='" + name + '\'' +
      ", board=" + Arrays.toString(board) +
      '}';
  }


  public State toState() {

    State state = State.fromDefaultConfiguration();
    state.setBlocks(board);
    return state;
  }
}
