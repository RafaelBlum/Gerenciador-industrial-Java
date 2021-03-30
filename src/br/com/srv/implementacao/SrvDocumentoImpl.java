package br.com.srv.implementacao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.repository.interfaces.RepositoryDocumento;
import br.com.srv.interfaces.SrvDocumento;

@Service
public class SrvDocumentoImpl implements SrvDocumento{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private RepositoryDocumento repositoryDocumento;
	

}
