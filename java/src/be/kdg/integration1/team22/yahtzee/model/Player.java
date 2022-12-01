package be.kdg.integration1.team22.yahtzee.model;

import java.util.ArrayList;

/*
    * This class represents a player in the game of Yahtzee.
    * A player keeps track of his/her scores and has a name.

    Attributes:
    * - String name: the name of the player
    * - ArrayList<Score> scores: the scores of the player

    Methods:
    * + Player(name: String)
    * + getName(): String
    * + getScores(): ArrayList<Score>
    * + getScore(moveName: String): int
    * + reset(): void
    * + toString(): String


    @author Team 22
 */

public class Player {
    private final String name;
    private final ArrayList<Score> scores = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        for (String moveName : Board.MOVE_NAMES) {
            scores.add(new Score(moveName));
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public int getScore(String moveName) {
        for (Score score : scores) {
            if (score.getMoveName().equals(moveName)) {
                return score.getScore();
            }
        }
        return 0;
    }

    public void initScores() {
        // add all move names to the scores list and set the score to 0
        for (String moveName : Board.MOVE_NAMES) {
            scores.add(new Score(moveName));
        }
    }

    public void reset() {
        for (Score score : scores) {
            score.reset();
        }
    }

    @Override public String toString() {
        return name;
    }

    public void setScore(String moveName, int value) {
        for (Score score : scores) {
            if (score.getMoveName().equals(moveName)) {
                score.setScore(value);
                return;
            }
        }
        // if moveName not found, throw exception
        throw new IllegalArgumentException("Move name not found: " + moveName);
    }

}
