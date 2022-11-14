package be.kdg.integration1.team22.yahtzee.model;

/*
    * This class represents a board in the game of Yahtzee.
    * A board keeps track of all the move names.
    * A board also keeps track of the possible moves and the dice.

    Attributes:
    * - String[] MOVE_NAMES: the names of the possible moves
    * - Die[] dice: the dice of the player
    * - ArrayList<Score> possibleMoves: the possible moves of the player

    Methods:
    * + Board()
    * + diceToString(): String
    * + rollDice(): void
    * + getDice(): Die[]
    * + getDiceValue(): int
    * + getPossibleMoveNames(): String[]
    * + getPossibleMoves(): ArrayList<Score>
    * + getMoveValue(moveName: String): int



    @author Team 22
 */

import java.util.ArrayList;

public class Board {
    public static final String[] MOVE_NAMES = {
        "Ones", "Twos", "Threes", "Fours", "Fives", "Sixes",
        "Three of a kind", "Four of a kind", "Full house", "Small straight", "Large straight", "Yahtzee", "Chance"
    };
    private final Die[] dice = new Die[5];
    private final ArrayList<Score> possibleMoves = new ArrayList<>();

    public Board() {
        for (int i = 0; i < dice.length; i++) {
            dice[i] = new Die();
        }
    }

    public String diceToString() {
        StringBuilder sb = new StringBuilder();
        for (Die die : dice) {
            sb.append(die).append(" ");
        }
        return sb.toString();
    }

    public void rollDice() {
        for (Die die : dice) {
            die.roll();
        }
    }

    public Die[] getDice() {
        return dice;
    }

    public int getDiceValue() {
        int value = 0;
        for (Die die : dice) {
            value += die.getValue();
        }
        return value;
    }

    public String[] getPossibleMoveNames() {
        String[] moveNames = new String[possibleMoves.size()];
        for (int i = 0; i < moveNames.length; i++) {
            moveNames[i] = possibleMoves.get(i).getMoveName();
        }
        return moveNames;
    }

    public ArrayList<Score> getPossibleMoves() {
        return possibleMoves;
    }

    public int getMoveValue(String moveName) {
        for (Score score : possibleMoves) {
            if (score.getMoveName().equals(moveName)) {
                return score.getScore();
            }
        }
        return 0;
    }


}
