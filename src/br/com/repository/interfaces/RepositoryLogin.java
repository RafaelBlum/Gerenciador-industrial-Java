package br.com.repository.interfaces;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryLogin extends Serializable{
	/*Login esta associado a sessão do usuário, desta forma podemos controlar a sessão do usuário e partes do sistema
	 * UMA INTERFACE DE CONEXÃO COM BANCO DE DADOS
	 * */
	//VALIDAR SE USUARIO EXISTE
	boolean autentico(String login, String senha) throws Exception;
}
