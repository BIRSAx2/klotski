package dev.plagarizers.klotski.game;

import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.KlotskiSolver;
import dev.plagarizers.klotski.game.state.State;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {

    State state = State.fromDefaultConfiguration();

    System.out.println(State.fromRandomConfiguration());

  }
}
