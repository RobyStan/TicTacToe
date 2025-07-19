package com.example.tictactoe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistics {

    @JsonIgnore
    private static Statistics instance;

    @JsonIgnore
    private static final String FILE_PATH = "statistics.json";

    private int totalGames;
    private int pvpGames;
    private int pvaiGames;

    private int pvpWinsPlayer1;
    private int pvpWinsPlayer2;
    private int pvpDraws;

    private final Map<DifficultyLevel, Integer> pvaiWinsPlayer;
    private final Map<DifficultyLevel, Integer> pvaiWinsAI;
    private final Map<DifficultyLevel, Integer> pvaiDraws;

    public enum Starter { PLAYER, AI }

    private final Map<DifficultyLevel, Map<Starter, Integer>> pvaiWinsPlayerByStarter;
    private final Map<DifficultyLevel, Map<Starter, Integer>> pvaiWinsAIByStarter;
    private final Map<DifficultyLevel, Map<Starter, Integer>> pvaiDrawsByStarter;

    public Statistics() {
        pvaiWinsPlayer = new EnumMap<>(DifficultyLevel.class);
        pvaiWinsAI = new EnumMap<>(DifficultyLevel.class);
        pvaiDraws = new EnumMap<>(DifficultyLevel.class);

        pvaiWinsPlayerByStarter = new EnumMap<>(DifficultyLevel.class);
        pvaiWinsAIByStarter = new EnumMap<>(DifficultyLevel.class);
        pvaiDrawsByStarter = new EnumMap<>(DifficultyLevel.class);

        for (DifficultyLevel d : DifficultyLevel.values()) {
            pvaiWinsPlayer.put(d, 0);
            pvaiWinsAI.put(d, 0);
            pvaiDraws.put(d, 0);

            Map<Starter, Integer> playerMap = new EnumMap<>(Starter.class);
            Map<Starter, Integer> aiMap = new EnumMap<>(Starter.class);
            Map<Starter, Integer> drawMap = new EnumMap<>(Starter.class);

            for (Starter s : Starter.values()) {
                playerMap.put(s, 0);
                aiMap.put(s, 0);
                drawMap.put(s, 0);
            }

            pvaiWinsPlayerByStarter.put(d, playerMap);
            pvaiWinsAIByStarter.put(d, aiMap);
            pvaiDrawsByStarter.put(d, drawMap);
        }
    }

    public static Statistics getInstance() {
        if (instance == null) {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    instance = mapper.readValue(file, Statistics.class);
                    return instance;
                } catch (IOException e) {
                    System.out.println("Failed to load stats. Using defaults.");
                    e.printStackTrace();
                }
            }
            instance = new Statistics();
        }
        return instance;
    }

    public void loadFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                Statistics loaded = mapper.readValue(file, Statistics.class);
                this.totalGames = loaded.totalGames;
                this.pvpGames = loaded.pvpGames;
                this.pvaiGames = loaded.pvaiGames;

                this.pvpWinsPlayer1 = loaded.pvpWinsPlayer1;
                this.pvpWinsPlayer2 = loaded.pvpWinsPlayer2;
                this.pvpDraws = loaded.pvpDraws;

                this.pvaiWinsPlayer.clear();
                this.pvaiWinsPlayer.putAll(loaded.pvaiWinsPlayer);

                this.pvaiWinsAI.clear();
                this.pvaiWinsAI.putAll(loaded.pvaiWinsAI);

                this.pvaiDraws.clear();
                this.pvaiDraws.putAll(loaded.pvaiDraws);

                this.pvaiWinsPlayerByStarter.clear();
                this.pvaiWinsPlayerByStarter.putAll(loaded.pvaiWinsPlayerByStarter);

                this.pvaiWinsAIByStarter.clear();
                this.pvaiWinsAIByStarter.putAll(loaded.pvaiWinsAIByStarter);

                this.pvaiDrawsByStarter.clear();
                this.pvaiDrawsByStarter.putAll(loaded.pvaiDrawsByStarter);

            } catch (IOException e) {
                System.err.println("Failed to load statistics: " + e.getMessage());
            }
        }
    }

    public void saveToFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), this);
        } catch (IOException e) {
            System.out.println("Failed to save statistics.");
            e.printStackTrace();
        }
    }

    public void recordPVPGame(String winner) {
        totalGames++;
        pvpGames++;
        if (winner == null) {
            pvpDraws++;
        } else if (winner.equals("player1")) {
            pvpWinsPlayer1++;
        } else if (winner.equals("player2")) {
            pvpWinsPlayer2++;
        }
    }

    public void recordPVAIGame(DifficultyLevel difficulty, Starter starter, String winner) {
        totalGames++;
        pvaiGames++;
        if (winner == null) {
            pvaiDraws.put(difficulty, pvaiDraws.getOrDefault(difficulty, 0) + 1);
            pvaiDrawsByStarter.get(difficulty).put(starter, pvaiDrawsByStarter.get(difficulty).getOrDefault(starter, 0) + 1);
        } else if (winner.equals("player")) {
            pvaiWinsPlayer.put(difficulty, pvaiWinsPlayer.getOrDefault(difficulty, 0) + 1);
            pvaiWinsPlayerByStarter.get(difficulty).put(starter, pvaiWinsPlayerByStarter.get(difficulty).getOrDefault(starter, 0) + 1);
        } else if (winner.equals("ai")) {
            pvaiWinsAI.put(difficulty, pvaiWinsAI.getOrDefault(difficulty, 0) + 1);
            pvaiWinsAIByStarter.get(difficulty).put(starter, pvaiWinsAIByStarter.get(difficulty).getOrDefault(starter, 0) + 1);
        }
    }

    public static Map<String, Integer> convertDifficultyMapToStringKey(Map<DifficultyLevel, Integer> map) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().name(),
                        Map.Entry::getValue
                ));
    }


    public int getTotalGames() {
        return totalGames;
    }

    public int getPvpGames() {
        return pvpGames;
    }

    public int getPvaiGames() {
        return pvaiGames;
    }

    public int getPvpWinsPlayer1() {
        return pvpWinsPlayer1;
    }

    public int getPvpWinsPlayer2() {
        return pvpWinsPlayer2;
    }

    public int getPvpDraws() {
        return pvpDraws;
    }

    public Map<DifficultyLevel, Integer> getPvaiWinsPlayer() {
        return pvaiWinsPlayer;
    }

    public Map<DifficultyLevel, Integer> getPvaiWinsAI() {
        return pvaiWinsAI;
    }

    public Map<DifficultyLevel, Integer> getPvaiDraws() {
        return pvaiDraws;
    }

    public Map<DifficultyLevel, Map<Starter, Integer>> getPvaiWinsPlayerByStarter() {
        return pvaiWinsPlayerByStarter;
    }

    public Map<DifficultyLevel, Map<Starter, Integer>> getPvaiWinsAIByStarter() {
        return pvaiWinsAIByStarter;
    }

    public Map<DifficultyLevel, Map<Starter, Integer>> getPvaiDrawsByStarter() {
        return pvaiDrawsByStarter;
    }

    private Map<String, Integer> extractStatsByStarter(Map<DifficultyLevel, Map<Starter, Integer>> nestedMap, Starter starter) {
        return nestedMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> entry.getValue().getOrDefault(starter, 0)
                ));
    }

    public Map<String, Integer> getPvaiWinsPlayerByStarterMap(Starter starter) {
        return extractStatsByStarter(pvaiWinsPlayerByStarter, starter);
    }

    public Map<String, Integer> getPvaiWinsAIByStarterMap(Starter starter) {
        return extractStatsByStarter(pvaiWinsAIByStarter, starter);
    }

    public Map<String, Integer> getPvaiDrawsByStarterMap(Starter starter) {
        return extractStatsByStarter(pvaiDrawsByStarter, starter);
    }
}
