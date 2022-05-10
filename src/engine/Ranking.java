/*
	Classe Ranking
	Descricao: mantem uma lista de jogadores, que eh construida a partir da leitura de um
    arquivo. Um jogador eh salvo no ranking caso sua pontuacao seja suficientemente grande
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package engine;

import java.util.ArrayList;
import java.util.Comparator;

import data.SerializationManager;

public class Ranking {
    private ArrayList<Player> players;      // Lista de jogadore do ranking
    final static private int TAMANHO = 10;  // Quantidade de jogadores no ranking

    // Construtor
    public Ranking() {
        // Le arquivo contendo jogadores do ranking
        this.players = (ArrayList<Player>) SerializationManager.deserialize("src/data/ranking.bin");
    }

    // Metodo que insere o jogador no ranking, caso ele consiga entrar
    public void update(Player player) {
        this.players.add(player);   // Adiciona jogador a lista
        
        // Ordena o ranking de maior a menor
        this.players.sort(new Comparator<Player>() {
            public int compare(Player player1, Player player2) {
                return Double.compare(player1.getScore(), player2.getScore())*-1;
            }
        });
        //this.players.sort(new Ordenador());
        this.players.remove(Ranking.TAMANHO);   // Remove o jogador que ficou fora do ranking

        // Salva o ranking de jogadores
        SerializationManager.serialize("src/data/ranking.bin", players);
    }

    // Metodo que retorna uma string contendo todos jogadores e seus pontos
    public String toString() {
        String ranking = "<html>";
        
        for (Player player : this.players)
            ranking += String.format("%s: %d pts<br/>", player.getName(), player.getScore());

        ranking += "<html>";
        return ranking;
    }
}