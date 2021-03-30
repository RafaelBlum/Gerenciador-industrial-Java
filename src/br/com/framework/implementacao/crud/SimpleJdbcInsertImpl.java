package br.com.framework.implementacao.crud;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @propagation=Propagation.REQUIRED:: QUANDO NECESSARIO UMA TRANSAÇÃO PELO SPRING, SE NÃO EXISTIR ELA CRIA
 * 	
 * @rollbackFor=Exception.class:: DA ROLLBACK SE ACONTECER ERROS
 * 
 * */

@Component
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class SimpleJdbcInsertImpl extends SimpleJdbcInsert implements Serializable{

	private static final long serialVersionUID = 1L;

	public SimpleJdbcInsertImpl(DataSource dataSource) {
		super(dataSource);
	}

}
