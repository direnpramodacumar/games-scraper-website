package com.direnpramode.pricecomparison;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.*;
import com.direnpramode.pricecomparison.entities.*;


/**
 * Manages the storage of the detail of the games
 * @author Diren Pramodacumar
 *
 */
public class GamesDao {

	// Injected by Spring
	public SessionFactory sessionFactory;

	/** Empty constructor */
	public GamesDao() {

	}
	/**
	 * Adds a name and description into the product table
	 * @param name :the name of the game 
	 */
	public void addProduct(String name) {

		// Get a new session instance from the session factory
		Session session = sessionFactory.getCurrentSession();

		// Start transaction
		session.beginTransaction();

		// Create an instance of a ProductTable class
		ProductTable product = new ProductTable();

		// Set values of the game
		product.setName(name);

		// Add the product to the database
		session.save(product);

		// Commit transaction to save
		session.getTransaction().commit();

		// Close the session and release database connection
		session.close();

		System.out.println("ID:" + product.getId() + "  Name:" + name);

	}
	/**
	 * Adds a image url and product_id and the format_id into the productimage table
	 * @param id :the product id from the product table
	 * @param formatId :the format id from the format table
	 * @param image :the url of the image 
	 */
	public synchronized void addProductImage(int id, int formatId,String image) {
		// Get a new session instance from the session factory
		Session session = sessionFactory.getCurrentSession();

		// Start transaction
		session.beginTransaction();

		// Create an instance of a ProductImageTable class
		ProductImageTable productImage = new ProductImageTable();
		
		// query to get the list of images of a particular game according to the product_id and format_id
		List<ProductImageTable> imgList = session.createQuery("from ProductImageTable where product_id="
						+ Integer.toString(id) + " and " + "format_id=" + Integer.toString(formatId)).getResultList();
		// create a query to get the id of the product
		List<ProductTable> gamesList = session.createQuery("from ProductTable where id=" + Integer.toString(id))
							.getResultList();

		// check if found the image in the database
		if (imgList.size() == 0) {

			// if doesn't find in database then set the image and the format
			productImage.setImage(image);
			productImage.setFormatTable(formatId);
			productImage.setProductId(gamesList.get(0));
			
			// Add the productimage to the database
			session.save(productImage);

			// Commit transaction to save
			session.getTransaction().commit();

			// Close the session and release database connection
			session.close();
		}
		 
		else { 
			System.out.println("Image already exist");
		}
		
		session.close();
		
	}
	/**	 
	 * Adds the prices of the game from different retailers.
	 * @param id :the product id from the product table
	 * @param formatId :the format id from the format table
	 * @param retailId :the retail id from the retailer table
	 * @param url : the url of the game
	 * @param price -: the price of the game
 	 * 

	 */
	public synchronized void addRetailPrice(int id,int formatId,int retailId,String url,String price) {
		
		// Get a new session instance from the session factory
		Session session = sessionFactory.getCurrentSession();

		// Start transaction
		session.beginTransaction();
		
		List<ProductImageTable> imgList = session.createQuery("from ProductImageTable where product_id="
				+ Integer.toString(id) + " and " + "format_id=" + Integer.toString(formatId)).getResultList();
		
		// create a query to get the id of the product
		List<ProductTable> gamesList = session.createQuery("from ProductTable where id=" + Integer.toString(id))
							.getResultList();
		// create a query to get the prices of the game
		List<RetailerPriceTable> retailList = session.createQuery("from RetailerPriceTable where product_id="
				+ Integer.toString(id) + " and " + "retailer_id=" + Integer.toString(retailId) + " and "
				+ "productimage_id=" + Integer.toString(imgList.get(0).getId())).getResultList();
		
		// check if found the price in the database
		if(retailList.size()==0) {
			// Create an instance of a RetailerPriceTable class
			RetailerPriceTable retailerPrice = new RetailerPriceTable();
	
			// then set in the retailer table the url, price, the retailer_id and the productimage_id
			retailerPrice.setUrl(url);
			retailerPrice.setPrice(Double.parseDouble(price));
			retailerPrice.setRetailId(retailId);
			retailerPrice.setProductimageid(imgList.get(0));
			retailerPrice.setProductid(gamesList.get(0));
						
			// Add the retailerprice to the database
			session.save(retailerPrice);
	
			// Commit transaction to save
			session.getTransaction().commit();
	
			// Close the session and release database connection
			session.close();
		}
		
		else { 
			System.out.println("Retailer Price already exist");
		}
		
		session.close();
		
	}




	/**
	 * Return a products names list from the database
	 * 
	 * @return List games name
	 */
	public List<ProductTable> searchProduct() {

		// Create list to names and id of the games
		List<ProductTable> names = new ArrayList<ProductTable>();

		// Get a new session instance from the session factory
		Session session = sessionFactory.getCurrentSession();

		// Start transaction
		session.beginTransaction();

		// query to get the names and the id of each game
		List<ProductTable> gamesList = session.createQuery("from ProductTable").getResultList();

		for (ProductTable games : gamesList) {
			// Create an instance of a ProductTable class
			ProductTable n = new ProductTable();

			// Set values of the game
			n.setId(games.getId());
			n.setName(games.getName());

			// add to the list
			names.add(n);
		}

		// Commit transaction to save close the session
		session.getTransaction().commit();

		// Close the session and release database connection
		session.close();

		// return the list
		return names;
	}

	// Getters and setters
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
