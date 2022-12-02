package be.kdg.integration1.team22.yahtzee;

import be.kdg.integration1.team22.yahtzee.model.Game;
import be.kdg.integration1.team22.yahtzee.model.Player;
import be.kdg.integration1.team22.yahtzee.model.Score;

import java.sql.SQLException;
import java.util.Scanner;

public class YahtzeeApplication {
    public static void main(String[] args) throws SQLException {
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
