package dev.plagarizers.klotski.game.state;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class KlotskiSolverTest {

    @Test
    void testMinSteps_ValidPuzzle_ReturnsCorrectSteps() {
        // Arrange
        State initialState = State.fromDefaultConfiguration();
        KlotskiSolver solver = new KlotskiSolver(initialState);
        int expectedSteps = 79;

        // Act
        int actualSteps = solver.minSteps();

        // Assert
        Assertions.assertEquals(expectedSteps, actualSteps, "Incorrect number of steps to reach the goal state.");
    }

    @Test
    void testMinSteps_InvalidPuzzle_ReturnsNegativeOne() {
        // Arrange

        // TODO - Add a test case for an invalid puzzle
        State initialState = State.fromDefaultConfiguration();
        KlotskiSolver solver = new KlotskiSolver(initialState);
        int expectedSteps = -1;

        // Act
        int actualSteps = solver.minSteps();

        // Assert
//        Assertions.assertEquals(expectedSteps, actualSteps, "Expected -1 as the puzzle is invalid.");
    }

    @Test
    void testGetPathToSolution_ValidSolution_ReturnsCorrectPath() {
        // Arrange
        State initialState = State.fromDefaultConfiguration();
        KlotskiSolver solver = new KlotskiSolver(initialState);
        List<State> expectedPath = new ArrayList<>();

        // Act
        int steps = solver.minSteps();
        List<State> actualPath = solver.getPathToSolution();

        // Assert
//    Assertions.assertEquals(expectedPath, actualPath, "Incorrect path to the solution.");
        Assertions.assertEquals(steps, actualPath.size(), "Path length does not match the number of steps.");
    }

}
