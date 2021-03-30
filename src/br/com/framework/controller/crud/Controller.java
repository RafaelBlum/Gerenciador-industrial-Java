package br.com.framework.controller.crud;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;

@Transactional
public class Controller extends ImplemantacaoCRUD<Object>{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void persist(Object entidade) throws Exception {
		super.persist(entidade);
	}

	@Override
	public void saveOrUpdate(Object entidade) throws Exception {
		super.saveOrUpdate(entidade);
	}

	@Override
	public void delete(Object entidade) throws Exception {

		super.delete(entidade);
	}

	@Override
	public Object findById(Class entidade, Long id) throws Exception {

		return super.findById(entidade, id);
	}



}
