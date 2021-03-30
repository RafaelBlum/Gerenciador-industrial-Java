package br.com.repository.interfaces;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;
import br.com.project.model.classes.Entidade;

@Repository
public class DaoEntidade extends ImplemantacaoCRUD<Entidade> implements
		RepositoryEntidade {


	/**
	 * AULA 148
	 * VAMOS UTILIZAR SQL, POR SER MAIS RAPIDO E APRENDER JDBC DO SPRING
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	//CONSULTA O ULTIMO ACESSO
	@Override
	public void updateUltimoAcessoUser(String login) {
		String sql = "update entidade set ent_ultimoacesso = current_timestamp where ent_inativo is false and ent_login = ? ";
		super.getSimpleJdbcTemplate().update(sql, login);
	}

	//RETORNA BOOLEAN - TRUE OU FALSE
	@Override
	public boolean existeUsuario(String ent_login) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(1) >=1 from entidade where ent_login = '").append(ent_login).append("' ");
		return super.getJdbcTemplate().queryForObject(builder.toString(), Boolean.class);
	}	

	//BUSCA NO BANCO O ULTIMO ACESSO NO BANCO
	@Override
	public Date getUltimoAcessoEntidadeLogada(String name) {

		SqlRowSet rowSet = super
				.getJdbcTemplate()
				.queryForRowSet(
						"select ent_ultimoacesso from entidade where ent_inativo is false and ent_login = ?",
						new Object[] { name });
		//RETORNA O RESULTADO: next() = existe reusltado? SE SIM - rowSet, SE NÃO : nulo
		return rowSet.next() ? rowSet.getDate("ent_ultimoacesso") : null;
	}

}
