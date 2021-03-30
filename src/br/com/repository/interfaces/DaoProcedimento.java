package br.com.repository.interfaces;

import org.springframework.stereotype.Repository;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.project.model.classes.Procedimento;

@Repository
public class DaoProcedimento extends ImplemantacaoCRUD<Procedimento> 
implements RepositoryProcedimento{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
