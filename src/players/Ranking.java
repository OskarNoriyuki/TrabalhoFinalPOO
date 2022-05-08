/*
    Class Ranking
    Authors: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package players;

import java.util.ArrayList;
import java.util.Comparator;

public class Ranking {
    ArrayList<Player> players;
    final static private int TAMANHO = 10;

    public Ranking() {
        this.players = (ArrayList<Player>) ManipuladorSerializaveis.desserializa("ranking.bin");
    }

    public void update(Player player) {
        this.players.add(player);             // Adiciona jogador ao ArrayList
        
        this.players.sort(new Comparator<Player>() {
            public int compare(Player player1, Player player2) {
                // Double.compare compara doubles
                return Double.compare(player1.getScore(), player2.getScore());
            }
        });    // Ordena do menor ao maior
        
        this.players.remove(Ranking.TAMANHO);     // Remove o jogador que ficou fora do ranking

        // Salva o ranking de jogadores no arquivo ranking.bin
        ManipuladorSerializaveis.serializa("ranking.bin", players);
    }
}