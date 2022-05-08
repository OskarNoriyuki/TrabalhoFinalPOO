/*
Programa: Classe reponsavel pela logica do jogo Tetris.
Objetivos: Exercicio da programacao orientada a objeto.
Entradas: Tamanho do mapa, Acao/Movimento de cada iteracao.
Saida: Cubos que compoem o mapa do tetris.
Autor: Oskar Akama
Atualizada em 06/05/2022
*/
package engine;

import java.util.Random;
import pecas.Tetromino;
import pecas.TetrominoI;
import pecas.TetrominoJ;
import pecas.TetrominoL;
import pecas.TetrominoO;
import pecas.TetrominoS;
import pecas.TetrominoT;
import pecas.TetrominoZ;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import graphics.GameWindow;

public class Tetris {
	/**atributos gerais**/
	private boolean perdeu;
	private int pontuacao, linhas, nivel;
	
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
	private final static int tamanhoCubo = tamanhoCuboOriginal*upScale; //40x40 pixels
	private final static int previewDownScale = 1; 
	private final static int tamanhoCuboPreview = tamanhoCuboOriginal/previewDownScale; //20x20 pixels
	private BufferedImage backgroundTile;
	private BufferedImage debugTile;
	private BufferedImage previewBgTile;
	
	/**atributos de pecas**/
	private final int numTiposPecas = 7, numSorteadas = 4;
	private Tetromino pecas[];
	private int proximaPeca[]; 
	/*
	 * representadas por 'I', 'J', 'L', 'O', 'S', 'T', 'Z',
	 * proximaPeca[0] eh a peca ATUAL, e as seguintes sao as proximas a cair.
	 */

	/**********************************************************************************************/
	/*************************************METODOS PUBLICOS*****************************************/
	/**********************************************************************************************/
	
