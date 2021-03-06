/*
	Classe Tetris
	Descricao: classe responsavel pela logica do jogo Tetris.
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package engine;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;

import data.TetrisInfo;
import pecas.Tetrominoe;
import pecas.TetrominoeI;
import pecas.TetrominoeJ;
import pecas.TetrominoeL;
import pecas.TetrominoeO;
import pecas.TetrominoeS;
import pecas.TetrominoeT;
import pecas.TetrominoeZ;

public class Tetris implements Serializable {
	/**atributos gerais**/
	private boolean perdeu;
	private int pontuacao, linhas, nivel;
	private Player player;
	private boolean pause, escape;
	private int option; //1->menu, 0->salvar
	private final int numOptions = 2; // 2 opcoes
	private TetrisInfo backupLocal;
	
	/**atributos do mapa**/
	private int maxX, maxY;
	private char map[][];
	/*
	 * mapa eh armazenado em uma matriz de char
	 * elementos 'B' simbolizam "background" ou fundo
	 * elementos 'I', 'J', 'L', 'O', 'S', 'T', 'Z' representam cubos das pecas que ja cairam
	 * elemento 'U' = undefined
	 */
	private char preview[][];
	private int previewColNum, previewLinNum;
	
	/**atributos esteticos**/
	private final static int tamanhoCuboOriginal = 20; //20x20 pixels
	private final static int upScale = 2;
	private int tamanhoCubo;
	private final static int previewDownScale = 1; 
	private int tamanhoCuboPreview;
	private BufferedImage backgroundTile;
	private BufferedImage debugTile;
	private BufferedImage previewBgTile;
	private boolean lowRes;
	
	/**atributos de pecas**/
	private final int numTiposPecas = 7, numSorteadas = 4;
	private Tetrominoe pecas[];
	private int proximaPeca[]; 
	/*
	 * representadas por 'I', 'J', 'L', 'O', 'S', 'T', 'Z',
	 * proximaPeca[0] eh a peca ATUAL, e as seguintes sao as proximas a cair.
	 */

	private static final long serialVersionUID = 123L;

	/**********************************************************************************************/
	/*************************************METODOS PUBLICOS*****************************************/
	/**********************************************************************************************/
	
	//construtor, recebe um tamanho de mapa, em cubos
	public Tetris(int lin, int col, boolean lowRes, Player player) {
		//geral
		this.perdeu = false;
		this.pontuacao = 0;
		this.linhas = 0;
		this.nivel = 1;
		this.player = player;
		this.pause = false;
		this.escape = false;
		this.option = 0;
		//tamanho das coisas
		this.lowRes = lowRes;
		if(lowRes) {
			this.tamanhoCubo = 30;
			this.tamanhoCuboPreview = 15;
		}else {
			this.tamanhoCubo = tamanhoCuboOriginal*upScale;
			this.tamanhoCuboPreview = tamanhoCuboOriginal/previewDownScale;
		}
		this.maxX = col;
		this.maxY = lin;
		this.map = new char[lin][col];
		//incializa o mapa com a cor de fundo
		for(int i = 0; i<lin; i++) {
			for(int j = 0; j<col; j++) { 
				map[i][j] = 'B';
			}
		}
		//tamanho do mapa de proximas pecas
		this.previewColNum = 6;
		this.previewLinNum = 6*this.numSorteadas;
		this.preview = new char[previewLinNum][previewColNum];
		//inicializa o mapa de proximas pecas
		for(int i = 0; i<previewLinNum; i++) {
			for(int j = 0; j<previewColNum; j++) { 
				preview[i][j] = 'B';
			}
		}
		//instancia uma peca de cada
		pecas = new Tetrominoe[numTiposPecas];
		pecas[0] = new TetrominoeI();
		pecas[1] = new TetrominoeJ();
		pecas[2] = new TetrominoeL();
		pecas[3] = new TetrominoeO();
		pecas[4] = new TetrominoeS();
		pecas[5] = new TetrominoeT();
		pecas[6] = new TetrominoeZ();
		//eh possivel ver, fora a peca atual, as proximas 3 a cair. inicializa aleatoriamente
		this.proximaPeca = new int[numSorteadas];
		this.proximaPeca[0] = this.getPecaAleatoria(numTiposPecas); //primeira a cair
		//nao caem 2 pecas iguais consecutivas
		for(int i = 1; i < numSorteadas; i++) {
			this.proximaPeca[i] = this.getPecaAleatoria(numTiposPecas, proximaPeca[i-1]);
		}	
		//imagem do cubo de fundo
		try {
			this.backgroundTile = ImageIO.read(new FileInputStream("src/img/map/cube_map_white.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		//imagem do cubo de debug
		try {
			this.debugTile = ImageIO.read(new FileInputStream("src/img/background/black.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		//imagem do cubo de fundo de preview
		try {
			this.previewBgTile = ImageIO.read(new FileInputStream("src/img/background/black.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		//atualiza o mapa de proximas pecas
		this.updatePreview();
	}

	//construtor que recebe um jogo salvo
	public Tetris(TetrisInfo backup) {	
		this(backup.maxY, backup.maxX, backup.lowRes, backup.player);
		
		//retoma o mapa
		for(int i = 0; i<this.maxY; i++) {
			for(int j = 0; j<this.maxX; j++) { 
				this.map[i][j] = backup.map[i][j];
			}
		}
		//retoma as pecas
		for(int i = 0; i<this.numSorteadas; i++) {
			this.proximaPeca[i] = backup.proximaPeca[i];
		}
		
		//retoma a posicao da peca ativa
		this.pecas[this.proximaPeca[0]].x = backup.pecaX;
		this.pecas[this.proximaPeca[0]].y = backup.pecaY;
		
		//retoma pontos, nivel e linhas
		this.pontuacao = backup.pontuacao;
		this.linhas = backup.linhas;
		this.nivel = backup.nivel;
		
	}
	
	//salva os valores de atributos determinantes para a condicao atual do jogo em uma instancia TetrisInfo
	public TetrisInfo salvaJogo() {
		
		this.backupLocal = new TetrisInfo(this.maxY, this.maxX, this.lowRes, this.player);
		
		//salva a proporcao de cubos
		this.backupLocal.maxX = this.maxX;
		this.backupLocal.maxY = this.maxY;
		
		//grava o mapa
		for(int i = 0; i<this.maxY; i++) {
			for(int j = 0; j<this.maxX; j++) { 
				this.backupLocal.map[i][j] = this.map[i][j];
			}
		}
		//grava as pecas
		for(int i = 0; i<this.numSorteadas; i++) {
			this.backupLocal.proximaPeca[i] = this.proximaPeca[i];
		}
		//grava a posicao da peca ativa
		this.backupLocal.pecaX = this.pecas[this.proximaPeca[0]].x;
		this.backupLocal.pecaY = this.pecas[this.proximaPeca[0]].y;
		
		//grava a pontuacao, linhas e nivel
		this.backupLocal.pontuacao = this.pontuacao;
		this.backupLocal.linhas = this.linhas;
		this.backupLocal.nivel = this.nivel;
		
		return this.backupLocal;
	}
	
	//realiza um movimento/acao, calcula as consequencias e atualiza o jogo. deve ser chamado 1x por frame, e 1x por comando
	public boolean updateGame(String acao) {
		Collision colisao;	//classe que armazena infos da colisao
		if(perdeu) {
			//chama window de game over
			//GameWindow.fecharTetris();
			System.err.println("PERDEEUUU!");
			return true;
		}else if(pause) {
			return false;
		}else if(acao.equals("rotateCW") || acao.equals("rotateCCW")) {
			//faz a rotacao
			if(acao.equals("rotateCW")){
				this.pecas[this.proximaPeca[0]].rotacionar("CW");
			}else {
				this.pecas[this.proximaPeca[0]].rotacionar("CCW");
			}
			//apos rotacionar, verifica se ocasionou em colisao
			colisao = this.testaColisao();
			//se sim, verifica se eh possivel adaptar a posicao da peca, a fim de permitir a rotacao sem colisoes. se nao, desfaz a rotacao 
			if(colisao.paredeEsq || colisao.paredeDir ||colisao.peca ||colisao.chao) {

				boolean desistir = false;
				//enquanto nao colide com outras pecas mas colide com alguma parede, tenta "chutar" a peca para esquerda, direita ou para cima a fim de permitir rotacao
				//**********implementar********** -> se a peca ja foi chutada, tenta trazer ela de volta
				if(!colisao.peca) {
					//colisao com parede direita
					if(colisao.paredeDir) {
						//tenta chutar uma casa pra esquerda
						this.pecas[this.proximaPeca[0]].x--;
						this.pecas[this.proximaPeca[0]].x_kick--;
						colisao = this.testaColisao();
						if(colisao.peca) {
							//se bate com outra peca simultaneamente, desiste
							desistir = true;
						}else if(colisao.paredeDir) {
							//tenta chutar mais uma casa pra esquerda
							this.pecas[this.proximaPeca[0]].x--;
							this.pecas[this.proximaPeca[0]].x_kick--;
							colisao = this.testaColisao();
							if(colisao.peca || colisao.paredeDir) {
								desistir = true;
							}
						}
					}
					//colisao com parede esquerda
					if(colisao.paredeEsq) {
						//tenta chutar uma casa pra direita
						this.pecas[this.proximaPeca[0]].x++;
						this.pecas[this.proximaPeca[0]].x_kick++;
						colisao = this.testaColisao();
						if(colisao.peca) {
							//se bate com outra peca simultaneamente, desiste
							desistir = true;
						}else if(colisao.paredeEsq) {
							//tenta chutar mais uma casa pra direita
							this.pecas[this.proximaPeca[0]].x++;
							this.pecas[this.proximaPeca[0]].x_kick++;
							colisao = this.testaColisao();
							if(colisao.peca || colisao.paredeEsq) {
								desistir = true;
							}
						}
					}
					//colisao o chao
					if(colisao.chao) {
						//tenta chutar uma casa pra cima
						this.pecas[this.proximaPeca[0]].y--;
						this.pecas[this.proximaPeca[0]].y_kick--;
						colisao = this.testaColisao();
						if(colisao.peca) {
							//se bate com outra peca simultaneamente, desiste
							desistir = true;
						}else if(colisao.chao) {
							//tenta chutar mais uma casa pra cima
							this.pecas[this.proximaPeca[0]].y--;
							this.pecas[this.proximaPeca[0]].y_kick--;
							colisao = this.testaColisao();
							if(colisao.peca || colisao.chao) {
								desistir = true;
							}
						}
					}
				}else {
					//se eh colisao com peca, nem tenta
					desistir = true;
				}

				if(desistir) {
					//desfaz os chutes
					this.pecas[this.proximaPeca[0]].x -= this.pecas[this.proximaPeca[0]].x_kick;
					this.pecas[this.proximaPeca[0]].y -= this.pecas[this.proximaPeca[0]].y_kick;
					this.pecas[this.proximaPeca[0]].x_kick = 0;
					this.pecas[this.proximaPeca[0]].y_kick = 0;
					//desfaz a rotacao
					if(acao.equals("rotateCW")){
						this.pecas[this.proximaPeca[0]].rotacionar("CCW");
					}else {
						this.pecas[this.proximaPeca[0]].rotacionar("CW");
					}
				}
				
			}
		} else if(acao.equals("goDown") || acao.equals("goDownExtra")) {
			//desce uma casa. essa acao pode ocasionar em atualizacoes no mapa (linha formada, peca terminou de cair) ou ate mesmo o fim de jogo
			this.pecas[this.proximaPeca[0]].y++;
			//testa colisao, as duas colisoes relevantes sao: colisao com o chao; colisao com pecas
			colisao = this.testaColisao();
			if(colisao.chao || colisao.peca) {
				//desfaz o movimento
				this.pecas[this.proximaPeca[0]].y--;
				//salva os cubos no mapa
				this.updateMap();
				//se nenhuma parte da peca ficou fora do mapa, o jogo continua
				if(!colisao.foraDoMapa) {
					//reseta a peca, ativa a proxima e sorteia mais uma
					this.atualizaProxPecas();
					//atualiza o mostrador de proximas pecas
					this.updatePreview();
				}else {
					//acende a flag
					this.perdeu = true;
				}
			}else {
				//se nao aterrissou, adiciona um ponto para cada cada adiantada
				if(acao.equals("goDownExtra"))
					this.pontuacao++;
			}
		} else if(acao.equals("goRight")) {
			//uma casa para a direita
			this.pecas[this.proximaPeca[0]].x++;
			//testa colisao, as duas colisoes relevantes sao: colisao com parede direita; colisao com pecas
			colisao = this.testaColisao();
			if(colisao.paredeDir || colisao.peca) {
				//desfaz o movimento
				this.pecas[this.proximaPeca[0]].x--;
			}

		} else if(acao.equals("goLeft")) {
			//uma casa para a esquerda
			this.pecas[this.proximaPeca[0]].x--;
			//testa colisao, as duas colisoes relevantes sao: colisao com parede esquerda; colisao com pecas
			colisao = this.testaColisao();
			if(colisao.paredeEsq || colisao.peca) {
				//desfaz o movimento
				this.pecas[this.proximaPeca[0]].x++;
			}
		} else if (acao.equals("goUp")) {
			//funcionalidade que sera desabilitada na versao final, subir a peca vai contra as regras do jogo
			this.pecas[this.proximaPeca[0]].y--;
			if(this.pecas[this.proximaPeca[0]].y < 0) {
				this.pecas[this.proximaPeca[0]].y = 0;
			}
		} 
		//se chegou aqui, perdeu == false
		return false;
	}
	
	//retorna a figura correspondente ao cubo das coordenadas X, Y. util para a GUI
	public BufferedImage getCubeImg(int posX, int posY) {
		if(pecaPresente(posX, posY)) {
			//se faz parte da regiao solida da peca ativa atual, retorna a figura da peca
			return this.pecas[proximaPeca[0]].getImage();
		}else {
			//se nao, retorna um cubo do mapa, que inclui background ou pecas acumuladas
			return this.getMapCubeImg(posX, posY);
		}
	}
	
	//metodo que retorna a figura correspondente a coordenadas X, Y (referenciadas na matriz do mapa de proximas pecas)
	public BufferedImage getPreviewCubeImg(int posX, int posY) {
		return this.getMapCubeImg(posX, posY, 1);
		//return this.previewBgTile;
	}

	//metodos importantes para o menu de pausa
	public void moveArrow(boolean direction) {
		if(direction) {
			//incremento
			this.option++;
			//saturacao
			if(this.option == this.numOptions) {
				this.option = this.numOptions - 1;
			}
		}else {
			//decremento
			this.option--;
			//saturacao
			if(this.option == -1) {
				this.option = 0;
			}
		}
	}
	
	//getters
	public boolean isPaused() {
		return this.pause;
	}
	public int getOption() {
		return this.option;
	}
	public boolean getEscape() {
		return this.escape;
	}
	public boolean isLowResMode() {
		if(this.lowRes) {
			return true;
		}else {
			return false;
		}
	}
	public int getCubeSize() {
		return tamanhoCubo;
	}
	public int getMiniCubeSize() {
		return tamanhoCuboPreview;
	}
	public int getPreviewSizeX() {
		return this.previewColNum;
	}
	public int getPreviewSizeY() {
		return this.previewLinNum;
	}
	public int getScore() {
		return this.pontuacao;
	}	
	public int getLines() {
		return this.linhas;
	}
	public int getLevel() {
		return this.nivel;
	}
	public int getSizeX() {
		return this.maxX;
	}
	public int getSizeY() {
		return this.maxY;
	}

	public Player getPlayer() {
		return this.player;
	}

	//setters
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	public void setEscape(boolean escape) {
		this.escape = escape;
	}
	/**********************************************************************************************/
	/******************************METODOS PRIVADOS (AUXILIARES)***********************************/
	/**********************************************************************************************/
	
	//metodo que atualiza o mapa, deve ser chamado apos aterrissagem de peca
	private void updateMap() {
		int ladoMatriz = this.pecas[proximaPeca[0]].getSize();
		//salva a contribuicao da peca atual
		for(int i = 0; i < ladoMatriz; i++) {
			for(int j = 0; j < ladoMatriz; j++) {
				if(this.pecas[proximaPeca[0]].getCube(i, j)) {
					int x = pecas[proximaPeca[0]].x + i;
					int y = pecas[proximaPeca[0]].y + j;
					//so salva o que pode de fato aparecer na GUI
					if(x < this.maxX && y < this.maxY && x >= 0 && y >= 0) {
						map[y][x] = pecas[proximaPeca[0]].toChar();
					}
				}
			}
		}
		
		int tetrisCount = 0; //armazena a quantia de linhas formadas de uma vez
		int alturaLinFormada[] = new int[4]; //armazena onde as linhas foram formadas, para poder apaga-las depois
		//registra quantas linhas foram formadas, e onde (o maximo sao 4, pois a peca mais comprida eh a TetrominoeI)
		for(int y = (this.maxY-1); y > 0; y--) {
			boolean linhaFormada = true; //auxiliar, flag de linha completa
			for(int x = 0; x < this.maxX; x++) {
				//se algum cubo da linha for mapa, a linha nao esta completa
				if(map[y][x] == 'B')
					linhaFormada = false;
			}
			if(linhaFormada) {
				//atualiza o local e quantidade de linhas formadas
				alturaLinFormada[tetrisCount] = y;
				tetrisCount++;
			}
		}
		
		//limpa as linhas formadas
		for(int i = 0; i < tetrisCount; i++) {
			//limpa a linha e desloca o mapa inteiro a partir dela, para baixo
			for(int y = alturaLinFormada[0]; y > 1; y--) {
				for(int x = 0; x < this.maxX; x++) {
					//cada cubo recebe o que esta logo acima do mesmo
					map[y][x] = map[y-1][x] ;
				}
			}
		}
		
		//no caso de 4 linhas ao mesmo tempo, TETRIS!
		if(tetrisCount == 4) {
			System.out.println("----TETRIS!---TETRIS!---TETRIS!---TETRIS!---");
		}
		
		//atualiza status
		this.updateScore(tetrisCount);
	}
	
	//auxiliar para updateMap(), atualiza o mostrador de proximas pecas
	private void updatePreview() {
		for(int i = 0; i<previewLinNum; i++) {
			for(int j = 0; j<previewColNum; j++) { 
				preview[i][j] = 'B';
			}
		}
		for(int k = 0; k < this.numSorteadas; k++) {
			int offsetX = 0, offsetY = 0; //offsets para centralizar melhor as pecas no preview
			switch(pecas[proximaPeca[k]].toChar()) {
				case 'O':
					offsetX = 1;
					offsetY = 1;
					break;
				case 'J':
					offsetY = 0;
					offsetX = 1;
					break;
				default:
					offsetX = 0;
					offsetY = 0;
			}
			//[numSorteadas] pecas ja sorteadas
			int ladoMatriz = this.pecas[proximaPeca[k]].getSize();
			//transfere a matriz da peca para o mapa de preview
			for(int i = 0; i < ladoMatriz; i++) {
				for(int j = 0; j < ladoMatriz; j++) {
					if(this.pecas[proximaPeca[k]].getCube(i, j)) {
						int x = 1 + i + offsetX;
						int y = k*6 + 1 + j + offsetY;
						//so salva o que pode de fato aparecer na GUI
						if(x < this.previewColNum && y < this.previewLinNum && x >= 0 && y >= 0) {
							preview[y][x] = pecas[proximaPeca[k]].toChar();
						}
					}
				}
			}
		}
	}
	
	//auxiliar para updateMap(), atualiza contagem de linhas, nivel e pontuacao
	private void updateScore(int numLinhas) {
		if(numLinhas > 0 && numLinhas < 5) {
			//atualiza contagem de linhas
			this.linhas += numLinhas;
			//atualiza nivel
			this.nivel = (int)(this.linhas/10) + 1;
			//atualiza pontuacao
			switch(numLinhas) {
				case 1:
					this.pontuacao += 100*this.nivel;
					break;
				case 2:
					this.pontuacao += 300*this.nivel;
					break;
				case 3:
					this.pontuacao += 500*this.nivel;
					break;
				case 4:
					this.pontuacao += 800*this.nivel;
					break;
				default:
					break;
			}
		}
		//atualiza o score maximo do player
		this.player.setScore(this.pontuacao);
	}
	
	//metodo que testa possiveis colisoes da peca ativa atual com os elementos do mapa, retorna dados sobre a colisao
	private Collision testaColisao() {
		//auxiliar, para escrever menos
		int ladoMatriz = this.pecas[proximaPeca[0]].getSize();
		//instancia que armazena dados sobre a colisao
		Collision retorno = new Collision();
		for(int i = 0; i < ladoMatriz; i++) {
			for(int j = 0; j < ladoMatriz; j++) {
				//se for um cubo ocupado, na matriz da peca
				if(this.pecas[proximaPeca[0]].getCube(i, j)) {
					//enquanto i e j coordenadas referenciadas na matriz da PECA, x e y sao referenciadas na matriz do MAPA
					int x = pecas[proximaPeca[0]].x + i;
					int y = pecas[proximaPeca[0]].y + j;
					//se o teste eh feito dentro do mapa
					if(x >= 0 && x < maxX && y < maxY && y >= 0) {
						//se coincide com algo no mapa que nao seja o fundo vazio, eh colisao com pecas acumuladas
						if(map[y][x] != 'B') {
							retorno.peca = true;
						}
					}else { //se nao, ainda pode ser colisao com as paredes ou o chao
						if(x < 0){
							//colisao com a parede esquerda
							retorno.paredeEsq = true;
						}else if(x >= maxX){
							//colisao com a parede direita
							retorno.paredeDir = true;
						}else if(y >= maxY){
							//colisao com o chao
							retorno.chao = true;
						}else if(y < 0) {
							//colisao com o teto
							retorno.foraDoMapa = true;
						}
					}
				}
			}
		}
		//retorna os dados sobre a colisao
		return retorno;
	}

	//metodo que retorna a figura correspondente a coordenadas X, Y (referenciadas na matriz do mapa)
	private BufferedImage getMapCubeImg(int posX, int posY, int mapType) {
		char cubo;
		if(mapType == 0) {
			//mapa principal
			cubo = this.map[posY][posX];
		}else {
			//mapa de proximas pecas
			cubo = this.preview[posY][posX];
		}
		BufferedImage retorno = this.debugTile;
		switch(cubo) {
			case 'B': 
				if(mapType == 0) {
					retorno = this.backgroundTile;
				}else {
					retorno = this.previewBgTile;
				}
				break;
			case 'I': 
				retorno = this.pecas[0].getImage();
				break;
			case 'J': 
				retorno = this.pecas[1].getImage();
				break;
			case 'L': 
				retorno = this.pecas[2].getImage();
				break;
			case 'O': 
				retorno = this.pecas[3].getImage();
				break;
			case 'S': 
				retorno = this.pecas[4].getImage();
				break;
			case 'T': 
				retorno = this.pecas[5].getImage();
				break;
			case 'Z': 
				retorno = this.pecas[6].getImage();
				break;
			default:
				break;
		}
		return retorno;
	}

	//Funcao polimorfa para simplificar a chamada da funcao acima
	private BufferedImage getMapCubeImg(int posX, int posY) {
		return getMapCubeImg(posX, posY, 0);
	}
	
	//verifica se um cubo de coordenadas X, Y (referenciadas na matriz do mapa) esta ocupado pelo corpo de uma peca
	private boolean pecaPresente(int posX, int posY) {
		boolean X_dentro = false, Y_dentro = false;
		//verifica se esta dentro da faixa em X
		if(posX >= pecas[proximaPeca[0]].x && posX <= (pecas[proximaPeca[0]].x + pecas[proximaPeca[0]].getSize() - 1)) X_dentro = true;
		//verifica se esta dentro da faixa em Y
		if(posY >= pecas[proximaPeca[0]].y && posY <= (pecas[proximaPeca[0]].y + pecas[proximaPeca[0]].getSize() - 1)) Y_dentro = true;
		//se o ponto ta dentro da matriz da peca ativa, usa getCube() para ver se eh cubo preenchido
		if(X_dentro && Y_dentro) {
			if(pecas[proximaPeca[0]].getCube(posX-pecas[proximaPeca[0]].x, posY-pecas[proximaPeca[0]].y)) {
				return true;
			}else {
				//nao eh cubo preenchido, eh transparente, mapa deve aparecer neste lugar.
				return false;
			}
		}else {
			//fora da matriz da peca, eh mapa
			return false;
		}
	}
	
	//desloca o vetor de proximas pecas em 1 un, sorteia proximaPeca[ultima]
	private void atualizaProxPecas() {
		if(!this.perdeu) {
			//reset da peca atual
			this.pecas[this.proximaPeca[0]].reset();
			//deslocamento
			for(int i = 0; i< this.numSorteadas - 1; i++) {
				this.proximaPeca[i] = this.proximaPeca[i+1];
			}
			//sorteia a ultima
			this.proximaPeca[this.numSorteadas - 1] = getPecaAleatoria(this.numTiposPecas, this.proximaPeca[this.numSorteadas - 2]);
			//debug //System.err.println("NOVA PECA!");
			this.pecas[this.proximaPeca[this.numSorteadas - 1]].reset();
		}else {
			//se perdeu, apenas reseta a peca atual
			this.pecas[this.proximaPeca[0]].reset();
		}
	}
	
	//retorna um valor de 0 a (numPecas - 1)
	private int getPecaAleatoria(int numPecas) {
		Random gerador = new Random();
		return(gerador.nextInt(numPecas));
	}
	
	//retorna um valor de 0 a (numPecas - 1), que nao seja a excecao
	private int getPecaAleatoria(int numPecas, int excecao) {
		int retorno = excecao;
		while(retorno == excecao) {
			retorno = getPecaAleatoria(numPecas);
		}
		return retorno;
	}

	//debug
	public void printdebug() {
		System.out.println("X: "+this.pecas[this.proximaPeca[0]].x+" Y: "+this.pecas[this.proximaPeca[0]].y);
	}
}
