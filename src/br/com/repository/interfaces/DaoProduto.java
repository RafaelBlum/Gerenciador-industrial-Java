package br.com.repository.interfaces;

import org.springframework.stereotype.Repository;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.project.model.classes.Produto;

@Repository
public class DaoProduto extends ImplemantacaoCRUD<Produto> 
implements RepositoryProduto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

}
