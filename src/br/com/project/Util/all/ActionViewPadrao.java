package br.com.project.Util.all;

import java.io.Serializable;

import javax.annotation.PostConstruct;

public interface ActionViewPadrao extends Serializable{
	//AULA 103
	abstract void limpaLista() throws Exception;
	
	abstract String save() throws Exception;
	
	abstract void saveNotReturn() throws Exception;
	
	abstract void saveEdit() throws Exception;
	
	abstract void excluir() throws Exception;
	
	abstract String ativar() throws Exception;
	
	/*
	 *@PostConstruct:: REALIZA INICIALIZAÇÃO DE METODOS, VALORES OU VARIAVEIS
	 */
	@PostConstruct
	abstract String novo() throws Exception;
	
	abstract String editar() throws Exception;
	
	abstract void setarVariaveisNulas() throws Exception;
	
	abstract void consultarEntidades() throws Exception;
	
	abstract void statusOperation(EstatusPersistencia a) throws Exception;
	
	abstract String redirecionarNewEntidade() throws Exception;
	
	abstract String redirecionarFindEntidade() throws Exception;
	
	abstract void addMsg(String msg);
	
}
