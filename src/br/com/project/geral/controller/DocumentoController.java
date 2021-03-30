package br.com.project.geral.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.Documento;
import br.com.repository.interfaces.RepositoryDocumento;
import br.com.srv.interfaces.SrvDocumento;

@Controller
public class DocumentoController extends ImplemantacaoCRUD<Documento> implements InterfaceCrud<Documento>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private SrvDocumento srvDocumento;
	
	@Resource
	private RepositoryDocumento repositoryDocumento;
	
	
}
