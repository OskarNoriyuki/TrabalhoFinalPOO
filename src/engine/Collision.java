/*
	Classe Collision
	Descricao: armazena quais tipos de colisao aconteceram
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package engine;

public class Collision {
	//atributos publicos, pois o objetivo da classe eh justamente ser uma estrutura de dados
	public boolean paredeDir;
	public boolean paredeEsq;
	public boolean chao;
	public boolean peca;
	public boolean foraDoMapa;

	// Inicializa as variaveis no construtor
	public Collision() {
		this.paredeDir = false;
		this.paredeEsq = false;
		this.chao = false;
		this.peca = false;
		this.foraDoMapa = false;
	}
	
	// Metodo basico, retorna verdadeiro se uma das flags (exceto colisao no teto) esta acesa
	public boolean nok() {
		if (this.paredeDir || this.paredeEsq || this.chao || this.peca) {
			return true;
		} else {
			return false;
		}
	}
}
