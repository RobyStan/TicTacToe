package com.example.tictactoe;

import java.util.EnumMap;
import java.util.Map;

public class Statistics {

    private static Statistics instance;

    private int totalGames = 0;
    private int pvpGames = 0;
    private int pvaiGames = 0;

    private int pvpWinsPlayer1 = 0;
    private int pvpWinsPlayer2 = 0;
    private int pvpDraws = 0;

    private final Map<DifficultyLevel, Integer> pvaiWinsPlayer = new EnumMap<>(DifficultyLevel.class);
    private final Map<DifficultyLevel, Integer> pvaiWinsAI = new EnumMap<>(DifficultyLevel.class);
    private final Map<DifficultyLevel, Integer> pvaiDraws = new EnumMap<>(DifficultyLevel.class);

    public enum Starter { PLAYER, AI }

    private final Map<DifficultyLevel, Map<Starter, Integer>> pvaiWinsPlayerByStarter = new EnumMap<>(DifficultyLevel.class);
    private final Map<DifficultyLevel, Map<Starter, Integer>> pvaiWinsAIByStarter = new EnumMap<>(DifficultyLevel.class);
    private final Map<DifficultyLevel, Map<Starter, Integer>> pvaiDrawsByStarter = new EnumMap<>(DifficultyLevel.class);


    private Statistics() {
        for (DifficultyLevel d : DifficultyLevel.values()) {
            pvaiWinsPlayer.put(d, 0);
            pvaiWinsAI.put(d, 0);
            pvaiDraws.put(d, 0);
        }

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
            instance = new Statistics();
        }
        return instance;
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
            pvaiDraws.put(difficulty, pvaiDraws.get(difficulty) + 1);
            pvaiDrawsByStarter.get(difficulty).put(starter, pvaiDrawsByStarter.get(difficulty).get(starter) +1);
        } else if (winner.equals("player")) {
            pvaiWinsPlayer.put(difficulty, pvaiWinsPlayer.get(difficulty) + 1);
            pvaiWinsPlayerByStarter.get(difficulty).put(starter, pvaiWinsPlayerByStarter.get(difficulty).get(starter) +1);
        } else if (winner.equals("ai")) {
            pvaiWinsAI.put(difficulty, pvaiWinsAI.get(difficulty) + 1);
            pvaiWinsAIByStarter.get(difficulty).put(starter, pvaiWinsAIByStarter.get(difficulty).get(starter) +1);
        }
    }

    public int getTotalGames() { return totalGames; }
    public int getPvpGames() { return pvpGames; }
    public int getPvaiGames() { return pvaiGames; }

    public int getPvpWinsPlayer1() { return pvpWinsPlayer1; }
    public int getPvpWinsPlayer2() { return pvpWinsPlayer2; }
    public int getPvpDraws() { return pvpDraws; }

    public int getPvaiWinsPlayer(DifficultyLevel DifficultyLevel) { return pvaiWinsPlayer.get(DifficultyLevel); }
    public int getPvaiWinsAI(DifficultyLevel DifficultyLevel) { return pvaiWinsAI.get(DifficultyLevel); }
    public int getPvaiDraws(DifficultyLevel DifficultyLevel) { return pvaiDraws.get(DifficultyLevel); }

    public int getPvaiWinsPlayerByStarter(DifficultyLevel difficulty, Starter starter) {
        return pvaiWinsPlayerByStarter.get(difficulty).get(starter);
    }
    public int getPvaiWinsAIByStarter(DifficultyLevel difficulty, Starter starter) {
        return pvaiWinsAIByStarter.get(difficulty).get(starter);
    }
    public int getPvaiDrawsByStarter(DifficultyLevel difficulty, Starter starter) {
        return pvaiDrawsByStarter.get(difficulty).get(starter);
    }
}
