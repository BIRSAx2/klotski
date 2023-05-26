package dev.plagarizers.klotski.game;

import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.block.SmallBlock;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Coordinate;
import dev.plagarizers.klotski.game.util.Level;
import dev.plagarizers.klotski.game.util.SavesManager;

import java.util.List;

public class Main {

  public static void main(String[] args) {

    State state = State.fromRandomConfiguration();


    Block small = new SmallBlock(Coordinate.of(3, 1));

    System.out.println(small);

    System.out.println(small.getOccupiedLocations());
    SavesManager savesManager = new SavesManager();


    List<Level> levels = savesManager.loadLevels();

    Level level = levels.get(0);

    for (Block block : level.getBoard()) {
      System.out.println(block);
    }
    System.out.println(levels.get(0).toState());
    System.out.println(levels.get(1).toState());


  }
}
