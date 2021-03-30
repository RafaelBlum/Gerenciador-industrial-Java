package br.com.repository.interfaces;

import org.springframework.stereotype.Repository;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.project.model.classes.Setor;

@Repository
public class DaoSetor extends ImplemantacaoCRUD<Setor>
implements RepositorySetor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
