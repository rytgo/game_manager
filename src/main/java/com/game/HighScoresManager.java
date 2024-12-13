package com.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighScoresManager {
    private String fileName;
    private Map<String, Map<String, Integer>> scores; // username -> (game -> score)

    public HighScoresManager(String fileName) {
        this.fileName = fileName;
        this.scores = new HashMap<>();
        loadHighScores();
    }

    private void loadHighScores() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 3) { // username:BlackJack=score:Snake=score
                    String username = parts[0];
                    String blackjackPart = parts[1];
                    String snakePart = parts[2];

                    int blackjackScore = Integer.parseInt(blackjackPart.split("=")[1]);
                    int snakeScore = Integer.parseInt(snakePart.split("=")[1]);

                    Map<String, Integer> userScores = new HashMap<>();
                    userScores.put("BlackJack", blackjackScore);
                    userScores.put("Snake", snakeScore);

                    scores.put(username, userScores);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading high scores: " + e.getMessage());
        }
    }
    
    private void saveHighScores() {
        StringBuilder data = new StringBuilder();
        for (Map.Entry<String, Map<String, Integer>> entry : scores.entrySet()) {
            String username = entry.getKey();
            Map<String, Integer> userScores = entry.getValue();
            data.append(username)
                .append(":BlackJack=")
                .append(userScores.get("BlackJack"))
                .append(":Snake=")
                .append(userScores.get("Snake"))
                .append(System.lineSeparator());
        }
        try {
            Files.write(Paths.get(fileName), data.toString().getBytes());
        } catch (IOException e) {
            System.out.println("Error saving high scores: " + e.getMessage());
        }
    }

    public void addUser(String username) {
        if (!scores.containsKey(username)) {
            Map<String, Integer> defaultScores = new HashMap<>();
            defaultScores.put("BlackJack", 1000);
            defaultScores.put("Snake", 1000);
            scores.put(username, defaultScores);
            saveHighScores(); // Save the updated scores to the file
        }
    }

    public void updateScore(String username, String game, int newScore) {
        if (scores.containsKey(username)) {
            scores.get(username).put(game, newScore);
            saveHighScores();
        } else {
            System.out.println("User not found!");
        }
    }

    public Map<String, Map<String, Integer>> getScores() {
        return scores;
    }
    
    public Map<String, Integer> getUserScores(String username) {
        return scores.getOrDefault(username, null);
    }
}
