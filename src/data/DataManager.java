/*
	Classe DataManager
	Descricao: faz o salvamento e carregamento do jogo
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package data;

import engine.Tetris;

public class DataManager {
    public static void saveGame(Tetris game) {
    	TetrisInfo info = game.salvaJogo();
    	SerializationManager.serialize("src/data/"+ game.getPlayer().getName() +".sav", info);
    }

    public static TetrisInfo loadGame(String playerName) {
        return (TetrisInfo) SerializationManager.deserialize("src/data/"+ playerName +".sav");
    }
}
