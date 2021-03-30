package br.com.framework.utils;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class UtilFramework implements Serializable{

	/**
	 * CLASSE PARA AUXILIAR QUANDO COMEÇARMOS A LIGAR AS CLASSES::
	 * ThreadLocal = RESPONSAVEL POR CARREGAR OS USUÁRIOS COM ID;
	 * NÃO TER CONCORRENCIA A ThreadLocal PRECISA DE UMA synchronized;
	 * O METODO QUANDO É synchronized static DOIS LUGARES NO SISTEMA NÃO CONSEGUEM ACESSAR AO MESMO TEMPO, 
	 * EX. EXECUTA UM INSERT E OUTRO UPDATE (UM PRECISA AGUARDAR O OUTRO).
	 * COM ESTA CLASSE CONSEGUIMOS VER O QUE OS USUÁRIOS ESTÃO FAZENDO.
	 */
	
	private static final long serialVersionUID = 1L;
	
	private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();
	
	public synchronized static ThreadLocal<Long> getThreadLocal(){
		return threadLocal;
	}

}
