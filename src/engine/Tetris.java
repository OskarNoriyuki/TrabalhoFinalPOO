//classe controladora

package engine;

import java.util.ArrayList;
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
	/*atributos gerais*/
	private boolean perdeu;
	/*atributos do mapa*/
	private int maxX, maxY;
	private char map[][];
	/*
	 * mapa eh armazenado em uma matriz de char
	 * elementos 'B' simbolizam "background" ou fundo
	 * elementos 'I', 'J', 'L', 'O', 'S', 'T', 'Z' representam cubos das pecas que ja cairam
	 * elemento 'U' = undefined
	 */
	/*atributos esteticos*/
	private BufferedImage backgroundTile;
	private BufferedImage debugTile;
	/*atributos de pecas*/
	private final int numTiposPecas = 7, numSorteadas = 4;
	private Tetromino pecas[];
	private int proximaPeca[]; 
	/*
	 * representadas por 'I', 'J', 'L', 'O', 'S', 'T', 'Z',
	 * a peca ativa eh o indice [0], e as seguintes sao as
	 * proximas a cair
	 */
	
	//construtor
	public Tetris(int lin, int col) {
		//geral
		this.perdeu = false;
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
	}

	

	
	//faz a peca atual cair mais uma linha. se colidiu, atualiza o mapa e demais detalhes
	public boolean updateGame(String acao) {
		//classe que armazena infos da colisao
		Colisao colisao;
		if(perdeu) {
			//chama window de game over
			//GameWindow.fecharTetris();
			System.err.println("PERDEEUUU!");
			return true;
		}else if(acao.equals("rotateCW") || acao.equals("rotateCCW")) {
			if(acao.equals("rotateCW")){
				this.pecas[this.proximaPeca[0]].rotacionar("CW");
			}else {
				this.pecas[this.proximaPeca[0]].rotacionar("CCW");
			}
			colisao = this.testaColisao();
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
					//se bateu com peca, nem tenta
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
		}else if(acao.equals("goDown")) {
			this.pecas[this.proximaPeca[0]].y++;
			colisao = this.testaColisao();
			if(colisao.chao || colisao.peca) {
				//print debug
				if(colisao.chao) System.err.println("BATEU NO CHAO! DEPTH="+colisao.profundidadeInf);
				if(colisao.peca) System.err.println("BATEU NOUTRA PECA! DEPTH_R="+colisao.profundidadeDir+" DEPTH_L="+colisao.profundidadeEsq+" DEPTH_INF="+colisao.profundidadeInf);
				//desfaz o movimento
				this.pecas[this.proximaPeca[0]].y--;
				//salva os cubos no mapa
				this.updateMap();
				//se der para continuar...
				if(!colisao.foraDoMapa) {
					//reseta a peca, ativa a proxima e sorteia mais uma
					atualizaProxPecas();
				}else {
					//se a peca bateu para baixo e continua fora do mapa, fim de jogo
					this.perdeu = true;
				}
			}
		}else if(acao.equals("goRight")) {
			this.pecas[this.proximaPeca[0]].x++;
			colisao = this.testaColisao();
			if(colisao.paredeDir || colisao.peca) {
				//print debug
				if(colisao.paredeDir) System.err.println("BATEU NA PAREDE DIREITA! DEPTH_R="+colisao.profundidadeDir);
				if(colisao.peca) System.err.println("BATEU NOUTRA PECA! DEPTH_R="+colisao.profundidadeDir+" DEPTH_L="+colisao.profundidadeEsq+" DEPTH_INF="+colisao.profundidadeInf);
				//desfaz o movimento
				this.pecas[this.proximaPeca[0]].x--;
			}

		}else if(acao.equals("goLeft")) {
			this.pecas[this.proximaPeca[0]].x--;
			colisao = this.testaColisao();
			if(colisao.paredeEsq || colisao.peca) {
				//print debug
				if(colisao.paredeEsq) System.err.println("BATEU NA PAREDE ESQUERDA! DEPTH_L="+colisao.profundidadeEsq);
				if(colisao.peca) System.err.println("BATEU NOUTRA PECA! DEPTH_R="+colisao.profundidadeDir+" DEPTH_L="+colisao.profundidadeEsq+" DEPTH_INF="+colisao.profundidadeInf);
				//desfaz o movimento
				this.pecas[this.proximaPeca[0]].x++;
			}
		}else if(acao.equals("goUp")) {
			this.pecas[this.proximaPeca[0]].y--;
			if(this.pecas[this.proximaPeca[0]].y < 0) {
				this.pecas[this.proximaPeca[0]].y = 0;
			}
		}
		return false;
	}
	
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
		
		boolean linhaFormada;
		int tetrisCount = 0;
		int alturaLinFormada[] = new int[4];
		//registra quantas linhas foram formadas, e onde (o maximo sao 4, pois a peca mais comprida eh a TetrominoeI)
		for(int y = (this.maxY-1); y > 0; y--) {
			linhaFormada = true;
			//se algum cubo da linha for mapa, a linha nao esta completa
			for(int x = 0; x < this.maxX; x++) {
				if(map[y][x] == 'B')
					linhaFormada = false;
			}
			if(linhaFormada) {
				alturaLinFormada[tetrisCount] = y;
				tetrisCount++;
			}
		}
		
		//limpa as linhas formadas
		System.err.println("----TETRIS_COUNT--- "+ tetrisCount);
		for(int i = 0; i < tetrisCount; i++) {
			//limpa a linha e desloca o mapa inteiro a partir dela, para baixo
			for(int y = alturaLinFormada[0]; y > 1; y--) {
				for(int x = 0; x < this.maxX; x++) {
					map[y][x] = map[y-1][x] ;
				}
			}
		}
		
		//no caso de 4 linhas ao mesmo tempo, TETRIS!
		if(tetrisCount == 4) {
			System.err.println("----TETRIS!---TETRIS!---TETRIS!---TETRIS!---");
		}
	}
	
	private Colisao testaColisao() {
		int ladoMatriz = this.pecas[proximaPeca[0]].getSize();
		Colisao retorno = new Colisao();
		/*
		 * "peca" para colisao com cubos acumulados
		 * "chao" para colisao com o chao
		 * "paredeDir" para colisao com a parede direita
		 * "paredeEsq" para colisao com a parede esquerda
		 * "foraDoMapa" para peca ainda fora do mapa
		 */
		int numColVaziaEsq = ladoMatriz, numColVaziaDir = ladoMatriz, numLinVaziaInf = ladoMatriz;
		for(int i = 0; i < ladoMatriz; i++) {
			for(int j = 0; j < ladoMatriz; j++) {
				//se for um cubo ocupado
				if(this.pecas[proximaPeca[0]].getCube(i, j)) {
					//detecta a profundidade das bordas vazias de cada lado, exceto o superior
					if(numColVaziaEsq > i)
						numColVaziaEsq = i;
					if(numColVaziaDir > (ladoMatriz - i - 1))
						numColVaziaDir = (ladoMatriz - i - 1);
					if(numLinVaziaInf > (ladoMatriz - j - 1))
						numLinVaziaInf = (ladoMatriz - j - 1);	
					
					int x = pecas[proximaPeca[0]].x + i;
					int y = pecas[proximaPeca[0]].y + j;
					//se o teste eh feito dentro do mapa
					if(x >= 0 && x < maxX && y < maxY && y >= 0) {
						//se coincide com algo no mapa que nao seja o fundo vazio, eh colisao com pecas acumuladas
						if(map[y][x] != 'B') {
							retorno.peca = true;
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
							//
							if(retorno.profundidadeEsq < (i+1))
								retorno.profundidadeEsq = (i+1);
						}else if(x >= maxX){
							//colisao com a parede direita
							retorno.paredeDir = true;
							if(retorno.profundidadeDir < (ladoMatriz - i))
								retorno.profundidadeDir = (ladoMatriz - i);
						}else if(y >= maxY){
							//colisao com o chao
							retorno.chao = true;
							if(retorno.profundidadeInf < (ladoMatriz - j))
								retorno.profundidadeInf = (ladoMatriz - j);
						}else if(y < 0) {
							retorno.foraDoMapa = true;
						}
						//System.err.println("verificacao de colisao fora do mapa!");
					}
				}
			}
		}
		//mantem as profundidades de colisao em relacao a area ocupada da matriz da peca
		retorno.profundidadeDir -= numColVaziaDir;
		retorno.profundidadeEsq -= numColVaziaEsq;
		retorno.profundidadeInf -= numLinVaziaInf;	
		//System.err.println("gapL="+numColVaziaEsq+" gapR="+numColVaziaDir+" gapInf="+numLinVaziaInf);
		return retorno;
	}

