package com.direnpramode.pricecomparison.websites;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** 
 * Scrape the video games from the website Argos
 * @author Diren Pramodacumar
 */
public class ProductScrapArgos extends WebScraper {

	/** Empty constructor */
	public ProductScrapArgos() {
	}

	/** Scrap PS4 games from the website Argos */
	public void scrapeGamesPS4(String name, int id) throws Exception {

		// check if the name have a comma, if have it replace
		if (name.contains(":")) {
			name = name.replace(":", "");
		}
		// variable to store the index of the game found
		int foundTheGame = -1;

		// url of the website
		String urlgamesfound = "https://www.argos.co.uk/search/" + name + " PS4";

		// Download HTML document from website
		Document doc = Jsoup.connect(urlgamesfound).get();

		// get elements to of no results
		Element noresult = doc.selectFirst(".search-replacement__label.h3");
		Element noresult2 = doc.selectFirst(".font-standard.no-results");

		// check if the there are results or not
		if (noresult == null || noresult2 == null) {

			// Get all of the products on the page
			Elements listprods = doc.select(".ac-product-name.ac-product-card__name");

			// Work through the all products
			for (int i = 0; i < listprods.size(); i++) {

				// Get the product name
				Element link = listprods.get(i).select("div").first();

				// converts into lower case
				String namee = link.text().toLowerCase();

				// checks if the name contains specific words
				if (namee.contains(name.toLowerCase()) && namee.contains("ps4")
						&& (Double.parseDouble(scrapePrice(scrapeUrlgame(urlgamesfound, i))) < 70)) {
					// if the name found contain the those words save in the variable the
					// foundTheGame the index value and break the loop
					foundTheGame = i;
					break;
				}
			}
			// if foundTheGame is different then -1
			if (foundTheGame != -1) {
				
				// scrap the url of the game and stores it
				String url = scrapeUrlgame(urlgamesfound, foundTheGame);
				// save to the database
				gamesdao.addProductImage(id, 1, scrapeImage(url));
				
				gamesdao.addRetailPrice(id, 1, 3,url, scrapePrice(url));
			}
		}
	}

	public String scrapeUrlgame(String url, int id) throws Exception {

		Document doc = Jsoup.connect(url).get();
		Elements listprods = doc.select(".ac-product-link.ac-product-card__details");
		Element link = listprods.get(id).select("a").first();
		String href = "https://www.argos.co.uk" + link.attr("href");
		return href;
	}

	public String scrapePrice(String href) throws Exception {

		Document doc = Jsoup.connect(href).get();
		Elements prods = doc.select(".price.product-price-primary");
		Element li = prods.select("li").first();
		return li.attr("content");
	}


	public String scrapeImage(String href) throws Exception {

		Document doc = Jsoup.connect(href).get();
		Elements prods = doc.select(".active");
		Element img = prods.select("img").first();

		return img.attr("src");
	}


	public void scrapeGamesXBOXONE(String name, int id) throws Exception {
		
		//check if the name have a comma, if have it replace
		if(name.contains(":")) {
			name=name.replace(":","");
		}
		
		//variable to store the index of the game found
		int foundTheGame = -1;
		
		//url of the website 
		String urlgamesfound = "https://www.argos.co.uk/search/" + name +" XBOX ONE";
				
		//Download HTML document from website
		Document doc = Jsoup.connect(urlgamesfound).get();
				
		//get elements to of no results
		Element noresult = doc.selectFirst(".search-replacement__label.h3");
		Element noresult2 = doc.selectFirst(".font-standard.no-results");
				
		//check if the there are results or not
		if (noresult == null||noresult2==null) {
					
			//Get all of the products on the page
			Elements listprods = doc.select(".ac-product-name.ac-product-card__name");
					
			// Work through the all products
			for (int i = 0; i < listprods.size(); i++) {
						
				// Get the product name
				Element link = listprods.get(i).select("div").first();
						
				//converts into lower case
				String namee = link.text().toLowerCase();
						
				//checks if the name contains specific words 
				if (namee.contains(name.toLowerCase()) && namee.contains("xbox one")&& (Double.parseDouble(scrapePrice(scrapeUrlgame(urlgamesfound, i))) < 70)) {
				
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
							
				gamesdao.addRetailPrice(id, 2, 3,url, scrapePrice(url));
			}
		}
	}
}
