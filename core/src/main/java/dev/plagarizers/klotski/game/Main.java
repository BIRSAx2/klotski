package dev.plagarizers.klotski.game;

import dev.plagarizers.klotski.game.block.HorizontalBlock;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Coordinate;
import dev.plagarizers.klotski.game.util.Direction;

public class Main {

  public static void main(String[] args) {

    State state = State.fromDefaultConfiguration();

    System.out.println(state);

//    state.moveBlock(new HorizontalBlock(Coordinate.of(2,1)), Direction.LEFT);
    state.moveBlock(state.getBlocks()[1], Direction.RIGHT);
    System.out.println(state);
    state.moveBlock(state.getBlocks()[1], Direction.UP);

    System.out.println(state);

  }
}
