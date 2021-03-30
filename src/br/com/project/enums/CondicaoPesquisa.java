package br.com.project.enums;

public enum CondicaoPesquisa {
	
	CONTEM("Contém"), 
	INICIA_COM("Inicia com"),
	TERMINA_COM("Términa com"), 
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
