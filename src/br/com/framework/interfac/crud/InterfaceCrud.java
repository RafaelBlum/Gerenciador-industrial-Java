package br.com.framework.interfac.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public interface InterfaceCrud<T> extends Serializable{
	/*DOC AULA 82
	 * ESTA CLASSE PODE SALVAR QUALQUER OBJETO.
	 * NÃƒO HÃ� NECESSIDADE DE COLOCAR OS METODOS COMO "PUBLIC" POR QUE TODA INTERFACE Ã‰ PUBLICA.
	 * 
	 *@Component
	 *INJEÃ‡ÃƒO DE DEPENDENCIA DO SPRING. 
	 *
	 *@Transactional
	 * COMO Ã‰ UMA CLASSE QUE IRÃ� FAZER TRANSAÃ‡Ã•ES EM BANCO DE DADOS, ASSIM O SPRING SABE O QUE A CLASSE ESTA REALIZANDO.
	 * */
	
	//SALVA DADOS
	void save(T obj) throws Exception;
	
	void persist(T obj) throws Exception;
	
	//SALVA OU ATUALIZA
	void saveOrUpdate(T obj) throws Exception;
	
	//REALIZA O UPDATE/ATUALIZAÃ‡ÃƒO DE DADOS
	void update(T obj) throws Exception;
	
	void delete(T obj) throws Exception;
	
	//SALVA OU ATUALIZA E RETORNA OBJ EM ESTADO PERSISTENTE; 
	T merge(T obj) throws Exception;
	
	//PRIMEIRO PRECISAMOS SABER QUAL A CLASSE E NÃƒO O OBJETO (CLASS<T>)
	//CARREGA A LISTA DE DADOS DE DETERMINADA CLASSE.
	List<T> findList(Class<T> obj) throws Exception;
	
	//BUSCA OBJETO QUALQUER: QUAL CLASSE E BUSCA POR ID
	Object findById(Class<T> entidade, Long id) throws Exception;
	
	//OUTRA FORMA DE BUSCA EM VEZ DE OBJETO Ã‰:
	//BUSCA O T
	T findByPorId(Class<T> entidade, Long id) throws Exception;
	
	//CARREGAMENTO DOS DADOS  EM QUALQUER CLASSE, PASSANDO QUERY DINAMICA
	List<T> findByQueryDinamica(String q) throws Exception;
	
	//AULA 83*********
	//RECEBE QUERY E SE ACONTECER ALGUM ERRO, RETORNA
	//PASSA UM HQL E EXECUTA
	void executeUpdateQueryDinamico(String q) throws Exception;
	
	//EXECUTA UPDATE COM SQL
	void executeUpdateSQLDinamico(String q) throws Exception;
	
	//FAZER O HIBERNATE LIMPAR SUA SESSÃƒO - LIMPAR CACHÃŠ
	void clearSession() throws Exception;
	
   /*RETIRA UM OBJETO DA SESSÃƒO DO HIBERNATE
	*AS VEZES TEMOS UM OBJETO QUE ESTÃƒO EM DIFERENTES TRANSAÃ‡Ã•ES OU ESTA NA MEMORIA E 
	*PODEM OCASIONAR ERROS DE PERSISTENCIA, ENTÃƒO
	*TIRAMOS O OBJ E ASSIM EXECUTAR AS OPERAÃ‡Ã•ES.
	**/
	void evict(Object objs) throws Exception;
	
	//RETORNA A SESSÃƒO NA INTERFACE
	Session getSession() throws Exception;

	//PODE SER T OU ?: RETORNA UM CONJUNTO DE RESULTADOS - PASSAMOS SQL
	List<?> getListSQLDinamico(String sql) throws Exception;
	
   /*USAREMOS UMA PARTE DE JDBC DO SPRING::
	*EXISTE JDBC DA API JAVA MAS Ã‰ COMPLEXA, MAS O SPRING TEM UMA PARTE QUE COMPLEMENTA O JDBC,
	*ABSTRAI COM FACILIDADE, E PROVE ALGUMAS CLASSES QUE FACILITAM E COM PERFORMANCE (PARECE HIBERNATE, MAS Ã‰ COM JDBC).
	*TRÃŠS CLASSE E CADA CLASSE TEM SEUS MÃ‰TODOS::
	**/
	JdbcTemplate getJdbcTemplate();	
	SimpleJdbcTemplate getSimpleJdbcTemplate();	
	SimpleJdbcInsert getSimpleJdbcInsert();
	
	//SABER QUANTOS REGISTROS TEM UMA TABELA
	Long totalRegistro(String table) throws Exception;
	
	//MEDOTO QUERY PARA MONTAR CONSULTAS DINAMICAS, CONSEGUIMOS CONSTRUIR UM METODO GENERICO SQL OU QUERY
	Query obterQuery(String query) throws Exception;
	
	//RETORNA UMA LISTA GENERICA DE DADOS - RECEBE UMA QUERY, INT INCIAL, INT MAXIMO. 
	//PARA CARREGAR POR DEMANDA, PARA SABER NA TELA A QUANTIDADE ESPECIFICA NA TELA.
	List<T> findByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception;	
	
	List<T> findByQueryDinamicaNative(String query, int iniciaNoRegistro, int maximoResultado) throws Exception;	
	
	public List<T> findByQueryDinamica2(String q, String field, List params,  int iniciaNoRegistro,
			int maximoResultado) throws Exception;
	
}
