package dev.plagarizers.klotski.game.util;

import dev.plagarizers.klotski.game.state.State;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SavesManager {

    private String externalStoragePath;
    private static final String LEVEL_DIRECTORY = "levels";
    private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";

    public SavesManager() {
        this.externalStoragePath = "~/klotski/saves/";
    }

    public SavesManager(String externalStoragePath) {
        this.externalStoragePath = externalStoragePath + "klotski/saves/";
    }

    public void saveState(State state) {
        try {
            String json = state.toJson();
            createSaveDirectoryIfNotExists();
            String filename = generateFilename();
            System.out.println("Filename: " + filename);
            System.out.println("Saving to: " + getSaveFilePath(filename));
            FileWriter fileWriter = new FileWriter(getSaveFilePath(filename));
            fileWriter.write(json);
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }

    public List<String> getSavedStatePaths() {
        List<String> savedStatePaths = new ArrayList<>();
        System.out.println(externalStoragePath == null);
        File saveDirectory = new File(externalStoragePath);
        System.out.println(externalStoragePath);
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

    private void createSaveDirectoryIfNotExists() {
        File saveDirectory = new File(externalStoragePath);
        if (!saveDirectory.exists() || !saveDirectory.isDirectory()) {
            boolean created = saveDirectory.mkdirs();
            if (!created) {
                System.err.println("Failed to create save directory: " + externalStoragePath);
            }
        }
    }

    private String generateFilename() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String timestamp = dateFormat.format(new Date());
        return "save_" + timestamp;
    }

    private String getSaveFilePath(String filename) {
        return externalStoragePath + filename + ".json";
    }


    public List<Level> loadLevelsFromDefaultPath() {
        return loadLevels(LEVEL_DIRECTORY + File.separator + "levels.json");

    }

    public List<Level> loadLevels(String filePath) {

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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

    public void deleteSave(String saveName) {

        String path = getSaveFilePath(saveName);

        System.out.println("Deleting save: " + path);

        File file = new File(path);
        System.out.println(file.exists());
        if (file.exists()) {
            file.delete();
        }

    }
}
