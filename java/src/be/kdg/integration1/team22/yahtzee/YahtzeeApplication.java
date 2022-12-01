package be.kdg.integration1.team22.yahtzee;

/*
• main() method which

    • creates an example game object (and all its associated objects)
      reflecting the game in an example state somewhere in the middle
      of a game with 2 players.

    • print the content of that game object (and recursively its associated objects)
 */

import be.kdg.integration1.team22.yahtzee.model.Game;

public class YahtzeeApplication {
    public static void main(String[] args) {
        Game game = new Game();

        game.start();

//        game.addPlayer("Player 1");
//        game.addPlayer("Player 2");
//        game.getBoard().rollDice();
//        game.getBoard().setPossibleMoves();
//        System.out.println(game.getBoard().toString());
    }
}
