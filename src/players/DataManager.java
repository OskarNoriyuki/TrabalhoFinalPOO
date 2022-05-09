/*
	Classe DataManager
	Descricao: faz o salvamento e carregamento do jogo
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package players;

import engine.Tetris;

public class DataManager {
    public static void saveGame(Tetris game) {
        SerializationManager.serialize("src/players/"+ game.getPlayer().getName() +".sav", game);
    }

    public static Tetris loadGame(String playerName) {
        return (Tetris) SerializationManager.deserialize("src/players/"+ playerName +".sav");
    }
}
