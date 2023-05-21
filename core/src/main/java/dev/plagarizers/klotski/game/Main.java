package dev.plagarizers.klotski.game;

import dev.plagarizers.klotski.game.state.State;

public class Main {

  public static void main(String[] args) {

    State state = State.fromDefaultConfiguration();

    System.out.println(state);
    State state1 = State.fromJson(state.toJson());

    System.out.println(state1);

  }
}
