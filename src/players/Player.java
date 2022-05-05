/*
    Class Player
    Authors: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package players;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private double score;

    private static final long serialVersionUID = 123L;

    // Construtor
    public Player(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return this.name;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}