package br.com.repository.interfaces;

import org.springframework.stereotype.Repository;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.project.model.classes.Item;

@Repository
public class DaoItem extends ImplemantacaoCRUD<Item> 
implements RepositoryItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

}
