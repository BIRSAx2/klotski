package dev.plagarizers.klotski.game.util;

import dev.plagarizers.klotski.game.state.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SavesManagerTest {
    private SavesManager savesManager;
    private File saveDirectory;
    private File saveFile;

    @BeforeEach
    public void setup() {
        savesManager = new SavesManager();
        saveDirectory = new File(savesManager.getExternalStoragePath());
        saveFile = new File(savesManager.getExternalStoragePath() + "save_2023-07-03_10-00-00.json");
    }

    @AfterEach
    public void cleanup() {
        if (saveFile.exists()) {
            saveFile.delete();
        }
        if (saveDirectory.exists()) {
            saveDirectory.delete();
        }
    }

    @Test
    public void saveState_SavesGameStateToFile() throws IOException {
        State state = State.fromDefaultConfiguration();
        String saveName = "save_2023-07-03_10-00-00";

        savesManager.saveState(state, saveName);

        assertTrue(saveDirectory.exists());
        assertTrue(saveDirectory.isDirectory());

        assertTrue(saveFile.exists());

        BufferedReader bufferedReader = new BufferedReader(new FileReader(saveFile));
        String savedJson = bufferedReader.readLine();
        bufferedReader.close();

        assertEquals(state.toJson(), savedJson);
    }

    @Test
    public void getMoves_ReadsMovesFromSavedStateFile() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(saveFile));
        State state = State.fromDefaultConfiguration();
        state.setMoves(5);

        savesManager.saveState(state, "save_2023-07-03_10-00-00");

        int moves = savesManager.getMoves(saveFile.getPath());

        saveFile.delete();

        assertEquals(5, moves);
    }

    @Test
    public void loadStateByPath_LoadsStateFromSavedStateFile() throws IOException {
        State expectedState = State.fromDefaultConfiguration();

        savesManager.saveState(expectedState, "save_2023-07-03_10-00-00");
        State loadedState = savesManager.loadStateByPath(saveFile.getPath());

        assertEquals(expectedState, loadedState);
    }

    @Test
    public void loadLevelsFromDefaultPath_LoadsLevelsFromDefaultPath() {
        // pass
        // Libgdx is needed to test this

    }

    @Test
    public void loadLevels_LoadsLevelsFromReader() throws FileNotFoundException {
        String levelsJson = "[\n" + "  {\n" + "    \"level\": 1,\n" + "    \"board\": [\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 0,\n" + "          \"y\": 1\n" + "        },\n" + "        \"height\": 2,\n" + "        \"width\": 2,\n" + "        \"type\": \"BigBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 0,\n" + "          \"y\": 0\n" + "        },\n" + "        \"height\": 2,\n" + "        \"width\": 1,\n" + "        \"type\": \"VerticalBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 0,\n" + "          \"y\": 3\n" + "        },\n" + "        \"height\": 2,\n" + "        \"width\": 1,\n" + "        \"type\": \"VerticalBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 2,\n" + "          \"y\": 0\n" + "        },\n" + "        \"height\": 2,\n" + "        \"width\": 1,\n" + "        \"type\": \"VerticalBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 2,\n" + "          \"y\": 3\n" + "        },\n" + "        \"height\": 2,\n" + "        \"width\": 1,\n" + "        \"type\": \"VerticalBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 2,\n" + "          \"y\": 1\n" + "        },\n" + "        \"height\": 1,\n" + "        \"width\": 2,\n" + "        \"type\": \"HorizontalBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 3,\n" + "          \"y\": 1\n" + "        },\n" + "        \"height\": 1,\n" + "        \"width\": 1,\n" + "        \"type\": \"SmallBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 3,\n" + "          \"y\": 2\n" + "        },\n" + "        \"height\": 1,\n" + "        \"width\": 1,\n" + "        \"type\": \"SmallBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 4,\n" + "          \"y\": 0\n" + "        },\n" + "        \"height\": 1,\n" + "        \"width\": 1,\n" + "        \"type\": \"SmallBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 4,\n" + "          \"y\": 3\n" + "        },\n" + "        \"height\": 1,\n" + "        \"width\": 1,\n" + "        \"type\": \"SmallBlock\"\n" + "      }\n" + "    ]\n" + "  },\n" + "  {\n" + "    \"level\": 2,\n" + "    \"board\": [\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 0,\n" + "          \"y\": 1\n" + "        },\n" + "        \"height\": 2,\n" + "        \"width\": 2,\n" + "        \"type\": \"BigBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 0,\n" + "          \"y\": 0\n" + "        },\n" + "        \"height\": 2,\n" + "        \"width\": 1,\n" + "        \"type\": \"VerticalBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 0,\n" + "          \"y\": 3\n" + "        },\n" + "        \"height\": 2,\n" + "        \"width\": 1,\n" + "        \"type\": \"VerticalBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 3,\n" + "          \"y\": 0\n" + "        },\n" + "        \"height\": 2,\n" + "        \"width\": 1,\n" + "        \"type\": \"VerticalBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 3,\n" + "          \"y\": 3\n" + "        },\n" + "        \"height\": 2,\n" + "        \"width\": 1,\n" + "        \"type\": \"VerticalBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 2,\n" + "          \"y\": 1\n" + "        },\n" + "        \"height\": 1,\n" + "        \"width\": 2,\n" + "        \"type\": \"HorizontalBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 2,\n" + "          \"y\": 0\n" + "        },\n" + "        \"height\": 1,\n" + "        \"width\": 1,\n" + "        \"type\": \"SmallBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 2,\n" + "          \"y\": 3\n" + "        },\n" + "        \"height\": 1,\n" + "        \"width\": 1,\n" + "        \"type\": \"SmallBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 3,\n" + "          \"y\": 1\n" + "        },\n" + "        \"height\": 1,\n" + "        \"width\": 1,\n" + "        \"type\": \"SmallBlock\"\n" + "      },\n" + "      {\n" + "        \"location\": {\n" + "          \"x\": 3,\n" + "          \"y\": 2\n" + "        },\n" + "        \"height\": 1,\n" + "        \"width\": 1,\n" + "        \"type\": \"SmallBlock\"\n" + "      }\n" + "    ]\n" + "  }]";
        Reader reader = new StringReader(levelsJson);

        List<Level> levels = savesManager.loadLevels(reader);

        assertNotNull(levels);
        assertFalse(levels.isEmpty());
    }

    @Test
    public void deleteSave_DeletesSavedStateFile() throws IOException {
        savesManager.saveState(State.fromDefaultConfiguration(), "save_2023-07-03_10-00-00");

        savesManager.deleteSave("save_2023-07-03_10-00-00");

        assertFalse(saveFile.exists());
    }

    @Test
    public void loadCompletedLevels_LoadsCompletedLevelsFromFile() {
        List<String> completedLevels = new ArrayList<>();
        completedLevels.add("Level 1");
        completedLevels.add("Level 2");

        savesManager.saveCompletedLevels(completedLevels);

        List<String> loadedCompletedLevels = savesManager.loadCompletedLevels();

        assertNotNull(loadedCompletedLevels);
        assertEquals(completedLevels, loadedCompletedLevels);
    }


    @Test
    public void saveCompletedLevels_SavesCompletedLevelsToFile() {
        List<String> completedLevels = new ArrayList<>();
        completedLevels.add("Level 1");
        completedLevels.add("Level 2");

        savesManager.saveCompletedLevels(completedLevels);

        List<String> loadedCompletedLevels = savesManager.loadCompletedLevels();

        assertNotNull(loadedCompletedLevels);
        assertEquals(completedLevels, loadedCompletedLevels);
    }
}
