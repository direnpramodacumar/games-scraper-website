package com.direnpramode.pricecomparison.websites;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** Scrape the video games from the website Simply */
public class ProductScrapSimply extends WebScraper {

	/** Empty constructor */
	public ProductScrapSimply() {

	}

	
	public void scrapeGamesPS4(String name, int id) throws Exception {

		//url of the website 
		String urlgamesfound = "https://www.simplygames.com/search?keywords=" + name + " PS4";
	
		// Download HTML document from website
		Document doc = Jsoup.connect(urlgamesfound).get();
		
		// Get all of the products on the page
		Elements prods = doc.select(".list_item");
		String price = "";
		String url = "";

		// check if the there are results or not
		if (prods.size() >= 1) {
			
			// Work through the all products
			for (int i = 0; i < prods.size(); i++) {
				
				// Get the productName
				String gname= prods.get(i).select(".product_name").text();

				// Get the product price
				Element span = prods.get(i).select("span").first();
				price = span.text().replace("£", "");
				
				// check if the name not contains word "and" and price of the product is less then 60
				if (!gname.contains("and")&&Double.parseDouble(price) < 60.00) {
					
					// scrap the url of the game and stores it
					url = scrapeUrlgame(urlgamesfound, i);
					break;
				}
			}

			// save to the database
			gamesdao.addProductImage(id, 1, scrapeImage(url));
						
			gamesdao.addRetailPrice(id, 1, 1,url, scrapePrice(url));
		}
	}

	
	public String scrapeUrlgame(String url, int i) throws Exception {

		Document doc = Jsoup.connect(url).get();

		Elements prods = doc.select(".list_item");

		Elements productLink = prods.get(i).select("a");
		String urll = "https://www.simplygames.com" + productLink.attr("href");

		return urll;

	}

	
	public String scrapeImage(String href) throws Exception {
		
		Document doc = Jsoup.connect(href).get();
		Elements prods = doc.select(".product_image");
		Element img = prods.select("img").first();
		
		return img.absUrl("src");
	}

	
	public String scrapePrice(String href) throws Exception {
		
		Document doc = Jsoup.connect(href).get();
		Elements prods = doc.select(".price_point");
		Element span = prods.select("span").first();
		String price = span.text().replace("£", "");
		return price;
	}

	
	public void scrapeGamesXBOXONE(String name, int id) throws Exception {
		
		//url of the website 
		String urlgamesfound = "https://www.simplygames.com/search?keywords=" + name + " XBOX ONE";
		
		// Download HTML document from website
		Document doc = Jsoup.connect(urlgamesfound).get();
		
		// Get all of the products on the page
		Elements prods = doc.select(".list_item");
		String price = "";
		String url = "";
		

		// check if the there are results or not
		if (prods.size() >= 1) { 
			
			// Work through the all products
			for (int i = 0; i < prods.size(); i++) {
				
				// Get the productName
				String gname= prods.get(i).select(".product_name").text();
				
				// Get the product price
				Element span = prods.get(i).select("span").first();
				price = span.text().replace("£", "");
				
				// check if the name not contains word "and" and price of the product is less then 60
				if (!gname.contains("and")&&Double.parseDouble(price) < 60.00) {
					
					// scrap the url of the game and stores it
					url = scrapeUrlgame(urlgamesfound, i);
					break;
				}
			}
			// save to the database
			// save to the database
			gamesdao.addProductImage(id, 2, scrapeImage(url));
						
			gamesdao.addRetailPrice(id, 2, 1,url, scrapePrice(url));
		}
	}
}