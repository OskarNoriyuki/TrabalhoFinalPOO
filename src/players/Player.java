/*
    Class Player
    Authors: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package players;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int score;

    private static final long serialVersionUID = 123L;

    // Construtor
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    // Getters and setters
    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}