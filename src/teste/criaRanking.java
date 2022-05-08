package teste;

import java.util.ArrayList;
import players.*;

public class criaRanking {
    public static void main(String[] args) {
        // Le ArrayList's presentes nos arquivos dicionario.poo e ranking.poo
        ArrayList<Player> jogadores =  new ArrayList<Player>();
        
        Player jogador = new Player("Fulano de tal");        // Instancia novo jogador
        jogador.setScore(0);
        for (int i = 0; i < 10; i++) {
            jogadores.add(jogador);
        }

        // Salva o ranking de jogadores no arquivo ranking.poo
        players.ManipuladorSerializaveis.serializa("ranking.bin", jogadores);
    }
}