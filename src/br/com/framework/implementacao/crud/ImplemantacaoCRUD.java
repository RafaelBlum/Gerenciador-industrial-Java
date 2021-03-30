package br.com.framework.implementacao.crud;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.interfac.crud.InterfaceCrud;

@Component
@Transactional
public class ImplemantacaoCRUD<T> implements InterfaceCrud<T>{

	/**
	 * IMPLEMENTAÇÃO DE TODOS METODOS DA INTERFACE AULA 84
	 */
	private static final long serialVersionUID = 1L;
	
	private static SessionFactory sessionFactory = HibernateUtil
			.getSessionFactory();
	
	//JDBC DO SPRING
	@Autowired
	private JdbcTemplateImpl jdbcTemplate;
	
	@Autowired
	private SimpleJdbcTemplateImpl simpleJdbcTemplate;
	
	@Autowired
	private SimpleJdbcInsertImpl simpleJdbcInsert;
	
	@Autowired
	private SimpleJdbcClassImpl simpleJdbcClassImpl;

	@Override
	public void save(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().save(obj);
		executeFlushSession();
	}

	@Override
	public void persist(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().persist(obj);
		executeFlushSession();
	}

	@Override
	public void saveOrUpdate(Object entidade) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().saveOrUpdate(entidade);
		executeFlushSession();
	}

	@Override
	public void update(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().update(obj);
		executeFlushSession();

	}

	@Override
	public void delete(T obj) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().delete(obj);
		executeFlushSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T merge(T obj) throws Exception {
		validaSessionFactory();
		obj = (T) sessionFactory.getCurrentSession().merge(obj);
		executeFlushSession();
		return obj;
	}

	@Override
	public List<T> findList(Class<T> entidade) throws Exception {
		validaSessionFactory();

		StringBuilder query = new StringBuilder();
		query.append(" select distinct(entity) from ").append(entidade.getSimpleName()).append(" entity ");

		List<T> lista = sessionFactory.getCurrentSession().createQuery(query.toString()).list();

		return lista;
	}

	@Override
	public Object findById(Class<T> entidade, Long id) throws Exception {
		validaSessionFactory();
		Object obj = sessionFactory.getCurrentSession().load(entidade, id);
		return obj;
	}

	@Override
	public T findByPorId(Class<T> entidade, Long id) throws Exception {
		validaSessionFactory();
		T obj = (T) sessionFactory.getCurrentSession().load(entidade, id);
		return obj;
	}
	
	@Override
	public List<T> findByQueryDinamica(String q) throws Exception {
		validaSessionFactory();
		List<T> lista = new ArrayList<T>();
		lista = sessionFactory.getCurrentSession().createQuery(q).list();
		return lista;
	}

	//PARA HQL
	@Override
	public void executeUpdateQueryDinamico(String q) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().createQuery(q).executeUpdate();
		executeFlushSession();		
	}

	//PARA SQL PURO
	@Override
	public void executeUpdateSQLDinamico(String q) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().createSQLQuery(q).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void clearSession() throws Exception {
		sessionFactory.getCurrentSession().clear();
		
	}

	@Override
	public void evict(Object objs) throws Exception {
		validaSessionFactory();
		sessionFactory.getCurrentSession().evict(objs);		
	}

	@Override
	public Session getSession() throws Exception {
		validaSessionFactory();
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<?> getListSQLDinamico(String sql) throws Exception {
		validaSessionFactory();		
		List<?> lista = sessionFactory.getCurrentSession().createSQLQuery(sql).list();		
		return lista;
	}

	//NAO USA VALIDASESSION POR USAMOS O SPRING AQUI
	@Override
	public Long totalRegistro(String table) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(1) from ").append(table);
		return jdbcTemplate.queryForLong(sql.toString());
	}

	@Override
	public Query obterQuery(String query) throws Exception {
		validaSessionFactory();
		Query queryReturn = sessionFactory.getCurrentSession().createQuery(query.toString());
		return queryReturn;
	}
	
	public int totalEros() throws HibernateException, Exception{
		Query soma = sessionFactory.getCurrentSession().createQuery(" select sum(Documento.erro) from Documento where Documento.dataEnvio = current_date() ");
		return soma.list().size();
		
	}
	
	/**
	 * Realiza consulta no banco de dados, iniciando o carregamento a partir do
	 * registro passado no paramentro -> iniciaNoRegistro e obtendo maximo de
	 * resultados passados em -> maximoResultado.
	 * 
	 * @param query
	 * @param iniciaNoRegistro
	 * @param maximoResultado
	 * @return List<T>
	 * @throws Exception
	 */
	
	@Override
	public List<T> findByQueryDinamica(String query, int iniciaNoRegistro,
			int maximoResultado) throws Exception {
		validaSessionFactory();
		List<T> lista = new ArrayList<T>();
		lista = sessionFactory.getCurrentSession()
				.createQuery(query.toString()).setFirstResult(iniciaNoRegistro)
				.setMaxResults(maximoResultado).list();
		System.out.println("Metodo findByQueryDinamica: " + query.toString());
		return lista;
	}
	
	@Override
	public List<T> findByQueryDinamicaNative(String query, int iniciaNoRegistro,
			int maximoResultado) throws Exception {
		validaSessionFactory();
		List<T> lista = new ArrayList<T>();
		lista = sessionFactory.getCurrentSession().createSQLQuery(query.toLowerCase()).setFirstResult(iniciaNoRegistro)
				.setMaxResults(maximoResultado).list();
		return lista;
	}

	//METODOS GET****************************************
	public SimpleJdbcClassImpl getSimpleJdbcClassImpl() {
		return simpleJdbcClassImpl;
	}
	
	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	@Override
	public SimpleJdbcInsert getSimpleJdbcInsert() {
		return simpleJdbcInsert;
	}
	
	private void validaSessionFactory(){
		if(sessionFactory == null){
			sessionFactory = HibernateUtil.getSessionFactory();
		}
		validaTransaction();
	}
	
	private void validaTransaction(){
		if(!sessionFactory.getCurrentSession().getTransaction().isActive()){
			sessionFactory.getCurrentSession().beginTransaction();
		}
	}
	
	private void commitProcessoAjax(){
		sessionFactory.getCurrentSession().beginTransaction().commit();
	}
	
	private void roolBackProcessoAjax(){
		sessionFactory.getCurrentSession().beginTransaction().rollback();
	}
	
	//RODA INSTANTANEAMENTE O SQL DO BANCO DE DADOS
	//CASO ALGUM DADOS PRECISE DO OUTRO. EX: TELEFONE ADD E NÃO TEM PESSOA CRIADA AINDA.
	private void executeFlushSession(){
		sessionFactory.getCurrentSession().flush();
	}

	
	//RETORNA UMA LISTA DE ARRAY DE OBJETOS GENERICOS
	public List<Object[]> getListSQLDinamicoArray(String sql) throws Exception {
		validaSessionFactory();
		
		List<Object[]> lista = (List<Object[]>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return lista;
	}
	
	// RETORNA UM UNICO RESULTADO - RETORNA UM USUARIO, UMA CIDADE, ETC...
	public T findUniqueByQueryDinamica(String query) throws Exception{
		validaSessionFactory();
		T obj = (T) sessionFactory.getCurrentSession()
				.createQuery(query.toString()).uniqueResult();
		return obj;
	}

	//MOTAMOS UM SQL DINAMICO AQUI - 
	//SELECIONA DA ENTIDADE X  QUANDO UM ATRIBUTO SEJA IGUAL AO LOGIN PASSADO E CONDIÇÃO;
	public T findIuniqueByProperty(Class<T> entidade, Object valor, String atributo, String condicao) throws Exception{

		validaSessionFactory();
		
		StringBuilder query = new StringBuilder();
		query.append(" select entity from ").append(entidade.getSimpleName())
				.append(" entity where entity.").append(atributo)
				.append(" = '").append(valor).append("' ").append(condicao);

		T obj = (T) this.findUniqueByQueryDinamica(query.toString());

		return obj;
	}

	@Override
	public List<T> findByQueryDinamica2(String q, String field, List params,  int iniciaNoRegistro,
			int maximoResultado) throws Exception {
		validaSessionFactory();
		
		return  sessionFactory.getCurrentSession().createQuery(q)
				.setParameterList(field, params)
				.setFirstResult(iniciaNoRegistro)
				.setMaxResults(maximoResultado).list();
	}
}
