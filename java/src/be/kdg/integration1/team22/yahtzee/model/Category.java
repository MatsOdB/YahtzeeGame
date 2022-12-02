package be.kdg.integration1.team22.yahtzee.model;

public enum Category {
    /*
        • Category.java: enum that represents the categories
            • It should contain the following categories:
                • Ones
                • Twos
                • Threes
                • Fours
                • Fives
                • Sixes
                • Three of a kind
                • Four of a kind
                • Full house
                • Small straight
                • Large straight
                • Yahtzee
                • Chance
     */
    ONES,
    TWOS,
    THREES,
    FOURS,
    FIVES,
    SIXES,
    THREE_OF_A_KIND,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    SMALL_STRAIGHT,
    LARGE_STRAIGHT,
    YAHTZEE,
    CHANCE;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase().replace("_", " ");
    }
}
