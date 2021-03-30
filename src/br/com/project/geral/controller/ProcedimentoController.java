package br.com.project.geral.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.Procedimento;
import br.com.repository.interfaces.RepositoryProcedimento;
import br.com.srv.interfaces.SrvProcedimento;

@Controller
public class ProcedimentoController  extends ImplemantacaoCRUD<Procedimento> 
implements InterfaceCrud<Procedimento>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private SrvProcedimento srvProcedimento;
	
	@Resource
	private RepositoryProcedimento repositoryProcedimento;
	
}
