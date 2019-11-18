package com.direnpramode.pricecomparison;

import java.util.List;


import com.direnpramode.pricecomparison.websites.WebScraper;


/**
 * Starts and stops the webscrapers
 * @author Diren Pramodacumar
 *
 */
public class ScraperManager {

	List<WebScraper> scraperList;

	// Starts up the scrapers
	public void startScraping() {
		for (WebScraper webScraper : scraperList) {
			webScraper.start();

		}
		// Wait for web scrapes to stop
		for (WebScraper webScraper : scraperList) {
			try {
				webScraper.join();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		if(!scraperList.isEmpty()) {
		    scraperList.get(0).getGamesDao().getSessionFactory().close();
		   
			} 
		System.out.println("Scraping terminated");
	}
	//Getters and setters
	public List<WebScraper> getScraperList() {
		return scraperList;
	}

	public void setScraperList(List<WebScraper> scraperList) {
		this.scraperList = scraperList;
	}

}