	//construtor, recebe um tamanho de mapa, em cubos
	public Tetris(int lin, int col) {
		//geral
		this.perdeu = false;
		this.pontuacao = 0;
		this.linhas = 0;
		this.nivel = 1;
		//tamanho do mapa
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
		pecas = new Tetromino[numTiposPecas];
		pecas[0] = new TetrominoI();
		pecas[1] = new TetrominoJ();
		pecas[2] = new TetrominoL();
		pecas[3] = new TetrominoO();
		pecas[4] = new TetrominoS();
		pecas[5] = new TetrominoT();
		pecas[6] = new TetrominoZ();
		//eh possivel ver, fora a peca atual, as proximas 3 a cair. inicializa aleatoriamente
		this.proximaPeca = new int[numSorteadas];
		this.proximaPeca[0] = this.getPecaAleatoria(numTiposPecas); //primeira a cair
		//nao caem 2 pecas iguais consecutivas
		for(int i = 1; i < numSorteadas; i++) {
			this.proximaPeca[i] = this.getPecaAleatoria(numTiposPecas, proximaPeca[i-1]);
		}	
		//imagem do cubo de fundo
		try {
			this.backgroundTile = ImageIO.read(new FileInputStream("src/img/map/cube_map_dark.png"));
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
			this.previewBgTile = ImageIO.read(new FileInputStream("src/img/map/cube_map_white.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		//atualiza o mapa de proximas pecas
		this.updatePreview();
	}
	
	//realiza um movimento/acao, calcula as consequencias e atualiza o jogo. deve ser chamado 1x por frame, e 1x por comando
	public boolean updateGame(String acao) {
		Colisao colisao;	//classe que armazena infos da colisao
		if(perdeu) {
			//chama window de game over
			//GameWindow.fecharTetris();
			System.err.println("PERDEEUUU!");
			return true;
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
				//print debug
				if(colisao.peca) System.err.println("BATEU NOUTRA PECA! DEPTH_R="+colisao.profundidadeDir+" DEPTH_L="+colisao.profundidadeEsq+" DEPTH_INF="+colisao.profundidadeInf);
				if(colisao.chao) System.err.println("BATEU NO CHAO! DEPTH="+colisao.profundidadeInf);
				if(colisao.paredeDir) System.err.println("BATEU NA PAREDE DIREITA! DEPTH_R="+colisao.profundidadeDir);
				if(colisao.paredeEsq) System.err.println("BATEU NA PAREDE ESQUERDA! DEPTH_L="+colisao.profundidadeEsq);

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
		}else if(acao.equals("goDown") || acao.equals("goDownExtra")) {
			//desce uma casa. essa acao pode ocasionar em atualizacoes no mapa (linha formada, peca terminou de cair) ou ate mesmo o fim de jogo
			this.pecas[this.proximaPeca[0]].y++;
			//testa colisao, as duas colisoes relevantes sao: colisao com o chao; colisao com pecas
			colisao = this.testaColisao();
			if(colisao.chao || colisao.peca) {
				//print debug
				if(colisao.chao) System.err.println("BATEU NO CHAO! DEPTH="+colisao.profundidadeInf);
				if(colisao.peca) System.err.println("BATEU NOUTRA PECA! DEPTH_R="+colisao.profundidadeDir+" DEPTH_L="+colisao.profundidadeEsq+" DEPTH_INF="+colisao.profundidadeInf);
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
					//se a peca bateu e uma parte dela ficou extrapolando o mapa, fim de jogo
					this.perdeu = true;
				}
			}else {
				//se nao aterrissou, adiciona um ponto para cada cada adiantada
				if(acao.equals("goDownExtra"))
					this.pontuacao++;
			}
		}else if(acao.equals("goRight")) {
			//uma casa para a direita
			this.pecas[this.proximaPeca[0]].x++;
			//testa colisao, as duas colisoes relevantes sao: colisao com parede direita; colisao com pecas
			colisao = this.testaColisao();
			if(colisao.paredeDir || colisao.peca) {
				//print debug
				if(colisao.paredeDir) System.err.println("BATEU NA PAREDE DIREITA! DEPTH_R="+colisao.profundidadeDir);
				if(colisao.peca) System.err.println("BATEU NOUTRA PECA! DEPTH_R="+colisao.profundidadeDir+" DEPTH_L="+colisao.profundidadeEsq+" DEPTH_INF="+colisao.profundidadeInf);
				//desfaz o movimento
				this.pecas[this.proximaPeca[0]].x--;
			}

		}else if(acao.equals("goLeft")) {
			//uma casa para a esquerda
			this.pecas[this.proximaPeca[0]].x--;
			//testa colisao, as duas colisoes relevantes sao: colisao com parede esquerda; colisao com pecas
			colisao = this.testaColisao();
			if(colisao.paredeEsq || colisao.peca) {
				//print debug
				if(colisao.paredeEsq) System.err.println("BATEU NA PAREDE ESQUERDA! DEPTH_L="+colisao.profundidadeEsq);
				if(colisao.peca) System.err.println("BATEU NOUTRA PECA! DEPTH_R="+colisao.profundidadeDir+" DEPTH_L="+colisao.profundidadeEsq+" DEPTH_INF="+colisao.profundidadeInf);
				//desfaz o movimento
				this.pecas[this.proximaPeca[0]].x++;
			}
		}else if(acao.equals("goUp")) {
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
	
	//getters
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
			//[numSorteadas] pecas ja sorteadas
			int ladoMatriz = this.pecas[proximaPeca[k]].getSize();
			//transfere a matriz da peca para o mapa de preview
			for(int i = 0; i < ladoMatriz; i++) {
				for(int j = 0; j < ladoMatriz; j++) {
					if(this.pecas[proximaPeca[k]].getCube(i, j)) {
						int x = 1 + i;
						int y = k*6 + 1 + j;
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
	}
	
	//metodo que testa possiveis colisoes da peca ativa atual com os elementos do mapa, retorna dados sobre a colisao
	private Colisao testaColisao() {
		//auxiliar, para escrever menos
		int ladoMatriz = this.pecas[proximaPeca[0]].getSize();
		//instancia que armazena dados sobre a colisao
		Colisao retorno = new Colisao();
		//[FUNCIONALIDADE NAO UTILIZADA] "gaps" na matriz sao calculados para posterior calculo de profundidade de interseccao de colisao
		int numColVaziaEsq = ladoMatriz, numColVaziaDir = ladoMatriz, numLinVaziaInf = ladoMatriz;
		for(int i = 0; i < ladoMatriz; i++) {
			for(int j = 0; j < ladoMatriz; j++) {
				//se for um cubo ocupado, na matriz da peca
				if(this.pecas[proximaPeca[0]].getCube(i, j)) {
					//[FUNCIONALIDADE NAO UTILIZADA] calcula as bordas vazias de cada lado, exceto superior
					if(numColVaziaEsq > i)
						numColVaziaEsq = i;
					if(numColVaziaDir > (ladoMatriz - i - 1))
						numColVaziaDir = (ladoMatriz - i - 1);
					if(numLinVaziaInf > (ladoMatriz - j - 1))
						numLinVaziaInf = (ladoMatriz - j - 1);	
					//enquanto i e j coordenadas referenciadas na matriz da PECA, x e y sao referenciadas na matriz do MAPA
					int x = pecas[proximaPeca[0]].x + i;
					int y = pecas[proximaPeca[0]].y + j;
					//se o teste eh feito dentro do mapa
					if(x >= 0 && x < maxX && y < maxY && y >= 0) {
						//se coincide com algo no mapa que nao seja o fundo vazio, eh colisao com pecas acumuladas
						if(map[y][x] != 'B') {
							retorno.peca = true;
							//[FUNCIONALIDADE NAO UTILIZADA] calcula a profundidade de colisao referenciada nos extremos da matriz
							if(retorno.profundidadeDir < (ladoMatriz - i))
								retorno.profundidadeDir = (ladoMatriz - i);
							if(retorno.profundidadeEsq < (i+1))
								retorno.profundidadeEsq = (i+1);
							if(retorno.profundidadeInf < (ladoMatriz - j))
								retorno.profundidadeInf = (ladoMatriz - j);
						}
					}else { //se nao, ainda pode ser colisao com as paredes ou o chao
						if(x < 0){
							//colisao com a parede esquerda
							retorno.paredeEsq = true;
							//[FUNCIONALIDADE NAO UTILIZADA] calcula a profundidade de colisao referenciada nos extremos da matriz
							if(retorno.profundidadeEsq < (i+1))
								retorno.profundidadeEsq = (i+1);
						}else if(x >= maxX){
							//colisao com a parede direita
							retorno.paredeDir = true;
							//[FUNCIONALIDADE NAO UTILIZADA] calcula a profundidade de colisao referenciada nos extremos da matriz
							if(retorno.profundidadeDir < (ladoMatriz - i))
								retorno.profundidadeDir = (ladoMatriz - i);
						}else if(y >= maxY){
							//colisao com o chao
							retorno.chao = true;
							//[FUNCIONALIDADE NAO UTILIZADA] calcula a profundidade de colisao referenciada nos extremos da matriz
							if(retorno.profundidadeInf < (ladoMatriz - j))
								retorno.profundidadeInf = (ladoMatriz - j);
						}else if(y < 0) {
							//colisao com o teto
							retorno.foraDoMapa = true;
						}
					}
				}
			}
		}
		//[FUNCIONALIDADE NAO UTILIZADA] calcula a profundidade de colisao referenciada nos extremos da regiao solida da peca
		retorno.profundidadeDir -= numColVaziaDir;
		retorno.profundidadeEsq -= numColVaziaEsq;
		retorno.profundidadeInf -= numLinVaziaInf;	
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
			System.err.println("NOVA PECA!");
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