/***************************************** CUBOS *****************************************/
	public BufferedImage getCubeImg(int posX, int posY) {
		//se faz parte da pe�a
		if(pecaPresente(posX, posY)) {
			return pecas[proximaPeca[0]].getImage();
		}else {
			/*
			//debug
			if(posX == 0 && posY == 0) {
				return this.debugTile;
			}else {
				return this.getMapCubeImg(posX, posY);
			}*/
			return this.getMapCubeImg(posX, posY);
		}
	}
	
	private BufferedImage getMapCubeImg(int posX, int posY) {
		char cubo = map[posY][posX];
		BufferedImage retorno = this.debugTile;
		switch(cubo) {
			case 'B': 
				retorno = this.backgroundTile;
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
	
	//verifica se o cubo destas coordenadas no mapa esta ocupado pelo corpo de uma peca
	private boolean pecaPresente(int posX, int posY) {
		//int pecaAtiva = 0;
		int pecaAtiva = this.proximaPeca[0];
		boolean X_dentro = false, Y_dentro = false;
		if(posX >= pecas[proximaPeca[0]].x && posX <= (pecas[proximaPeca[0]].x + pecas[proximaPeca[0]].getSize() - 1)) X_dentro = true;
		if(posY >= pecas[proximaPeca[0]].y && posY <= (pecas[proximaPeca[0]].y + pecas[proximaPeca[0]].getSize() - 1)) Y_dentro = true;
		//se o ponto pertence a area da matriz da peca, usa getCube() para ver se eh cubo preenchido
		if(X_dentro && Y_dentro) {
			if(pecas[proximaPeca[0]].getCube(posX-pecas[proximaPeca[0]].x, posY-pecas[proximaPeca[0]].y)) {
				return true;
			}else {
				//nao eh cubo ocupado
				return false;
			}
		}else {
			//eh mapa
			return false;
		}
	}
	
	//desloca o vetor de proximas pecas para baixo em 1 un, sorteia proximaPeca[ultima]
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
	
	//retorna um caractere correspondente a uma peca
	private int getPecaAleatoria(int numPecas) {
		Random gerador = new Random();
		return(gerador.nextInt(numPecas));
		/*
		int intRandom = gerador.nextInt(numPecas);
		char retorno;
		switch(intRandom) {
			case 0: 
				retorno = 'I';
				break;
			case 1: 
				retorno = 'J';
				break;
			case 2: 
				retorno = 'L';
				break;
			case 3: 
				retorno = 'O';
				break;
			case 4: 
				retorno = 'S';
				break;
			case 5: 
				retorno = 'T';
				break;
			case 6: 
				retorno = 'Z';
				break;
			default:
				retorno = 'U';
				break;
		}
		return retorno;
		*/
	}
	
	//retorna uma peca aleatoria que nao seja a excecao
	private int getPecaAleatoria(int numPecas, int excecao) {
		int retorno = excecao;
		while(retorno == excecao) {
			retorno = getPecaAleatoria(numPecas);
		}
		return retorno;
		/*
		char retorno = excecao;
		while(retorno == excecao) {
			retorno = getPecaAleatoria(numPecas);
		}
		return retorno;
		*/
	}


	public void printdebug() {
		System.out.println("X: "+this.pecas[this.proximaPeca[0]].x+" Y: "+this.pecas[this.proximaPeca[0]].y);
	}

}
