package be.kdg.integration1.team22.yahtzee.model;

/*
    • Player.java: class that represents a player
        • It should contain a name.
        • It should contain an ID that is unique and is generated automatically.
 */

public class Player {
    private final String name;
    private final int id;

    private static int nextId = 1;

    public Player(String name) {
        this.name = name;
        this.id = nextId++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
