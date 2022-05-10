/*
	Classe Collision
	Descricao:
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package engine;

public class Collision {
	//armazena quais tipos de colisao aconteceram.
	//atributos publicos, pois o objetivo da classe eh justamente ser uma estrutura de dados
	public boolean paredeDir;
	public boolean paredeEsq;
	public boolean chao;
	public boolean peca;
	public boolean foraDoMapa;

	//inicializa as variaveis no construtor
	public Collision() {
		this.paredeDir = false;
		this.paredeEsq = false;
		this.chao = false;
		this.peca = false;
		this.foraDoMapa = false;
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
