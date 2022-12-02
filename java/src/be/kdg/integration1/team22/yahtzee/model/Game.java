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

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Game {
    private final Player[] players;
    private final Score[] scores;
    private final Scanner scanner = new Scanner(System.in);
    private final GameBoard gameBoard = new GameBoard();
    private int currentPlayerIndex = 0;
    private boolean gameFinished = false;

    public Game(Player[] players, Score[] scores) {
        this.players = players;
        this.scores = scores;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Score[] getScores() {
        return scores;
    }

    public void startGame() {
        for (int i = 0; i < players.length; i++) {
            scores[i] = new Score(players[i].getId());
        }
        while (!isGameFinished()) {
            // Add scores for each player to the scores array

            System.out.println("Current player: " + getCurrentPlayer().getName());
            System.out.println("Current score for player with " + getCurrentPlayerScore());

            System.out.println("Enter a command (h for help): ");
            // Check if the user entered a valid command.
            // If the user entered a valid command, handle the command.
            // If the user entered an invalid command, print an error message.
            if (scanner.hasNext()) {
                String command = scanner.nextLine();
                while (command.isEmpty()) {
                    command = scanner.nextLine();
                }
                switch (command) {
                    case "h" -> {
                        System.out.println("Commands:");
                        System.out.println("h: show help");
                        System.out.println("q: quit");
                        System.out.println("r: roll dice");
                        System.out.println("s: print scores");
                    }
                    case "q" -> {
                        System.out.println("Quitting...");
                        stop();
                    }
                    case "r" -> {
                        // This method should ask the user which dice he wants to roll.
                        // The input can be empty, in which case all dice should be rolled.
                        // The input can be a comma-separated list of dice numbers.

                        // The user can roll the dice 3 times.
                        // After each roll, the user should be asked which dice he wants to roll.
                        // After the third roll, the user should be asked to select a category.
                        // The user should be asked to select a category until he selects a valid category that is not already filled.
                        System.out.println("Rolling dice...");
                        gameBoard.rollAllDice();
                        System.out.println(gameBoard);
                        // While the user wants to roll the dice, roll the dice.
                        label:
                        {
                            for (int i = 0; i < 2; i++) {
                                // Ask the user if he wants to roll the dice again.
                                // Keep asking until the user enters a valid answer.
                                System.out.println("Do you want to roll the dice again? (y/n)");
                                boolean validAnswer = false;
                                while (!validAnswer) {
                                    String rollAgain = scanner.nextLine();
                                    if (rollAgain.equals("y")) {
                                        // Roll the dice again.
                                        validAnswer = true;
                                    } else if (rollAgain.equals("n")) {
                                        break label;
                                    } else {
                                        System.out.println("Invalid answer. Please enter 'y' or 'n'.");
                                    }
                                }

                                // Ask the user which dice he wants to roll.
                                System.out.println("Enter the dice you want to roll (empty for all): ");
                                if (scanner.hasNext()) {
                                    String diceToRoll = scanner.nextLine();
                                    if (diceToRoll.isEmpty()) {
                                        // Roll all dice.
                                        gameBoard.rollAllDice();
                                    } else {
                                        // Roll the dice that the user entered.
                                        String[] diceToRollArray = diceToRoll.replaceAll("\\s", "").split(",");
                                        for (String dice : diceToRollArray) {
                                            gameBoard.rollDie(Integer.parseInt(dice));
                                        }
                                    }
                                }
                                // Print the dice.
                                System.out.println(gameBoard);
                            }
                        }
                        // Print all categories and print the possible score for each category that is not already filled.
                        // Print "-" for categories that are already filled.
                        for (int i = 0; i < Category.values().length; i++) {
                            System.out.println(i + 1 + ". " + Category.values()[i] + ": " + (getCurrentPlayerScore().getCategoryScore(Category.values()[i]) == -1 ? gameBoard.calculateScore(Category.values()[i]) : "-"));
                        }
                        // Ask the user to select a category.
                        System.out.println("Select a category: ");
                        // Check if the user entered a valid category.
                        // If the user did not enter a valid category, ask the user again.
                        boolean validCategory = false;
                        while (!validCategory) {
                            if (scanner.hasNext()) {
                                int category = scanner.nextInt();
                                if (category > 0 && category < Category.values().length + 1) {
                                    // Check if the category is already filled.
                                    // If the category is already filled, ask the user again.
                                    if (getCurrentPlayerScore().getCategoryScore(Category.values()[category - 1]) == -1) {
                                        // Calculate the score for the category.
                                        int score = gameBoard.calculateScore(Category.values()[category - 1]);
                                        // Set the score for the category.
                                        getCurrentPlayerScore().setScorePerCategory(Category.values()[category - 1], score);
                                        // Set the next player.
                                        setNextPlayer();
                                        if (score != -1) {
                                            validCategory = true;
                                            System.out.println("Score: " + score);
                                        } else {
                                            System.out.println("You did not score any points. Are you sure you want to select this category? (y/n)");
                                            boolean validAnswer = false;
                                            while (!validAnswer) {
                                                String answer = scanner.nextLine();
                                                if (answer.equals("y")) {
                                                    // Set the score for the category.
                                                    getCurrentPlayerScore().setScorePerCategory(Category.values()[category + 1], score);
                                                    // Set the next player.
                                                    setNextPlayer();
                                                    validCategory = true;
                                                    validAnswer = true;
                                                } else if (answer.equals("n")) {
                                                    // Ask the user to select a category.
                                                    System.out.println("Select a category: ");
                                                    validAnswer = true;
                                                } else {
                                                    System.out.println("Invalid answer. Please enter 'y' or 'n'.");
                                                }
                                            }
                                        }
                                    } else {
                                        System.out.println("Category already filled.");
                                        System.out.println("Select a category: ");
                                    }
                                } else {
                                    System.out.println("Invalid category.");
                                    System.out.println("Select a category: ");
                                }
                            }
                        }
                    }
                    case "s" -> {
                        System.out.println("Printing scores for player: " + getCurrentPlayer().getName());
                        // Print the scores.
                        for (Score score : scores) {
                            if (score.getPlayerId() == getCurrentPlayer().getId()) {
                                System.out.println(score);
                            }
                        }
                    }
                    default -> System.out.println("Invalid command.");
                }
            }
            System.out.println();
        }
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

    private void stop() {
        // This method should end the game.
        // It should print the final scores.
        // It should ask the user if he wants to play another game.
        // If the user wants to play another game, it should start a new game.
        // If the user doesn't want to play another game, it should quit the application.
        System.out.println("Final scores:");
        for (Score score : scores) {
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
