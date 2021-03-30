package br.com.repository.interfaces;

import org.springframework.stereotype.Repository;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.project.model.classes.ContaRazao;

@Repository
public class DaoContaRazao extends ImplemantacaoCRUD<ContaRazao> 
implements RepositoryContaRazao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
