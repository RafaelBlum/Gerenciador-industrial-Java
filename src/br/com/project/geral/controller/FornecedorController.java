package br.com.project.geral.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.model.SelectItem;

import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.Fornecedor;
import br.com.repository.interfaces.RepositoryFornecedor;
import br.com.srv.interfaces.SrvFornecedor;

@Controller
public class FornecedorController extends ImplemantacaoCRUD<Fornecedor> implements InterfaceCrud<Fornecedor> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private SrvFornecedor srvFornecedor;
	
	@Resource
	private RepositoryFornecedor repositoryFornecedor;
	
	public List<SelectItem> getListFornecedorController() throws Exception {
		List<SelectItem> list = new ArrayList<SelectItem>();

		List<Fornecedor> fornecedores = super.findByQueryDinamica(" from Fornecedor");

		for (Fornecedor fornecedor : fornecedores) {
			list.add(new SelectItem(fornecedor, fornecedor.getNome()));
		}
		return list;
	}

}
