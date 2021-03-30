package br.com.project.geral.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.Setor;
import br.com.repository.interfaces.RepositorySetor;
import br.com.srv.interfaces.SrvSetor;

@Controller
public class SetorController extends ImplemantacaoCRUD<Setor>
implements InterfaceCrud<Setor>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private SrvSetor srvSetor;
	
	@Resource
	private RepositorySetor repositorySetor;

}
