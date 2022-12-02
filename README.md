# ASCII Implementation of Yahtzee

This is a simple implementation of the game Yahtzee in Java.
At the moment, the command line visuals quite boring, 
but we will use screens in the future.

## How to run

To run the game, the user has to run the YahtzeeApplication class.
<br>
What does the user have to do to run the game?
</br>

<ul>
    <li> The user should install Java 17 </li>
    <li> User also needs to add postgres database password as an environment variable called "DB_PASSWORD" </li>  
</ul>

## Rules

The game is played with 5 dice and 13 rounds. 
In each round, the player rolls the dice up to three times and then scores the roll in one of 13 categories. 
If the category has already been used in this game, 
the player may not use it again in the same game. 
Once a category for a roll has been selected, 
it may not be changed. 

If the player decides not to use one of the score categories in a round, 
the score for that round is zero. 
The game ends when all 13 categories have been given a score. 
The score of the game is the sum of scores in each of the 13 categories. 
The game does not have to be completed in 13 rounds. 
If the player decides to stop playing before completing 13 rounds, 
the unused categories are scored as zero. 
The player with the highest score wins.
