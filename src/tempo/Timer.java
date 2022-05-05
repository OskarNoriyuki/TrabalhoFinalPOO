package tempo;

import java.io.IOException;

import graphics.TetrisField;

public class Timer implements Runnable{
	
	Thread tetrisThread; //tarefa principal do jogo, atualmente 1 tick = 1 frame (jogo roda junto ao refresh da tela);
	private final long targetFPS;
	private final long loopInterval_ns; //nanossegundos
	private TetrisField campoTetris;
	private double dutyCycle = 0.0;
	private long loopCount = 0;
	private long loopTime[] = new long[60];
	private final int debugMessageFreq = 3; //usar apenas divisores perfeitos de fps
	
	//construtor da classe gerenciadora do loop do jogo
	public Timer(TetrisField campoTetris, int fps) {
		//referencia para o painel do jogo
		this.campoTetris = campoTetris;
		
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
		while(tetrisThread != null) {
			
			long startTime = System.nanoTime();
			//*************************************trabalho útil do loop começa aqui**********************************************
			//print de debug:
			String dutyCyclePerc = String.format("%.2f", this.dutyCycle*100.0);
 			if(loopCount%(targetFPS/debugMessageFreq) == 0) {
 				System.out.println("Use W,S,A,D para mover. Use J, K para rotacionar. \nCiclo ativo medio nos ultimos "+(this.targetFPS/this.debugMessageFreq)+" frames: "+dutyCyclePerc+"%");
 				campoTetris.debug();
 				campoTetris.oneStepDown(); //roda uma iteracao do jogo, a peca cai uma casa
 			}
			campoTetris.repaint(); //atualizacao dos elementos graficos
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
				System.out.println("Não consigo dormir!");
				//e.printStackTrace();
			}
		}
	}
	
	//nao usar
	public void clearConsole() {
		try{
			if(System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
			}else {
				System.out.print("\033\143");
			}
		} catch(IOException eio) {}
		catch(InterruptedException ei) {}
	}
	

}
