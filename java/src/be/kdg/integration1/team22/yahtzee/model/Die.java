package be.kdg.integration1.team22.yahtzee.model;

/*
    * This class represents a die in the game of Yahtzee.
    * A die has a value and can be rolled.
    * A die can be used in a combination of dice.
    * A die can be used in a score.

    Attributes:
    * - String[] MOVE_NAMES: the names of the possible moves
    * - Die[] dice: the dice of the player

    Methods:
    * + Die()
    * + roll(): void
    * + getValue(): int
    * + toString(): String

    @author Team 22
 */

public class Die {
    private int value;

    public Die() {
        roll();
    }

    public void roll() {
        value = (int) (Math.random() * 6) + 1;
    }

    public int getValue() {
        return value;
    }

    @Override public String toString() {
        return String.valueOf(value);
    }
}
