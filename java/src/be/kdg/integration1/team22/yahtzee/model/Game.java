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
}
