package br.com.srv.implementacao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.repository.interfaces.RepositorySetor;
import br.com.srv.interfaces.SrvSetor;

@Service
public class SrvSetorImpl implements SrvSetor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private RepositorySetor sepositorySetor;

}
