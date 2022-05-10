/*
	Classe Timer
	Descricao: Classe que estabelece a base de tempo e controla as funcionalidades sincronas do programa
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package tempo;

import java.io.IOException;

import javax.swing.JFrame;

import engine.Ranking;
import engine.Tetris;
import graphics.GameWindow;
import graphics.MenuWindow;
import graphics.RankingWindow;
import graphics.StatsField;
import graphics.TetrisField;
import sounds.SoundPlayer;
import data.DataManager;
import data.SerializationManager;

public class Timer implements Runnable {
	
	Thread tetrisThread; //tarefa principal do game, atualmente 1 tick = 1 frame (game roda junto ao refresh da tela);
	private final long targetFPS;
	private final long loopInterval_ns; //nanossegundos
	private GameWindow gameWindow;
	private Tetris game;
	private TetrisField painelJogo;
	private StatsField painelAux;
	private double dutyCycle;
	private long loopCount;
	private long loopTime[] = new long[60];
	private final int debugMessageFreq = 3; //usar apenas divisores perfeitos de fps
	//a velocidade maxima eh atingida no nivel 23 (stepDownPeriod[23]), 60 casas por segundo, 1 por frame
	//a velocidade inicial depende da dificuldade
	private final int stepDownPeriod[][] = {
		{60,60,50,42,35,29,24,20,17,14,12,10,8,7,6,5,4,3,3,2,2,2,1,1,1},
		{42,35,29,24,20,17,14,12,10,8,7,6,5,4,3,3,2,2,2,1,1,1,1,1,1},
		{24,20,17,14,12,10,8,7,6,5,4,3,3,2,2,2,1,1,1,1,1,1,1,1,1}};
	private int periodIndex;
	private int dropCount;
	
	private boolean fimdejogo = false, voltar;
	private int dificuldade;
	
	//construtor da classe gerenciadora do loop do game
	public Timer(Tetris game, int fps, TetrisField campoTetris, StatsField campoAux, GameWindow gameWindow, int dificuldade) {
		this.voltar = false;
		
		//referencias para o painel do game
		this.painelJogo = campoTetris;
		this.painelAux = campoAux;
		this.gameWindow=gameWindow;
		this.dificuldade=dificuldade;
		
		//referencia do game
		this.game = game;
		
		//inicializa variaveis
		this.dutyCycle = 0.0;
		this.loopCount = 0;
		this.dropCount = 0;
		this.periodIndex = 0;
		
		//seta o FPS maximo
		this.targetFPS = fps;
		this.loopInterval_ns = 1000000000/targetFPS;
	}
	
	//inicia a Thread do game
	public void iniciaTetris() {
		tetrisThread = new Thread(this);
		tetrisThread.start();
	}


	public void run() {
		//while(tetrisThread != null) {
		while(!(fimdejogo||voltar)){
			
			long startTime = System.nanoTime();
			//*************************************trabalho util do loop aqui**********************************************
			//print de debug:
			String dutyCyclePerc = String.format("%.2f", this.dutyCycle*100.0);
 			if(loopCount%(targetFPS/debugMessageFreq) == 0) {
 				//System.out.println("Use W,S,A,D para mover. Use J, K para rotacionar. \nCiclo ativo medio nos ultimos "+(this.targetFPS/this.debugMessageFreq)+" frames: "+dutyCyclePerc+"%");
 				//System.out.println("Nivel: "+game.getLevel()+" Linhas: "+game.getLines()+" Pontos: "+game.getScore());
 				this.painelAux.setThreadCycleIndicator(dutyCyclePerc);
 			}
 			
 	    	if(game.getEscape()) {
 	    		switch(game.getOption()) {
 	    			case 0:
 	    				DataManager.saveGame(this.game);
 	    				game.setEscape(false);
 	    				game.setPause(false);
 	    				break;
 	    			case 1:
 	    				this.voltar = true;
 	    				MenuWindow novoMenu = new MenuWindow();
 	    				this.gameWindow.dispose();
 	    				break;
 	    			default:
 	    				break;
 	    		}
 	    	}
 			//processamento do game
 			//loopCount nao eh adequado para guiar os stepDowns, pois podem ocorrer erros nas trocas de frequencia
 			this.dropCount++;
 			if(dropCount == stepDownPeriod[dificuldade-1][periodIndex]) {
 				//roda uma iteracao do game, a peca cai uma casa e testa se perdeu
 				fimdejogo=game.updateGame("goDown");
 				//reseta o contador
 				this.dropCount = 0;
 				//atualiza a frequencia de acordo com o nivel
 				periodIndex= game.getLevel();
 	 			if(periodIndex > 23)
 	 				periodIndex = 23;
 			}
 			
 			//atualizacao dos elementos graficos
			painelJogo.repaint();
			painelAux.repaint();
			//****************************************************loop_end*********************************************************
			this.loopCount++;
			//armazena duracoes de loops anteriores:
			for(int i = 59; i > 0; i--)
				this.loopTime[i] = this.loopTime[i-1];
			//calcula o tempo de loop atual:
			this.loopTime[0] = System.nanoTime() - startTime;
			//calcula a media do ciclo ativo do programa nos ultimos loops desde o ultimo print de debug:
			this.dutyCycle = 0;
			for(int j = 0; j < this.targetFPS/this.debugMessageFreq; j++)
				this.dutyCycle += (double)loopTime[j]/(this.loopInterval_ns*(this.targetFPS/this.debugMessageFreq));
			//a ultima coisa a se fazer deve ser o calculo do tempo ocioso, e dormir ate o proximo loop:
			long endTime = startTime + this.loopInterval_ns;
			long sleepTime = (long)(endTime - System.nanoTime())/1000000;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException | IllegalArgumentException e) {
				System.err.println("Nao consigo dormir!");
			}
			
		}

		//se saiu do loop principal, deve finalizar o jogo ou voltar ao menu
		SoundPlayer.pararLoop();
		SoundPlayer.tocarSom("lose.wav");
		if(this.fimdejogo) {
			Ranking ranking = new Ranking();
			ranking.update(this.game.getPlayer());
			RankingWindow rankingWindow = new RankingWindow();
			this.gameWindow.dispose();
		}else if(this.voltar){
			this.gameWindow.dispose();
		}
	}

}
