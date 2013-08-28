package com.encima.dwc;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
 
public class HibernateHelper {
	
	EntityManagerFactory entityManagerFactory;
	
	 public void add(Object occ) {
		  entityManagerFactory = Persistence.createEntityManagerFactory( "gsn.utils.dwc" );
		  EntityManager entityManager = entityManagerFactory.createEntityManager();
		  entityManager.getTransaction().begin();
		  entityManager.persist(occ);
		  entityManager.getTransaction().commit();
		  entityManager.close();
	 }
}