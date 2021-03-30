package br.com.srv.implementacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.repository.interfaces.RepositoryLogin;
import br.com.srv.interfaces.SrvLogin;

@Service
public class SrvLoginImpl implements SrvLogin{

	/**
	 * Precisamos acessar o banco, ter todo acesso com a DaoLogin, mas não precisa criar na mão, vamos fazer injeção de dependencia do spring;
	 */
	private static final long serialVersionUID = 1L;
	
	//@Autowired ou @Resource para injeção de dependencia do spring
	@Autowired
	private RepositoryLogin repositoryLogin;

	@Override
	public boolean autentico(String login, String senha) throws Exception {
		return repositoryLogin.autentico(login, senha);
	}

}
