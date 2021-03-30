package br.com.srv.implementacao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.repository.interfaces.RepositoryContaRazao;
import br.com.srv.interfaces.SrvContaRazao;

@Service
public class SrvContaRazaoImpl implements SrvContaRazao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private RepositoryContaRazao repositoryContaRazao;

}
