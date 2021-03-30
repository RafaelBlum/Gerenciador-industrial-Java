package br.com.project.geral.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.Item;
import br.com.repository.interfaces.RepositoryItem;
import br.com.srv.interfaces.SrvItem;

@Controller
public class ItemController extends ImplemantacaoCRUD<Item> implements InterfaceCrud<Item> {

	private static final long serialVersionUID = 1L;

	@Resource
	private SrvItem srvItem;
	
	@Resource
	private RepositoryItem repositoryItem;

}
