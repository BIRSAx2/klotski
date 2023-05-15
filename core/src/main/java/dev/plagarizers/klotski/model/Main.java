package dev.plagarizers.klotski.model;

import dev.plagarizers.klotski.util.KlotskiSolver;

import java.util.List;

public class Main {

  public static void main(String[] args) {

    State state = new State();

    System.out.println(state);
    State state1 = State.fromBitBoard(state.toBitBoard());


    System.out.println(state1);
    KlotskiSolver solver = new KlotskiSolver(state);

    int steps = solver.minSteps();

    System.out.println("Steps: " + steps);
    List<State> pathToSolution = solver.getPathToSolution();


    for (State step : pathToSolution) {
      System.out.println(step);
    }
//    solver.minSteps();
  }
}
