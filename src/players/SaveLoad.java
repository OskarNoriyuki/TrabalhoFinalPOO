package players;

import engine.Tetris;

public class SaveLoad {
    public static void saveGame(Tetris game) {
        ManipuladorSerializaveis.serializa("src/players/"+ game.getPlayer().getName() +".sav", game);
    }

    public static Tetris loadGame(String playerName) {
        return (Tetris) ManipuladorSerializaveis.desserializa("src/players/"+ playerName +".sav");
    }
}
