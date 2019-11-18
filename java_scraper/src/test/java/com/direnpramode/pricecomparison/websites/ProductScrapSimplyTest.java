package com.direnpramode.pricecomparison.websites;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test ProductScrapSimply")
public class ProductScrapSimplyTest {

	@Test
	@DisplayName("Test Scrap the price of the game")
	public void testScrapPrice() {
		ProductScrapSimply simply=new ProductScrapSimply();
		String gamePrice="";
		try {
			gamePrice=simply.scrapePrice("https://www.simplygames.com/p/fifa-19-ps4");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//expect the same price
		assertEquals(gamePrice,"39.85");
	}
	
	@Test
	@DisplayName("Test Scrap the url of the game")
	public void testScrapUrl() {
		ProductScrapSimply simply=new ProductScrapSimply();
		String url="";
		try {
			url=simply.scrapeUrlgame("https://www.simplygames.com/search?keywords=Fifa+19+PS4", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//expect the same url
		assertEquals(url,"https://www.simplygames.com/p/fifa-19-ps4");
	}
	
	
	
}
