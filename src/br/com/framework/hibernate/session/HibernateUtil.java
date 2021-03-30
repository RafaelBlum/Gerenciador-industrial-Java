package br.com.framework.hibernate.session;

/*
 * RESPONSAVEL POR ESTABELECER A CONEXÃO COM HIBERNATE
 * @author Rafael Blum
 * */
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.bean.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

import br.com.framework.implementacao.crud.VariavelConexaoUtil;

@ApplicationScoped
public class HibernateUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static String JAVA_COMP_ENV_JDBC_DATA_SOURCE = "java:/comp/env/jdbc/datasource";
	
	private static SessionFactory sessionFactory = buildSessionFactory();
	
	/*
	 * RESPONSAVEL POR LER O ARQUIVO DE CONFIGURAÇÃO HIBERNATE.cfg.xml
	 * */	
	private static SessionFactory buildSessionFactory(){
		try{
			if(sessionFactory == null){
				sessionFactory = new Configuration().configure().buildSessionFactory();
			}
			
			return sessionFactory;
		}catch(Exception e){
			e.printStackTrace();
			throw new ExceptionInInitializerError("ERRO AO CRIAR A CONEXÃO SESSIONFACTORY!!");
		}
	}
	
	
	/*RETORNA O SESSIONFACTORY CORRENTE
	 * @return: SessionFactory
	 * */
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	
	/*RETORNA O SESSÃO DO SESSIONFACTORY
	 * @return: Session
	 * */
	public static Session getCurrentSession(){
		return getSessionFactory().getCurrentSession();
	}
	
	
	/*
	 * ABRE UMA NOVA SESSÃO DO SESSIONFACTORY
	 * @return Session
	 * */
	public static Session openSession(){
		if(sessionFactory == null){
			buildSessionFactory();
		}
		return sessionFactory.openSession();
	}
	
	/*
	 * OBTER A CONECTION DO PROVEDOR DE CONEXÃO CONFIGURADO
	 * @return Connection SQL
	 * @throws SQLException
	 * */
	public static Connection getConnerctionProvider() throws SQLException{
		return ((SessionFactoryImplementor) sessionFactory).getConnectionProvider().getConnection();
	}
	
	/*
	 * @return Connection no InitialContext java:/comp/env/jdbc/datasource
	 * @throws Exception
	 * */
	public static Connection getConnection () throws Exception{
		InitialContext context = new InitialContext();
		DataSource ds = (DataSource) context.lookup(JAVA_COMP_ENV_JDBC_DATA_SOURCE);
		
		return ds.getConnection();
	}
	
	
	/*
	 * @return DataSource JNDI TomCat
	 * @throws NamingException
	 * */
	public DataSource getDataSourceJndi() throws NamingException{
		InitialContext context = new InitialContext();
		return (DataSource) context.lookup(VariavelConexaoUtil.JAVA_COMP_ENV_JDBC_DATA_SOURCE);
	}
	
}
