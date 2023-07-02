package dev.plagarizers.klotski.game.util;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.plagarizers.klotski.game.state.State;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SavesManager {
    private static final String LEVEL_DIRECTORY = "levels";
    private static final String COMPLETED_LEVELS_FILE = "completed_levels.json";
    private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    private final String externalStoragePath;

    /**
     * Constructs a `SavesManager` object with the default external storage path.
     */
    public SavesManager() {
        this.externalStoragePath = "~/klotski/saves/";
    }

    /**
     * Constructs a `SavesManager` object with the specified external storage path.
     *
     * @param externalStoragePath the external storage path
     */
    public SavesManager(String externalStoragePath) {
        this.externalStoragePath = externalStoragePath + "klotski/saves/";
    }

    /**
     * Saves the game state to a file with a specified save name.
     *
     * @param state    the game state to be saved
     * @param saveName the name of the save file
     */
    public void saveState(State state, String saveName) {
        try {
            String json = state.toJson();
            createSaveDirectoryIfNotExists();
            FileWriter fileWriter = new FileWriter(getSaveFilePath(saveName));
            fileWriter.write(json);
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }

    /**
     * Retrieves the saved state paths and the number of moves for each saved state.
     *
     * @return a HashMap containing the saved state paths and the corresponding number of moves
     */
    public HashMap<String, Integer> getSavedStatePaths() {
        HashMap<String, Integer> savedStatePaths = new HashMap<>();
        File saveDirectory = new File(externalStoragePath);
        if (saveDirectory.exists() && saveDirectory.isDirectory()) {
            File[] saveFiles = saveDirectory.listFiles();
            if (saveFiles != null) {
                for (File saveFile : saveFiles) {
                    if (saveFile.isDirectory()) {
                        continue;
                    }
                    if (!saveFile.getName().endsWith(".json")) {
                        continue;
                    }
                    if (saveFile.getName().equals(COMPLETED_LEVELS_FILE)) {
                        continue;
                    }
                    String path = saveFile.getAbsolutePath();
                    int moves = getMoves(path);
                    if (moves < 0) {
                        moves = 0;
                    }
                    savedStatePaths.put(path, moves);
                }
            }
        }
        return savedStatePaths;
    }

    /**
     * Retrieves the number of moves from a saved state file.
     *
     * @param filePath the path to the saved state file
     * @return the number of moves
     */
    public int getMoves(String filePath) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            String json = stringBuilder.toString();

            return State.fromJson(json).getMoves();
        } catch (IOException e) {
            System.err.println("Error getting moves: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Loads a saved state by its name.
     *
     * @param name the name of the saved state
     * @return the loaded state
     */
    public State loadStateByName(String name) {
        return loadStateByPath(getSaveFilePath(name));
    }

    /**
     * Loads a saved state by its file path.
     *
     * @param filePath the file path of the saved state
     * @return the loaded state
     */
    public State loadStateByPath(String filePath) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();


            String json = stringBuilder.toString();
            return State.fromJson(json);
        } catch (IOException e) {
            System.err.println("Error loading state: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates the save directory if it does not exist.
     */
    private void createSaveDirectoryIfNotExists() {
        File saveDirectory = new File(externalStoragePath);
        if (!saveDirectory.exists() || !saveDirectory.isDirectory()) {
            boolean created = saveDirectory.mkdirs();
            if (!created) {
                System.err.println("Failed to create save directory: " + externalStoragePath);
            }
        }
    }

    /**
     * Generates a unique filename for the saved state.
     *
     * @return the generated filename
     */
    private String generateFilename() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String timestamp = dateFormat.format(new Date());
        return "save_" + timestamp;
    }

    /**
     * Retrieves the file path for the specified filename.
     *
     * @param filename the filename
     * @return the file path
     */
    private String getSaveFilePath(String filename) {
        return externalStoragePath + filename + ".json";
    }

    /**
     * Loads the game levels from the default path.
     *
     * @return a list of loaded levels
     */
    public List<Level> loadLevelsFromDefaultPath() {
        return loadLevels(Gdx.files.internal(LEVEL_DIRECTORY + File.separator + "levels.json").reader());

    }

    /**
     * Loads the game levels from a given reader.
     *
     * @param levelsReader the reader for the levels file
     * @return a list of loaded levels
     */
    public List<Level> loadLevels(Reader levelsReader) {
        try {
            BufferedReader bufferedReader = new BufferedReader(levelsReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            String json = stringBuilder.toString();
            return Level.fromJson(json);
        } catch (IOException e) {
            throw new RuntimeException("Error loading levels: " + e.getMessage());
        }
    }

    /**
     * Deletes a saved state file by its name.
     *
     * @param saveName the name of the saved state file to be deleted
     */
    public void deleteSave(String saveName) {
        String path = getSaveFilePath(saveName);
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Loads the list of completed levels from a file.
     *
     * @return the list of completed levels
     */
    public List<String> loadCompletedLevels() {
        Gson gson = new Gson();
        try {
            File completedLevelsFile = new File(getCompletedLevelsFilePath());

            if (!completedLevelsFile.exists()) {
                // If the file doesn't exist, return an empty list

                boolean created = completedLevelsFile.createNewFile();
                if (!created) {
                    System.err.println("Failed to create completed levels file: " + completedLevelsFile.getAbsolutePath());
                }
                return new ArrayList<>();
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(completedLevelsFile));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            String json = stringBuilder.toString();

            return gson.fromJson(json, new TypeToken<List<String>>() {
            }.getType());
        } catch (IOException e) {
            System.err.println("Error loading completed levels: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Saves the list of completed levels to a file.
     *
     * @param completedLevels the list of completed levels
     */
    public void saveCompletedLevels(List<String> completedLevels) {
        Gson gson = new Gson();
        try {
            String json = gson.toJson(completedLevels);
            FileWriter fileWriter = new FileWriter(getCompletedLevelsFilePath());
            fileWriter.write(json);
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Error saving completed levels: " + e.getMessage());
        }
    }

    /**
     * Adds a level to the list of completed levels.
     *
     * @param levelName the number of the completed level
     */
    public void addCompletedLevel(String levelName) {
        List<String> completedLevels = loadCompletedLevels();
        if (!completedLevels.contains(levelName)) {
            completedLevels.add(levelName);
            saveCompletedLevels(completedLevels);
        }
    }

    /**
     * Retrieves the file path for the completed levels file.
     *
     * @return the file path
     */
    private String getCompletedLevelsFilePath() {
        return externalStoragePath + COMPLETED_LEVELS_FILE;
    }
}
