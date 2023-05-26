package dev.plagarizers.klotski.game.block;

import dev.plagarizers.klotski.game.util.Coordinate;

public class SmallBlock extends Block {
  public SmallBlock(Coordinate location) {
    super(location, 1, 1);
    setType(BlockType.SmallBlock);

  }
}
