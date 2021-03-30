package br.com.project.geral.controller;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.servlet.http.HttpSession;

@ApplicationScoped
public interface SessionController extends Serializable{
	/* Detalhe: o escopo é de sessão ou requisição e depois morrem, mas este controle registra todos usuários logados;
	 * Para isso: precisamos de um escopo UNICO (uma instancia só) usando o @ApplicationScoped;
	 * Metodos: Agora só declarar os seus metodos da interface;
	 * */
	
	//Passa código do usuário logado e a sessão
	void addSession(String keyLoginUser, HttpSession httpSession);
	
	//Receber codigo do usuario para validar a sessão dele
	void invalidateSession(String keyLoginUser);

}
