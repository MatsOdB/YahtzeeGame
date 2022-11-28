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

    public void setPossibleMoves() {
        possibleMoves.clear();
        for (String moveName : MOVE_NAMES) {
            possibleMoves.add(new Score(moveName));
            setMoveValue(moveName);
        }
    }

    private void setMoveValue(String moveName) {
        int value = 0;
        switch (moveName) {
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
                if (dice[0].getValue() == dice[1].getValue()
                        && dice[1].getValue() == dice[2].getValue()) {
                    value = getDiceValue();
                } else if (dice[1].getValue() == dice[2].getValue()
                        && dice[2].getValue() == dice[3].getValue()) {
                    value = getDiceValue();
                } else if (dice[2].getValue() == dice[3].getValue()
                        && dice[3].getValue() == dice[4].getValue()) {
                    value = getDiceValue();
                }
                break;
            case "Four of a kind":
                if (dice[0].getValue() == dice[1].getValue()
                        && dice[1].getValue() == dice[2].getValue()
                        && dice[2].getValue() == dice[3].getValue()) {
                    value = getDiceValue();
                } else if (dice[1].getValue() == dice[2].getValue()
                        && dice[2].getValue() == dice[3].getValue()
                        && dice[3].getValue() == dice[4].getValue()) {
                    value = getDiceValue();
                }
                break;
            case "Full house":
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
                if (dice[0].getValue() == 1 && dice[1].getValue() == 2
                        && dice[2].getValue() == 3 && dice[3].getValue() == 4) {
                    value = 30;
                } else if (dice[1].getValue() == 2 && dice[2].getValue() == 3
                        && dice[3].getValue() == 4 && dice[4].getValue() == 5) {
                    value = 30;
                } else if (dice[2].getValue() == 3 && dice[3].getValue() == 4
                        && dice[4].getValue() == 5 && dice[5].getValue() == 6) {
                    value = 30;
                }
                break;
            case "Large straight":
                if (dice[0].getValue() == 1 && dice[1].getValue() == 2
                        && dice[2].getValue() == 3 && dice[3].getValue() == 4
                        && dice[4].getValue() == 5) {
                    value = 40;
                } else if (dice[1].getValue() == 2 && dice[2].getValue() == 3
                        && dice[3].getValue() == 4 && dice[4].getValue() == 5
                        && dice[5].getValue() == 6) {
                    value = 40;
                }
                break;
            case "Yahtzee":
                if (dice[0].getValue() == dice[1].getValue()
                        && dice[1].getValue() == dice[2].getValue()
                        && dice[2].getValue() == dice[3].getValue()
                        && dice[3].getValue() == dice[4].getValue()) {
                    value = 50;
                }
                break;
            case "Chance":
                value = getDiceValue();
                break;
        }
        for (Score score : possibleMoves) {
            if (score.getMoveName().equals(moveName)) {
                score.setScore(value);
            }
        }
    }

    private int countDice(int i) {
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
        return "Board{" +
                "dice=" + Arrays.toString(dice) +
                ", possibleMoves=" + possibleMoves +
                '}';
    }
}
