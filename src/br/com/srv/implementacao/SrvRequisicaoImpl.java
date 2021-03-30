package br.com.srv.implementacao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.repository.interfaces.RepositoryRequisicao;
import br.com.srv.interfaces.SrvRequisicao;

@Service
public class SrvRequisicaoImpl implements SrvRequisicao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private RepositoryRequisicao repositoryRequisicao;

}
