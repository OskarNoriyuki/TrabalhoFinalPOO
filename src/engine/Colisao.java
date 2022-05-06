package engine;

public class Colisao {
	//armazena quais tipos de colisao aconteceram.
	//atributos publicos, pois o objetivo da classe eh justamente ser uma estrutura de dados
	public boolean paredeDir;
	public boolean paredeEsq;
	public boolean chao;
	public boolean peca;
	public boolean foraDoMapa;
	
	//armazena a profundidade de colisao referenciada nos extremos da regiao solida da peca
	public int profundidadeEsq;
	public int profundidadeDir;
	public int profundidadeInf;
	
	//inicializa as variaveis no construtor
	public Colisao() {
		this.paredeDir = false;
		this.paredeEsq = false;
		this.chao = false;
		this.peca = false;
		this.foraDoMapa = false;
		this.profundidadeDir = 0;
		this.profundidadeEsq = 0;
		this.profundidadeInf = 0;
	}
	
	//metodo basico, retorna verdadeiro se uma das flags (exceto colisao no teto) esta acesa
	public boolean nok() {
		if(this.paredeDir || this.paredeEsq || this.chao || this.peca) {
			return true;
		}else {
			return false;
		}
	}
}
