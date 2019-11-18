package com.direnpramode.pricecomparison.websites;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/** Scrape the  games from the website Ebay */

public class ProductScrapEbay extends WebScraper {

	/** Empty constructor */
	public ProductScrapEbay() {
		
	}

	
	public void scrapeGamesPS4(String name, int id) throws Exception {
		
		//variable to store the index of the game found
		int foundTheGame=-1;
		
		//url of the website 
		String urlgamesfound = "https://www.ebay.co.uk/sch/i.html?_from=R40&_trksid=m570.l1313&_nkw="+ name +" PS4"+"&_sacat=0&_oac=1";
		
		//Download HTML document from website
		Document doc = Jsoup.connect(urlgamesfound).get();
		
		//Get all of the products on the page
		Elements prods = doc.select(".sresult.lvresult.clearfix.li.shic");
		
		// Work through the all products
		for(int i=0;i<prods.size();i++) {
			
			// Get the product name
			Element h2 = prods.select(".vip").get(i).select("a").first();
			
			//converts into lower case
			String namelow = h2.text().toLowerCase();
			
			//checks if the name contains specific words 
			if(namelow.contains(name.toLowerCase())&&namelow.contains("ps4")) {
				
				//if the name found contain the those words save in the variable the foundTheGame the index value and break the loop
				foundTheGame=i;
				break;
			}
		}
		//if foundTheGame is different then -1
		if(foundTheGame!=-1) {

			String url=scrapeUrlgame(urlgamesfound,foundTheGame);
			
			// save to the database
			gamesdao.addProductImage(id, 1, scrapeImage(url));
						
			gamesdao.addRetailPrice(id, 1, 2,url, scrapePrice(url));
		}
	}
	
	public String scrapeUrlgame(String url,int id) throws Exception {

		Document doc = Jsoup.connect(url).get();
		Elements prods =doc.select(".sresult.lvresult.clearfix.li.shic");;
		Element a = prods.select(".vip").get(id).select("a").first();
		
		return a.attr("href");
	}

	
	public String scrapeImage(String href) throws Exception {
		
		Document doc = Jsoup.connect(href).get();
		Element img = doc.getElementById("icImg");
		
		return img.absUrl("src");
	}

	
	public String scrapePrice(String href) throws Exception {
		Document doc = Jsoup.connect(href).get();
		Elements prods = doc.select(".notranslate");
		Element span = prods.select("span").first();
		return span.text().replace("£", "");
	}

	
	public void scrapeGamesXBOXONE(String name, int id) throws Exception {
		//variable to store the index of the game found
		int foundTheGame=-1;
		
		//url of the website 
		String urlgamesfound = "https://www.ebay.co.uk/sch/i.html?_from=R40&_trksid=m570.l1313&_nkw="+ name + " XBOX ONE"+"&_sacat=0&_oac=1";
		
		//Download HTML document from website
		Document doc = Jsoup.connect(urlgamesfound).get();
		
		//Get all of the products on the page
		Elements prods = doc.select(".sresult.lvresult.clearfix.li.shic");
		
		// Work through the all products
		for(int i=0;i<prods.size();i++) {
			
			// Get the product name
			Element h2 = prods.select(".vip").get(i).select("a").first();
			
			//converts into lower case
			String namelow = h2.text().toLowerCase();
			
			//checks if the name contains specific words 
			if(namelow.contains(name.toLowerCase())&&namelow.contains("xbox one")) {
				//if the name found contain the those words save in the variable the foundTheGame the index value and break the loop
				foundTheGame=i;
				break;
			}
		}
		//if foundTheGame is different then -1
		if(foundTheGame!=-1) {
			
			String url=scrapeUrlgame(urlgamesfound,foundTheGame);
		
			// save to the database
			gamesdao.addProductImage(id, 2, scrapeImage(url));
						
			gamesdao.addRetailPrice(id, 2, 2,url, scrapePrice(url));
		}
	}
	
}
