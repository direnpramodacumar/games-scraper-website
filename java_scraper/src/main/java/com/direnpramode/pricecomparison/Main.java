package com.direnpramode.pricecomparison;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Contains the main method of the application
 * @author Diren Pramodacumar
 *
 */
public class Main {

	/**
	 * Main method of the application
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Instruct Spring to create and wire beans using annotations.
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		//Get the web scraper manager bean
		ScraperManager manager =(ScraperManager)context.getBean("scraper");
		
		//Get the names scraper bean
		NameProductScraper productNames =(NameProductScraper)context.getBean("namescraper");
		
		try {
			//scrap the name of the games 
			productNames.scrapeGames();
			
			//starts scraping from different websites
			//manager.startScraping();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
