package dev.plagarizers.klotski.game;

import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.state.KlotskiSolver;

import java.util.List;

public class Main {

  public static void main(String[] args) {

    State state = State.fromDefaultConfiguration();

    KlotskiSolver solver = new KlotskiSolver(state);

    int steps = solver.minSteps();

    System.out.println("Steps: " + steps);
    List<State> pathToSolution = solver.getPathToSolution();

    for (State step : pathToSolution) {
      System.out.println(step);
    }
  }
}
