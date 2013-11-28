/**
 * 
 */
package com.bliferniz.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.bliferniz.dto.UserDetails;

public class HibernateTest {

	private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();
	
	/**
	 * 1) First Level Cache := Session
	 * 2) Second Level Cache	:= Across sessions in application
	 * 							:= Across applications
	 * 							:= Across clusters
	 * 3) Query Cache			:= 
	 */
	
	public static void main(String[] args) {

		Session session = factory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from UserDetails user where user.userId = 1");
		query.setCacheable(true); // Mark the query as cache able

		List<UserDetails> users = (List<UserDetails>) query.list();
		
		session.getTransaction().commit();
		session.close();

		Session session2 = factory.openSession();
		session2.beginTransaction();
		

		Query query2 = session2.createQuery("from UserDetails user where user.userId = 1");
		query2.setCacheable(true); //Tells the query to look up in the cache
		
		List<UserDetails> users2 = (List<UserDetails>) query2.list();
		
		session2.getTransaction().commit();
		session2.close();
		

	}

	
}
