package com.direnpramode.pricecomparison;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.direnpramode.pricecomparison.entities.ProductImageTable;
import com.direnpramode.pricecomparison.entities.ProductTable;
import com.direnpramode.pricecomparison.entities.RetailerPriceTable;

@DisplayName("Test GameDao")
public class GamesDaoTest {

	public static SessionFactory sessionFactory;

	@BeforeAll
	public static void init() {
		
			try {
				// Create a builder for the standard service registry
				StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

				// Load configuration from hibernate configuration file.
				// Here we are using a configuration file that specifies Java annotations.
				standardServiceRegistryBuilder.configure("resources/hibernate-annotations.cfg.xml");

				// Create the registry that will be used to build the session factory
				StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
				try {
					// Create the session factory - this is the goal of the init method.
					sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
				} catch (Exception e) {
					/*
					 * The registry would be destroyed by the SessionFactory, but we had trouble
					 * building the SessionFactory, so destroy it manually
					 */
					System.err.println("Session Factory build failed.");
					e.printStackTrace();
					StandardServiceRegistryBuilder.destroy(registry);
				}

				// Output result
				System.out.println("Session factory built.");

			} catch (Throwable ex) {
				// Make sure you log the exception, as it might be swallowed
				System.err.println("SessionFactory creation failed. " + ex);
				ex.printStackTrace();
			}
		}

	

	@Test
	@DisplayName("Test save game name in product table")
	public void testSaveNameGametoDatabase() {
		
		// Create instance of class we are going to test
		GamesDao gamedao = new GamesDao();
		
		gamedao.setSessionFactory(sessionFactory);
		
		//add the name Pokemon and description
		gamedao.addProduct("Pokemon");
		
		// Get a new session instance from the session factory
		Session session = sessionFactory.getCurrentSession();
		
		// Start transaction
		session.beginTransaction();
		
		// create a query to get the name of the product
		List<ProductTable> gamesList = session.createQuery("from ProductTable where name='Pokemon'").getResultList();
		
		//One result should be stored 
		assertEquals(gamesList.size(),1);
		
		// Commit transaction to save it to database
		session.getTransaction().commit();
		
		// Close the session and release database connection
		session.close();
	
	}
	@Test
	@DisplayName("Test save game image, product_id and format_id in productimage table")
	public void testSaveImage() {
		
		
		// Create instance of class we are going to test
		GamesDao gamedao = new GamesDao();
		
		gamedao.setSessionFactory(sessionFactory);
		//add the image of the game
		gamedao.addProductImage(339,1, "https://www.pokemon.com/image");
				
		// Get a new session instance from the session factory
		Session session = sessionFactory.getCurrentSession();
		
		// Start transaction
		session.beginTransaction();
				
		// create a query to get the image of the product
		List<ProductImageTable> imageList = session.createQuery("from ProductImageTable where product_id=339").getResultList();
				
		//One result should be stored 	
		assertEquals(imageList.size(),1);
				
		// Commit transaction to save it to database
		session.getTransaction().commit();
				
		// Close the session and release database connection 
		session.close();
				
	}
	
	
}
