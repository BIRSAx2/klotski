package dev.plagarizers.klotski.game.block;

import dev.plagarizers.klotski.game.util.Coordinate;

public class HorizontalBlock extends Block {
  public HorizontalBlock(Coordinate location) {
    super(location, 1, 2);
    setType(BlockType.HorizontalBlock);
  }
}
