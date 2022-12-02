package be.kdg.integration1.team22.yahtzee.model;

/*
    • Die.java: class that represents a die
        • It should contain a value.
        • It should contain a method that rolls the die.
        • It should contain a method that returns the value of the die.
        • It should contain a method that sets the value of the die.
        • It should contain a method that returns the value of the die as a string.
 */

public class Die {
    private int value;

    public Die() {
        this.value = 0;
    }

    public void roll() {
        this.value = (int) (Math.random() * 6) + 1;
    }

    public int getValue() {
        return value;
    }

    public int getFaceValue() {
        return value;
    }
}
