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
import java.util.Arrays;
import java.util.Comparator;

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

    private void sortDice() {
        // Sort the dice in ascending order using the value of the dice
        Arrays.sort(dice, Comparator.comparingInt(Die::getValue));
    }

    private String diceToString() {
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

    Die[] getDice() {
        return dice;
    }

    private int getDiceValue() {
        int value = 0;
        for (Die die : dice) {
            value += die.getValue();
        }
        return value;
    }

    private String[] getPossibleMoveNames() {
        String[] moveNames = new String[possibleMoves.size()];
        for (int i = 0; i < moveNames.length; i++) {
            moveNames[i] = possibleMoves.get(i).getMoveName();
        }
        return moveNames;
    }

    public ArrayList<Score> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves() {
        possibleMoves.clear();
        for (String moveName : MOVE_NAMES) {
            possibleMoves.add(new Score(moveName));
            setMoveValue(moveName);
        }
    }

    private void setMoveValue(String moveName) {
        int value = 0;
        sortDice();
        // check if the move has already been played
        for (Score score : possibleMoves) {
            if (score.getMoveName().equals(moveName)) {
                if (score.getScore() != 0) {
                    throw new IllegalStateException("Move has already been played");
                }
            }
        }

        switch (moveName) {
            // for simple moves (ones, twos, threes, fours, fives, sixes) check if the dice have the right value
            // if so, add the value of the dice to the value of the move
            case "Ones":
                value = countDice(1);
                break;
            case "Twos":
                value =  countDice(2);
                break;
            case "Threes":
                value = countDice(3);
                break;
            case "Fours":
                value = countDice(4);
                break;
            case "Fives":
                value = countDice(5);
                break;
            case "Sixes":
                value = countDice(6);
                break;
            case "Three of a kind":
                // check if there are three dice with the same value
                for (int i = 0; i < dice.length - 2; i++) {
                    if (dice[i].getValue() == dice[i + 1].getValue() && dice[i].getValue() == dice[i + 2].getValue()) {
                        value = getDiceValue();
                        break;
                    }
                }
                break;
            case "Four of a kind":
                // check if any four dice have the same value
                for (int i = 0; i < dice.length - 3; i++) {
                    if (dice[i].getValue() == dice[i + 1].getValue()
                            && dice[i + 1].getValue() == dice[i + 2].getValue()
                            && dice[i + 2].getValue() == dice[i + 3].getValue()) {
                        value = getDiceValue();
                    }
                }
                break;
            case "Full house":
                // 3 of a kind and 2 of a kind in the same roll
                if (dice[0].getValue() == dice[1].getValue()
                        && dice[1].getValue() == dice[2].getValue()
                        && dice[3].getValue() == dice[4].getValue()) {
                    value = 25;
                } else if (dice[0].getValue() == dice[1].getValue()
                        && dice[2].getValue() == dice[3].getValue()
                        && dice[3].getValue() == dice[4].getValue()) {
                    value = 25;
                }
                break;
            case "Small straight":
                // Small straight is 4 consecutive dice, doesn't matter which ones
                if (dice[0].getValue() == dice[1].getValue() - 1 // Check if the first 4 dice are consecutive
                        && dice[1].getValue() == dice[2].getValue() - 1
                        && dice[2].getValue() == dice[3].getValue() - 1) {
                    value = 30;
                } else if (dice[1].getValue() == dice[2].getValue() - 1 // Check if the last 4 dice are consecutive
                        && dice[2].getValue() == dice[3].getValue() - 1
                        && dice[3].getValue() == dice[4].getValue() - 1) {
                    value = 30;
                }
                break;
            case "Large straight":
                // Large straight is 5 consecutive dice, doesn't matter which ones
                if (dice[0].getValue() == dice[1].getValue() - 1 // Check if the first 5 dice are consecutive
                        && dice[1].getValue() == dice[2].getValue() - 1
                        && dice[2].getValue() == dice[3].getValue() - 1
                        && dice[3].getValue() == dice[4].getValue() - 1) {
                    value = 40;
                }
                break;
            case "Yahtzee":
                // Yahtzee is 5 dice with the same value
                if (dice[0].getValue() == dice[1].getValue()
                        && dice[1].getValue() == dice[2].getValue()
                        && dice[2].getValue() == dice[3].getValue()
                        && dice[3].getValue() == dice[4].getValue()) {
                    value = 50;
                }
                break;
            case "Chance":
                // Chance is the sum of all dice
                value = getDiceValue();
                break;
        }
        for (Score score : possibleMoves) {
            if (score.getMoveName().equals(moveName)) {
                score.setScore(value);
            }
        }
    }

    private int countDice(int i) { // count the number of dice with the value i
        int count = 0;
        for (Die die : dice) {
            if (die.getValue() == i) {
                count++;
            }
        }
        return count * i;
    }

    public int getMoveValue(String moveName) {
        for (Score score : possibleMoves) {
            if (score.getMoveName().equals(moveName)) {
                return score.getScore();
            }
        }
        return 0;
    }

    public Score[] getMoves() {
        Score[] moves = new Score[MOVE_NAMES.length];
        for (int i = 0; i < MOVE_NAMES.length; i++) {
            moves[i] = new Score(MOVE_NAMES[i]);
        }
        return moves;
    }

    @Override
    public String toString() {
        // Return formatted string of dice values and possible moves
        // Every move is on a new line
        String diceString = "";
        for (Die die : dice) {
            diceString += die.getValue() + " ";
        }
        String moveString = "";
        for (Score score : possibleMoves) {
            moveString += score.getMoveName() + ": " + score.getScore() + "\n";
        }
        return diceString + "\n\n" + moveString;
    }

    // Check if all players have played all moves
    public static boolean isGameOver(Player[] players) {
        for (Player player : players) {
            for (Score score : player.getScores()) {
                if (score.getScore() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void fillInMove(String move, Player player) {
        // throw exception if move doesn't exist
        // check in possibleMoves if move exists and get the value
        for (Score score : possibleMoves) {
            if (score.getMoveName().equals(move)) {
                player.setScore(move, score.getScore());
                return;
            }
        }
        throw new IllegalArgumentException("Move doesn't exist");
    }

    public boolean isMoveFilledIn(String move, Player player) {
        // check if move is valid, if it is, check if it is filled in
        // if it is not valid, return false
        for (Score score : possibleMoves) {
            if (score.getMoveName().equals(move)) {
                return player.getScore(move) != 0;
            }
        }
        return false;
    }
}
