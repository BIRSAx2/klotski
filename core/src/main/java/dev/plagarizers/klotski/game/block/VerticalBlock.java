package dev.plagarizers.klotski.game.block;

import dev.plagarizers.klotski.game.util.Coordinate;

public class VerticalBlock extends Block {
  public VerticalBlock(Coordinate location) {
    super(location, 2, 1);
    setType(BlockType.VerticalBlock);
  }
}
