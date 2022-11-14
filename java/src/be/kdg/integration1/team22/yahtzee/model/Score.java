package be.kdg.integration1.team22.yahtzee.model;

/*
    * This class represents a score in the game of Yahtzee.
    * A score is a combination of dice values and a score value.
    * The score value is the number of points you get for that combination.
    * The combination is determined by the combination type.
    * A score can be filled in on the board of a player.

    Attributes:
    * - moveName: the name of the move
    * - score: the score value

    Methods:
    * + Score(moveName: String)
    * + getMoveName(): String
    * + getScore(): int
    * + setScore(score: int): void
    * + isScored(): boolean
    * + reset(): void
    * + toString(): String

    @author Team 22
 */

public class Score {
    private final String moveName;
    private int score;

    public Score(String moveName) {
        this.moveName = moveName;
    }

    public String getMoveName() {
        return moveName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isScored() {
        return score > 0;
    }

    public void reset() {
        score = 0;
    }

    @Override public String toString() {
        return String.format("%10s: %3d", moveName, score);
    }
}
