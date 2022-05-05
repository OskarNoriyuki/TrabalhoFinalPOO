package engine;

public class Colisao {
	public boolean paredeDir;
	public boolean paredeEsq;
	public boolean chao;
	public boolean peca;
	public boolean foraDoMapa;
	
	public int profundidadeEsq;
	public int profundidadeDir;
	public int profundidadeInf;
	
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
	
	public boolean nok() {
		if(this.paredeDir || this.paredeEsq || this.chao || this.peca) {
			return true;
		}else {
			return false;
		}
	}
}
