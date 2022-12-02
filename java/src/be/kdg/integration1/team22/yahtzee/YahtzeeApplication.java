package be.kdg.integration1.team22.yahtzee;

import be.kdg.integration1.team22.yahtzee.model.Game;
import be.kdg.integration1.team22.yahtzee.model.Player;
import be.kdg.integration1.team22.yahtzee.model.Score;

import java.util.Scanner;

/*
    • YahtzeeApplication.java: main class
        • It is responsible for starting a new game.
        • It should ask the user for the number of players and the names of the players.
        • It should create a new game and start it.
        • When the game is finished, it should ask the user if he wants to play another game.

    • Game.java: class that represents a game
        • It should contain a list of players.
        • It should contain a list of scores.
        • It should contain a board.
        • It should contain a method that starts the game.
        • It should contain a method that ends the game.
        • It is responsible for the game logic (rolling the dice, scoring, etc.).
        • It is responsible for the game flow (which player is playing, etc.).
        • It should contain a method to check if the game is finished.

    • Board.java: class that represents the board
        • It should calculate the score for a given category.
        • It should contain a method that calculates the total score.
        • It should contain a method that calculates the bonus.
        • It should contain a method that calculates the total score with bonus.

    • Player.java: class that represents a player
        • It should contain a name.
        • It should contain an ID.

    • Score.java: class that represents a score
        • It should contain a player ID.
        • It should contain an array of scorePerCategory.

    • ScorePerCategory.java: class that represents a score per category
        • It should contain a category.
        • It should contain a score.

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

    • Die.java: class that represents a die
        • It should contain a value.
        • It should contain a method that rolls the die.
        • It should contain a method that returns the value of the die.
        • It should contain a method that sets the value of the die.
        • It should contain a method that returns the value of the die as a string.
 */
public class YahtzeeApplication {
    public static void main(String[] args) {
        // While the user wants to play another game
        boolean playAnotherGame = true;
        while (playAnotherGame) {
            Scanner in = new Scanner(System.in);

            System.out.println("How many players?");
            // Check if the input is a number and if it is between 1 and 4.
            // If not, ask again.
            int numberOfPlayers = 0;
            while (numberOfPlayers < 1 || numberOfPlayers > 4) {
                try {
                    numberOfPlayers = Integer.parseInt(in.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number between 1 and 4.");
                }
            }

            // Create an array of players. The length of the array is the number of players.
            // Ask the user for the name of each player.
            Player[] players = new Player[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++) {
                String name = "";
                while (name.isEmpty()) {
                    System.out.println("Enter the name of player " + (i + 1) + ":");
                    name = in.nextLine();
                }
                players[i] = new Player(name);
            }

            // Create a new game and start it.
            Game game = new Game(players, new Score[numberOfPlayers]);
            game.startGame();

            String answer = "";
            while (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("Do you want to play another game? (y/n)");
                answer = in.nextLine();
            }
            if (answer.equals("n")) {
                playAnotherGame = false;
            }
        }
    }
}
