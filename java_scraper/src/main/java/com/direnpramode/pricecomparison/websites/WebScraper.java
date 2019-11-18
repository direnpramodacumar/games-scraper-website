package com.direnpramode.pricecomparison.websites;

import com.direnpramode.pricecomparison.GamesDao;
import com.direnpramode.pricecomparison.entities.ProductTable;

/**
 * Class inherited by all webscrapers
 * @author Diren Pramodacumar
 *
 */
public abstract class WebScraper extends Thread{
	
	//Used to store data
	GamesDao gamesdao;
	
	// Interval between HTTP requests to website
	int sleepTime = 1000;

	/**
	 * All classes that inherit from WebScraper must implement this method <br>
	 * Scrap PS4 games from the website
	 * @param name : the name of the game
	 * @param id : the id of the game from the product table
	 * @throws Exception
	 */
	public abstract void scrapeGamesPS4(String name, int id) throws Exception ;
	
	
	/**
	 * All classes that inherit from WebScraper must implement this method <br>
	 * Scrap the url of the game
	 * @param url :the url of the search 
	 * @param id :the id of the game from the product table
	 * @return the url of the game
	 * @throws Exception
	 */
	public abstract String scrapeUrlgame(String url,int id) throws Exception ;

	
	/**
	 * All classes that inherit from WebScraper must implement this method <br>
	 * Scrap the image of the game
	 * @param href :the url of the game
	 * @return the url of the image
	 * @throws Exception
	 */
	public abstract String scrapeImage(String href) throws Exception ;


	/**
	 * All classes that inherit from WebScraper must implement this method <br>
	 * Scrap the price of the game
	 * @param href : the url of the game
	 * @return the price of the game
	 * @throws Exception
	 */
	public abstract String scrapePrice(String href) throws Exception ;

	/**
	 * All classes that inherit from WebScraper must implement this method <br>
	 * Scrap XBOX ONE games from the website
	 * @param name : the name of the game
	 * @param id : the id of the game from the product table
	 * @throws Exception
	 */
	public abstract void scrapeGamesXBOXONE(String name, int id) throws Exception ;

	/** Run method inherited from Thread */
	public void run() {
	
		for (ProductTable game :gamesdao.searchProduct()) {
			try {
				//scrap ps4 games 
				scrapeGamesPS4(game.getName(), game.getId());
				//scrap xbox one games
				scrapeGamesXBOXONE(game.getName(), game.getId());
				//Sleep for sleep delay
				sleep(sleepTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//Getters and setters
	public GamesDao getGamesDao() {
		return gamesdao;
	}
	public void setGamesDao(GamesDao gamesdao) {
		this.gamesdao = gamesdao;
	}

	public int getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

}
