package br.com.repository.interfaces;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import br.com.framework.implementacao.crud.ImplemantacaoCRUD;

@Repository
public class DaoLogin extends ImplemantacaoCRUD<Object> implements RepositoryLogin{
	/**
	 * A DaoLogin é implementada pela RepositoryLogin;
	 * E precisamos da conexão com banco onde extends ImplemantacaoCRUD<Object>;
	 */
	
	
	
	private static final long serialVersionUID = 1L;

	//CONSULTA NO BANCO SE O USUÁRIO EXISTE REALMENTE
	//Utilizamos SQL por ser mais rapido
	@Override
	public boolean autentico(String login, String senha) throws Exception {
		String sql = "select count(1) as autentica from entidade where ent_login = ? and ent_senha = ?";
		
		//SqlRowSet = JDBC DO SPRING: RETORNA UMA LINHA, COM DADOS DO SQL, ASSIM RETORNANDO UMA LINHA SE EXISTE OU NÃO.
		//getJdbcTemplate  tem varios metodo query e neste assamos a sql e instanciamos o object passando parametros.
		SqlRowSet sqlRowSet = super.getJdbcTemplate().queryForRowSet(sql, new Object[]{login, senha});
		
		//RETORNO VAI SER CONFORME CONDIÇÃO, SE MAIOR QUE ZERO, SES NÃO FALSE
		//next() = retorna sempre zero ou um
		return sqlRowSet.next() ? sqlRowSet.getInt("autentica") > 0 : false;
	}

}
