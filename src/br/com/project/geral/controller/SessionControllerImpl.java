package br.com.project.geral.controller;

import java.util.HashMap;

import javax.faces.bean.ApplicationScoped;
import javax.servlet.http.HttpSession;

@ApplicationScoped
public class SessionControllerImpl implements SessionController{

	private static final long serialVersionUID = 1L;

	/**Criação de um identificador
	 * 
	 */
	private HashMap<String, HttpSession> hashMap = new HashMap<String, HttpSession>();

	@Override
	public void addSession(String keyLoginUser, HttpSession httpSession) {
		//Tendo a instancia do controller, quando addSession for chamado, pegamos o hashMap e fizemos um PUT 
		hashMap.put(keyLoginUser, httpSession);
	}

	@Override
	public void invalidateSession(String keyLoginUser) {
		// Invalidando a sessão
		HttpSession session = hashMap.get(keyLoginUser);
		
		//REMOVE SESSÃO DO USUARIO PASSADO POR PARAMETROS
		if(session != null){ 
			try{
				session.invalidate();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("Não tem usuário!");
		}
		
		hashMap.remove(keyLoginUser);
		
	}

}
