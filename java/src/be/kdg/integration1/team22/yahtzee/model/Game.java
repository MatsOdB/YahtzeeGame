package be.kdg.integration1.team22.yahtzee.model;

/*
    * This class represents a game of Yahtzee.
    * A game has a board and a list of players.
    * A game can be played by rolling the dice and filling in the scores.

    Attributes:
    * - Board board: the board of the game
    * - ArrayList<Player> players: the players of the game

    Methods:
    * + Game()
    * + getBoard(): Board
    * + getPlayers(): ArrayList<Player>
    * + addPlayer(name: String): void
    * + removePlayer(name: String): void
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private final Board board = new Board();
    private final ArrayList<Player> players = new ArrayList<>();

    public Game() {
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    public void removePlayer(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                players.remove(player);
                return;
            }
        }
    }

    // start function
    public void start() {
        // game loop until all players have filled in all their scores
        // ask for the amount of players and ask for the names of the players

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Yahtzee!");
        System.out.print("Enter the amount of players: ");
        // repeat question until a valid number is entered
        int amountOfPlayers = 0;
        while (amountOfPlayers < 1) {
            try {
                amountOfPlayers = scanner.nextInt();
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
        for (int i = 0; i < amountOfPlayers; i++) {
            System.out.print("Enter the name of player " + (i) + ": ");
            // wait for the name of the player
            String name = scanner.next();
            // add the player to the game
            addPlayer(name);
        }

        // start game loop
        while (!Board.isGameOver(getPlayers().toArray(new Player[players.size()]))) {
            // loop through all players
            for (Player player : players) {
                // Show which player's turn it is
                System.out.println("It's " + player.getName() + "'s turn!");
                // ask user to enter a command (roll, score, quit)
                System.out.print("Enter a command r, s, q (roll, score, quit): ");
                String command = scanner.next();

                // check if the command is valid
                while (!command.equals("r") && !command.equals("s") && !command.equals("q")) {
                    System.out.print("Please enter a valid command!\n Enter a command r, s, q (roll, score, quit): ");
                    command = scanner.next();
                }

                // if the command is roll, roll the dice
                if (command.equals("r")) {
                    board.rollDice();
                    board.setPossibleMoves();
                    System.out.println(board);

                    String move;
                    do {
                        // ask user to choose a move to fill in
                        System.out.print("Enter a move to fill in: ");
                        move = scanner.nextLine();

                        try {
                            // fill in the move
                            board.fillInMove(move, player);
                            System.out.printf("Added %d to %s's %s score.\n", player.getScore(move), player.getName(), move);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    } while (!board.isMoveFilledIn(move, player));
                }
                // if the command is score, show the current player's scores
                else if (command.equals("s")) {
                    System.out.println("Your scores:");
                    for (Score score : player.getScores()) {
                        System.out.println(score.getMoveName() + ": " + score.getScore());
                    }
                }
                // if the command is quit, quit the game
                else {
                    System.out.println("Quitting game...");
                    return;
                }
            }
        }

    }
}

