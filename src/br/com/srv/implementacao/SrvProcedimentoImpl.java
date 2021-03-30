package br.com.srv.implementacao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.repository.interfaces.RepositoryProcedimento;
import br.com.srv.interfaces.SrvProcedimento;

@Service
public class SrvProcedimentoImpl implements SrvProcedimento{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private RepositoryProcedimento repositoryProcedimento;

}
