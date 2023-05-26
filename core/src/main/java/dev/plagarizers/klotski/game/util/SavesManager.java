package dev.plagarizers.klotski.game.util;

import com.google.gson.Gson;
import dev.plagarizers.klotski.game.state.State;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SavesManager {
  private static final String SAVE_DIRECTORY = "saves";
  private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";
  private Gson gson;

  public SavesManager() {
    gson = new Gson();
  }

  public void saveState(State state) {
    try {
      String json = state.toJson();

      createSaveDirectoryIfNotExists();
      String filename = generateFilename();
      FileWriter fileWriter = new FileWriter(getSaveFilePath(filename));
      fileWriter.write(json);
      fileWriter.close();

      System.out.println("State saved successfully with filename: " + filename);
    } catch (IOException e) {
      System.err.println("Error saving state: " + e.getMessage());
    }
  }

  public List<String> getSavedStatePaths() {
    List<String> savedStatePaths = new ArrayList<>();
    File saveDirectory = new File(SAVE_DIRECTORY);
    if (saveDirectory.exists() && saveDirectory.isDirectory()) {
      File[] saveFiles = saveDirectory.listFiles();
      if (saveFiles != null) {
        for (File saveFile : saveFiles) {
          savedStatePaths.add(saveFile.getAbsolutePath());
        }
      }
    }
    return savedStatePaths;
  }


  public State loadStateByName(String name) {
    return loadStateByPath(getSaveFilePath(name));
  }

  public State loadStateByPath(String path) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
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

  private void createSaveDirectoryIfNotExists() {
    File saveDirectory = new File(SAVE_DIRECTORY);
    if (!saveDirectory.exists() || !saveDirectory.isDirectory()) {
      boolean created = saveDirectory.mkdirs();
      if (!created) {
        System.err.println("Failed to create save directory: " + SAVE_DIRECTORY);
      }
    }
  }

  private String generateFilename() {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    String timestamp = dateFormat.format(LocalDate.now());
    return "save_" + timestamp + ".json";
  }

  private String getSaveFilePath(String filename) {
    return SAVE_DIRECTORY + File.separator + filename;
  }

  public List<Level> loadLevels() {
    return loadLevels("assets/levels/levels.json");
  }

  public List<Level> loadLevels(String filePath) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line);
      }
      bufferedReader.close();

      String json = stringBuilder.toString();
      return Level.fromJson(json);
    } catch (IOException e) {
      System.err.println("Error loading levels: " + e.getMessage());
      return null;
    }
  }
}
