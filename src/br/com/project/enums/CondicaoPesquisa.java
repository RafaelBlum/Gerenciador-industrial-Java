package br.com.project.enums;

public enum CondicaoPesquisa {
	
	CONTEM("Cont�m"), 
	INICIA_COM("Inicia com"),
	TERMINA_COM("T�rmina com"), 
	IGUAL_A("Igual");
	
	private String condicao;
	
	private CondicaoPesquisa(String condicao){
		this.condicao = condicao;
	}

	public String getCondicao() {
		return condicao;
	}

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}
	
	@Override
	public String toString(){
		return this.condicao;
	}
	
}
