/*
	Classe Player
	Descricao: mantem o nome do jogador junto de sua pontuacao
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package players;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;    // Nome do jogador
    private int score;      // Pontuacao do jogador

    private static final long serialVersionUID = 123L;

    // Construtor
    public Player(String name) {
        this.name = name;
        this.score = 0; // Pontuacao comeca zerada
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