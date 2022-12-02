package be.kdg.integration1.team22.yahtzee.model;

/*
    • Game.java: class that represents a game
        • It should contain a list of players.
        • It should contain a list of scores.
        • It should contain a board.
        • It should contain a method that starts the game.
        • It should contain a method that ends the game.
        • It is responsible for the game logic (rolling the dice, scoring, etc.).
        • It is responsible for the game flow (which player is playing, etc.).
        • It should contain a method to check if the game is finished.
 */

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Game {
    private final Player[] players;
    private final Score[] scores;
    private final GameBoard gameBoard = new GameBoard();
    private int currentPlayerIndex = 0;
    private boolean gameFinished = false;

    public Game(Player[] players, Score[] scores) {
        this.players = players;
        this.scores = scores;
    }

    public void startGame() throws SQLException {
        for (int i = 0; i < players.length; i++) {
            scores[i] = new Score(players[i].getId(), players[i].getName());
        }
        while (!isGameFinished()) {
            // Add scores for each player to the scores array

            System.out.println();
            System.out.println("It's " + players[currentPlayerIndex].getName() + "'s turn.");

            helpMenu();
            askForCommand();
        }
        for (Score score : scores) {
            if (score.getGrandTotal() != 0) {
                score.saveScore();
            }
        }
        scores[0].stop();
    }

    private void askForCommand() throws SQLException {
        System.out.println("Enter a command: ");
        checkCommand(getInput());
    }

    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void checkCommand(String command) throws SQLException {
        switch (command) {
            case "q" -> {
                System.out.println("Quitting...");
                stop();
            }
            case "r" -> {
                gameBoard.rollAllDice();
                askToRollAgain:
                {for (int i = 0; i < 2; i++) {
                    // Ask the user if he wants to roll the dice again.
                    // Keep asking until the user enters a valid answer.
                    System.out.println("Do you want to roll the dice again? (y/n)");
                    boolean validAnswer = false;
                    while (!validAnswer) {
                        String rollAgain = getInput();
                        if (rollAgain.equals("y")) {
                            // Roll the dice again.
                            validAnswer = true;
                            selectDice();
                        } else if (rollAgain.equals("n")) {
                            break askToRollAgain;
                        } else {
                            System.out.println("Invalid answer. Please enter 'y' or 'n'.");
                        }
                    }
                }}
                selectCategory();
            }
            case "s" -> {
                System.out.printf("Score for %s:\n%s\n", getCurrentPlayer().getName(), getCurrentPlayerScore());
            }
            case "l" -> {
                scores[0].getLeaderboard();
                System.out.println();
                System.out.println("Enter c to continue or the name of a player to see his/her score:");
                String input = getInput();
                if (!input.equals("c")) {
                    scores[0].lookUpScore(input);
                }

            }
            case "h" -> {
                helpMenu();
            }
            default -> {
                System.out.println("Invalid command.");
                helpMenu();
            }
        }
    }

    private void selectDice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the dice you want to roll (a for all): ");
        if (scanner.hasNext()) {
            String diceToRoll = scanner.nextLine();
            if (diceToRoll.equals("a")) {
                // Roll all dice.
                gameBoard.rollAllDice();
            } else {
                // Roll the dice that the user entered.
                String[] diceToRollArray = diceToRoll.replaceAll("\\s", "").split(",");
                for (String dice : diceToRollArray) {
                    if (dice.matches("[1-5]")) {
                        gameBoard.rollDie(Integer.parseInt(dice));
                    } else {
                        System.out.println("Invalid dice.");
                    }
                }
                System.out.println();
                System.out.println(gameBoard);
            }
        }
    }

    private void selectCategory() {
        // Print the categories.
        for (int i = 0; i < Category.values().length; i++) {
            System.out.println(i + 1 + ". " + Category.values()[i] + ": " + (getCurrentPlayerScore().getCategoryScore(Category.values()[i]) == -1 ? gameBoard.calculateScore(Category.values()[i]) : "-"));
        }
        // Ask the user to select a category.
        System.out.println("Select a category: ");
        selectCategory(getInput());
    }

    private void selectCategory(String cat) {
        int category = Integer.parseInt(cat);
        if (category > 0 && category < Category.values().length + 1) {
            if (isCategoryFilled(category)) {
                System.out.println("Category already filled.");
                System.out.println("Please select a different category: ");
                selectCategory(getInput());
            } else {
                // Calculate the score for the category.
                int score = gameBoard.calculateScore(Category.values()[category - 1]);
                if(isScoreZero(score)) {
                    // Ask the user if he wants to select a different category.
                    System.out.println("You did not score any points. Are you sure you want to select this category? (y/n)");
                    boolean validAnswer = false;
                    while (!validAnswer) {
                        switch (getInput()) {
                            case "y" -> {
                                // Set the score for the category.
                                getCurrentPlayerScore().setScorePerCategory(Category.values()[category - 1], score);
                                // Set the next player.
                                setNextPlayer();
                                validAnswer = true;
                            }
                            case "n" -> {
                                selectCategory();
                            }
                            default -> System.out.println("Invalid answer. Please enter 'y' or 'n'.");
                        }
                    }
                }
                // Set the score for the category.
                getCurrentPlayerScore().setScorePerCategory(Category.values()[category - 1], score);
                // Print the scores.
                System.out.println();
                System.out.println("Printing scores for player: " + getCurrentPlayer().getName());
                for (Score s : scores) {
                    if (s.getPlayerId() == getCurrentPlayer().getId()) {
                        System.out.println(s);
                    }
                }
                // Set the next player.
                setNextPlayer();
            }
        }
    }

    private boolean isCategoryFilled(int category) {
        return getCurrentPlayerScore().isScoreSet(category - 1);
    }

    private boolean isScoreZero(int score) {
        return score == 0;
    }

    private void helpMenu() {
        System.out.println();
        System.out.println("Commands:");
        System.out.println("r: Roll dice");
        System.out.println("s: Show scores");
        System.out.println("l: Show leaderboard");
        System.out.println("q: Quit");
    }

    private void setNextPlayer() {
        // This method should set the next player.
        // If the current player is the last player, the next player should be the first player.
        if (currentPlayerIndex == players.length - 1) {
            currentPlayerIndex = 0;
        } else {
            currentPlayerIndex++;
        }
    }

    private Player getCurrentPlayer() {
        // This method should return the current player.
        return players[currentPlayerIndex];
    }

    private Score getCurrentPlayerScore() {
        // This method should look up the current player score.
        // It should return the current player score as a string.
        for (Score score : scores) {
            if (score.getPlayerId() == getCurrentPlayer().getId()) {
                return score;
            }
        }
        return null;
    }

    private void stop() throws SQLException {
        // This method should end the game.
        // It should print the final scores.
        // It should ask the user if he wants to play another game.
        // If the user wants to play another game, it should start a new game.
        // If the user doesn't want to play another game, it should quit the application.
        System.out.println("Final scores:");
        for (Score score : scores) {
            System.out.println();
            System.out.println(score);
        }
        // Make isGameFinished() return true.
        // This will end the game loop.
        setGameFinished(true);
    }

    public boolean isGameFinished() {
        // This method should check if the game is finished.
        // It should check if all players have played.
        // It should check if all scores are set.
        // It should return true if the game is finished.
        // It should return false if the game is not finished.
        return gameFinished;
    }

    private void setGameFinished(boolean b) {
        // This method should set the gameFinished variable to true.
        // This will end the game loop.
        gameFinished = b;
    }

    private static class GameBoard {
        // This class should represent the game board.
        // It should contain a list of dice.
        // It should contain a method to roll all dice.
        // It should contain a method to roll a single dice.
        // It should contain a method to print the dice.

        private final Die[] dice = new Die[5];

        public GameBoard() {
            for (int i = 0; i < dice.length; i++) {
                dice[i] = new Die();
            }
        }

        private void rollAllDice() {
            for (Die die : dice) {
                die.roll();
            }
            System.out.println();
            System.out.println(this);
        }

        private void rollDie(int diceNumber) {
            dice[diceNumber - 1].roll();
        }

        private void sortDice() {
            Arrays.sort(dice, Comparator.comparingInt(Die::getValue));
        }

        private int calculateScore(Category category) {
            int score = 0;
            sortDice();
            switch (category) {
                case ONES -> {
                    for (Die die : dice) {
                        if (die.getFaceValue() == 1) {
                            score += 1;
                        }
                    }
                }
                case TWOS -> {
                    for (Die die : dice) {
                        if (die.getFaceValue() == 2) {
                            score += 2;
                        }
                    }
                }
                case THREES -> {
                    for (Die die : dice) {
                        if (die.getFaceValue() == 3) {
                            score += 3;
                        }
                    }
                }
                case FOURS -> {
                    for (Die die : dice) {
                        if (die.getFaceValue() == 4) {
                            score += 4;
                        }
                    }
                }
                case FIVES -> {
                    for (Die die : dice) {
                        if (die.getFaceValue() == 5) {
                            score += 5;
                        }
                    }
                }
                case SIXES -> {
                    for (Die die : dice) {
                        if (die.getFaceValue() == 6) {
                            score += 6;
                        }
                    }
                }
                case THREE_OF_A_KIND -> {
                    int[] faceValues = new int[6];
                    for (Die die : dice) {
                        faceValues[die.getFaceValue() - 1]++;
                    }
                    for (int faceValue : faceValues) {
                        if (faceValue >= 3) {
                            for (Die die : dice) {
                                score += die.getFaceValue();
                            }
                            break;
                        }
                    }
                }
                case FOUR_OF_A_KIND -> {
                    int[] faceValues = new int[6];
                    for (Die die : dice) {
                        faceValues[die.getFaceValue() - 1]++;
                    }
                    for (int faceValue : faceValues) {
                        if (faceValue >= 4) {
                            for (Die die : dice) {
                                score += die.getFaceValue();
                            }
                            break;
                        }
                    }
                }
                case FULL_HOUSE -> {

                    boolean threeConsecutive = false;
                    boolean twoConsecutive = false;
                    for (int i = 0; i < dice.length - 2; i++) {
                        if (dice[i].getFaceValue() == dice[i + 1].getFaceValue() && dice[i].getFaceValue() == dice[i + 2].getFaceValue()) {
                            threeConsecutive = true;
                            for (int j = 0; j < dice.length - 1; j++) {
                                if (dice[j].getFaceValue() != dice[i].getFaceValue() && dice[j].getFaceValue() == dice[j + 1].getFaceValue()) {
                                    twoConsecutive = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (threeConsecutive && twoConsecutive) {
                        score = 25;
                    }
                }
                case SMALL_STRAIGHT -> {
                    // Check if there are 4 consecutive dice.
                    boolean fourConsecutive = false;
                    for (int i = 0; i < dice.length - 3; i++) {
                        if (dice[i].getFaceValue() == dice[i + 1].getFaceValue() - 1 && dice[i].getFaceValue() == dice[i + 2].getFaceValue() - 2 && dice[i].getFaceValue() == dice[i + 3].getFaceValue() - 3) {
                            fourConsecutive = true;
                            break;
                        }
                    }
                    if (fourConsecutive) {
                        score = 30;
                    }
                }
                case LARGE_STRAIGHT -> {
                    // Check if there are 5 consecutive dice.
                    boolean fiveConsecutive = false;
                    for (int i = 0; i < dice.length - 4; i++) {
                        if (dice[i].getFaceValue() == dice[i + 1].getFaceValue() - 1 && dice[i].getFaceValue() == dice[i + 2].getFaceValue() - 2 && dice[i].getFaceValue() == dice[i + 3].getFaceValue() - 3 && dice[i].getFaceValue() == dice[i + 4].getFaceValue() - 4) {
                            fiveConsecutive = true;
                            break;
                        }
                    }
                    if (fiveConsecutive) {
                        score = 40;
                    }
                }
                case YAHTZEE -> {
                    // Check if all dice have the same face value.
                    boolean allSame = true;
                    for (int i = 0; i < dice.length - 1; i++) {
                        if (dice[i].getFaceValue() != dice[i + 1].getFaceValue()) {
                            allSame = false;
                            break;
                        }
                    }
                    if (allSame) {
                        score = 50;
                    }
                }
                case CHANCE -> {
                    for (Die die : dice) {
                        score += die.getFaceValue();
                    }
                }
            }
            return score;
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dice.length; i++) {
                sb.append("Die ").append(i + 1).append(": ").append(dice[i].getValue()).append("\n");
            }
            return sb.toString();
        }
    }
}
