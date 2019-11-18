package com.direnpramode.pricecomparison;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Scrape video games names from the website Simply
 * @author Diren Pramodacumar
 */
public class NameProductScraper {
	
	//list to store the name of the games
	private List<String> names = new ArrayList<String>();
	
	GamesDao gamesdao;

	/** Empty constructor */
	public NameProductScraper() {

	}
	/**
	 * Scrap a list of games name from the website Simply
	 * 
	 * @throws Exception
	 */
	public void scrapeGames() throws Exception {
		
		//loop to have access to diferent pages 
		for (int j = 1; j < 12; j++) {
			
			//url of the website 
			String urllistgames = "https://www.simplygames.com/ps4/games?p=" + Integer.toString(j);
			
			//Download HTML document from website
			Document doc = Jsoup.connect(urllistgames).get();
			
			//Get all of the products on the page
			Elements prods = doc.select(".list_item");

			// Work through the all products
			for (int i = 0; i < prods.size(); i++) {
				
				// Get the productName
				Elements name = prods.get(i).select(".product_name");
				
				//stores the name 
				String nameClean = name.text();
				
				//checks if from the websites it contains packs or collection 
				if (nameClean.contains("Pack")||nameClean.contains("and")||nameClean.contains("&") || nameClean.contains("Collection"))
					continue;
				//if not remove uncessary details from the name 
				else {
					nameClean = nameClean.replaceAll("(?: [-]\\s*(?:\\S+)\\s*)+(.*?)(.*)", "");
					nameClean = nameClean.replaceAll("(?:[+]\\s*(?:\\S+)\\s*)+(.*?)(.*)", "");
					nameClean = nameClean.replace("Pre Order Game ", "");
					nameClean = nameClean.replace("Patch", "");
					nameClean = nameClean.replace("+ Bonus", "");
					nameClean = nameClean.replace("DLC", "");
					nameClean = nameClean.replace("Content", "");
					nameClean = nameClean.replace("VR Compatible", "");
					nameClean = nameClean.replace("(PlayStation VR)", "");
					nameClean = nameClean.replace("...", "");
				}
				//stores the url of the game
				String productLinkHref = scrapeUrlgame(urllistgames, i);
				
				//check if the list array is empty or not 
				if (names.isEmpty()) {
					
					//add the name to the list
					names.add(nameClean);
					
					//add to the database
					gamesdao.addProduct(nameClean);
					
					
				} 
				//if the list is not empty
				else {
					
					//check if duplicates exists 
					if (searchforduplicates(nameClean) != "1") {
						
						//if doesn't exist adds to the list 
						names.add(nameClean);
						
						//add to the database
						gamesdao.addProduct(nameClean);
					}
				}
			}
		}

	}

	/**
	 * Scrap the url of the game
	 * 
	 * @param url : the url of the search 
	 * @param i :the index of the specific game searched
	 * @return the url of the game
	 * @throws Exception
	 */
	public String scrapeUrlgame(String url, int i) throws Exception {

		Document doc = Jsoup.connect(url).get();
		
		Elements prods = doc.select(".list_item");
		
		Elements productLink = prods.get(i).select("a");
		
		url = "https://www.simplygames.com" + productLink.attr("href");
		
		return url;

	}
	
	/**
	 * Search for duplicate names
	 * @param name : the name of the game
	 * @return the name of the game or a value
	 */
	public String searchforduplicates(String name) {
		
		//loop to check the name
		for (String gamename : names) {
			//if the name already exists in the array then return char 1
			if (name.equals(gamename)) {
				name = "1";
				break;
			}
			
		}
		//if not return the name of the game 
		return name;

	}

	//Getters and setters
	
	public GamesDao getGamesdao() {
		return gamesdao;
	}
	

	public void setGamesdao(GamesDao gamesdao) {
		this.gamesdao = gamesdao;
	}

	


}
