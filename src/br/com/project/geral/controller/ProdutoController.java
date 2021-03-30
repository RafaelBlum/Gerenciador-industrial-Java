package br.com.project.geral.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.model.SelectItem;

import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.Produto;
import br.com.repository.interfaces.RepositoryProduto;
import br.com.srv.interfaces.SrvProduto;

@Controller
public class ProdutoController extends ImplemantacaoCRUD<Produto> implements InterfaceCrud<Produto>{

	private static final long serialVersionUID = 1L;

	@Resource
	private SrvProduto srvProduto;
	
	@Resource
	private RepositoryProduto repositoryProduto;
	
	public List<SelectItem> getListProdutoController() throws Exception {
		List<SelectItem> list = new ArrayList<SelectItem>();

		List<Produto> produtos = super.findByQueryDinamica(" from Produto");

		for (Produto produto : produtos) {
			list.add(new SelectItem(produto, "" + produto.getMaterial()));
		}
		return list;
	}

}
