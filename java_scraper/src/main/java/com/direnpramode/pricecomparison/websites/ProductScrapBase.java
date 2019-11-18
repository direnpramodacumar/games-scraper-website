package com.direnpramode.pricecomparison.websites;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/** Scrape the video games from the website Base */
public class ProductScrapBase extends WebScraper {
	
	/** Empty constructor */
	public ProductScrapBase (){
		
	}
	
	public void scrapeGamesPS4(String name, int id) throws Exception {

		//variable to store the index of the game found
		int foundTheGame = -1;
		
		//url of the website 
		String urlgamesfound = "https://www.base.com/fsearch.htm?search=" + name + " PS4";
		
		//Download HTML document from website
		Document doc = Jsoup.connect(urlgamesfound).get();

		//Get all of the products on the page
		Elements prods = doc.select(".title");

		//checks if there are results 
		if (doc.select(".cell.cell-4col.cell-1").size() >= 0) {
			
			// Work through the all products
			for (int i = 0; i < prods.size(); i++) {
				
				// Get the product name
				Element a = prods.get(i).select("a").first();
				
				//converts into lower case
				String namee = a.text().toLowerCase();
				
				//checks if the name contains specific words 
				if (namee.contains(name.toLowerCase()) && namee.contains("ps4")) {
					
					//if the name found contain the those words save in the variable the foundTheGame the index value and break the loop
					foundTheGame = i;
					break;
				}
			}
			//if foundTheGame is different then -1
			if (foundTheGame != -1) {
				
				//scrap the url of the game and stores it
				
				String url = scrapeUrlgame(urlgamesfound, foundTheGame);
				
				// save to the database
				gamesdao.addProductImage(id, 1, scrapeImage(url));
				
				gamesdao.addRetailPrice(id, 1, 5,url, scrapePrice(url));
				
				
			}
		}
	}

	
	public String scrapeUrlgame(String url, int id) throws Exception {

		// Download HTML document from website
		Document doc = Jsoup.connect(url).get();
		String href = "https://www.base.com" +doc.select(".title").get(id).select("a").first().attr("href");
		return href;

	}

	
	public String scrapeImage(String href) throws Exception {
		Document doc = Jsoup.connect(href).get();
		String src = doc.getElementById("mainImage").select("img").first().attr("src");
		return "https://www.base.com" + src;
	}

	
	public String scrapePrice(String href) throws Exception {
		
		Document doc = Jsoup.connect(href).get();
		Elements priceinfo = doc.select(".price-info");
		String price= priceinfo.select(".price").select("span").first().text();
		price = price.replace("£", "");
		return price;
	}

	
	public void scrapeGamesXBOXONE(String name, int id) throws Exception {

		//variable to store the index of the game found
		int foundTheGame = -1;
		
		//url of the website 
		String urlgamesfound = "https://www.base.com/fsearch.htm?search=" + name + " XBOX ONE";
		
		//Download HTML document from website
		Document doc = Jsoup.connect(urlgamesfound).get();

		//Get all of the products on the page
		Elements prods = doc.select(".title");

		//checks if there are results 
		if (doc.select(".cell.cell-4col.cell-1").size() >= 0) {
			
			// Work through the all products
			for (int i = 0; i < prods.size(); i++) {
				
				// Get the product name
				Element a = prods.get(i).select("a").first();
				
				//converts into lower case
				String namee = a.text().toLowerCase();
				
				//checks if the name contains specific words 
				if (namee.contains(name.toLowerCase()) && namee.contains("xbox one")) {
					
					//if the name found contain the those words save in the variable the foundTheGame the index value and break the loop
					foundTheGame = i;
					break;
				}
			}
			//if foundTheGame is different then -1
			if (foundTheGame != -1) {
				
				//scrap the url of the game and stores it
				String url = scrapeUrlgame(urlgamesfound, foundTheGame);
				
				// save to the database
				gamesdao.addProductImage(id, 2, scrapeImage(url));
							
				gamesdao.addRetailPrice(id, 2, 5,url, scrapePrice(url));
			}
		}
	}
}
