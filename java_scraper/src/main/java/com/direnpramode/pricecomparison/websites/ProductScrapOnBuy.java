package com.direnpramode.pricecomparison.websites;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/** Scrape the video games from the website OnBuy */
public class ProductScrapOnBuy extends WebScraper{
	
	/** Empty constructor */
	public ProductScrapOnBuy () {
		
	}

	public void scrapeGamesPS4(String name, int id) throws Exception {
		
		//variable to store the index of the game found
		int foundTheGame = -1;
		
		//url of the website 
		String urlgamesfound = "https://www.onbuy.com/gb/search/?department=all&query=" + name + " PS4";
		
		//Download HTML document from website
		Document doc = Jsoup.connect(urlgamesfound).get();
		
		// get element to of no results
		Element noresult = doc.getElementById("no-search-results");
		
		// check if the there are results or not
		if (noresult == null) {
			
			// Get all of the products on the page
			Elements listprods = doc.select(".name");
			
			// Work through the all products
			for (int i = 0; i < listprods.size(); i++) {
				
				// Get the product name
				Element link = listprods.get(i).select("span").first();
				
				// converts into lower case
				String namee = link.text().toLowerCase();
				
				// checks if the name contains specific words
				if (namee.contains(name.toLowerCase()) && namee.contains("ps4")) {
					
					// if the name found contain the those words save in the variable the
					// foundTheGame the index value and break the loop
					foundTheGame = i;
					break;
				}
			}
			// if foundTheGame is different then -1
			if (foundTheGame != -1) {
				
			// scrap the url of the game and stores it
			String	url = scrapeUrlgame(urlgamesfound, foundTheGame);
			
			// save to the database
			gamesdao.addProductImage(id, 1, scrapeImage(url));
			
			gamesdao.addRetailPrice(id, 1, 4,url, scrapePrice(url));
			}

		}

	}

	
	public void scrapeGamesXBOXONE(String name, int id) throws Exception {

		//variable to store the index of the game found
		int foundTheGame = -1;
		
		//url of the website 
		String urlgamesfound = "https://www.onbuy.com/gb/search/?department=all&query=" + name + " XBOX ONE";
		
		//Download HTML document from website
		Document doc = Jsoup.connect(urlgamesfound).get();
		
		// get element to of no results
		Element noresult = doc.getElementById("no-search-results");
		
		// check if the there are results or not
		if (noresult == null) {
			
			// Get all of the products on the page
			Elements listprods = doc.select(".name");
			
			// Work through the all products
			for (int i = 0; i < listprods.size(); i++) {
				
				// Get the product name
				Element link = listprods.get(i).select("span").first();
				
				// converts into lower case
				String namee = link.text().toLowerCase();
				
				// checks if the name contains specific words
				if (namee.contains(name.toLowerCase()) && namee.contains("xbox one")) {
					
					// if the name found contain the those words save in the variable the
					// foundTheGame the index value and break the loop
					foundTheGame = i;
					break;
				}
			}
			// if foundTheGame is different then -1
			if (foundTheGame != -1) {
				
				// scrap the url of the game and stores it
				String	url = scrapeUrlgame(urlgamesfound, foundTheGame);
				
				// save to the database
				gamesdao.addProductImage(id, 2, scrapeImage(url));
							
				gamesdao.addRetailPrice(id, 2, 4,url, scrapePrice(url));
			}
		}

	}



	
	public String scrapeUrlgame(String url, int id) throws Exception {

		Document doc = Jsoup.connect(url).get();
		Elements listprods = doc.select(".product");
		Element link = listprods.get(id).select("a").first();
		String href = link.attr("href");
		return href;

	}

	
	public String scrapeImage(String href) throws Exception {
		
		Document doc = Jsoup.connect(href).get();
		String src = "";
		Element prods = doc.getElementById("img_01");
		Element img = prods.select("img").first();
		src = img.attr("src");
		return src;
	}

	
	public String scrapePrice(String href) throws Exception {
		
		Document doc = Jsoup.connect(href).get();
		String price = "";
		Elements prods = doc.select(".price");
		Element pricediv = prods.select("div").first();
		price = pricediv.text();
		price = price.replace("£", "");
		return price;
	}

}
