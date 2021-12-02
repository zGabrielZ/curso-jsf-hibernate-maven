package br.com.gabrielfereira.util;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped 
public class JPAHibernateUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory entityManagerFactory;
	
	public JPAHibernateUtil() {
		entityManagerFactory = Persistence.createEntityManagerFactory("projeto-jsf");
	}
	
	// Produzir esse entitymanager pra nao ser chamado manualmente
	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

}
