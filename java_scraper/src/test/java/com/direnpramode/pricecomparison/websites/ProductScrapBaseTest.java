package com.direnpramode.pricecomparison.websites;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@DisplayName("Test ProductScrapBase")
public class ProductScrapBaseTest {

	@Test
	@DisplayName("Test Scrap the image of the game")
	public void testScrapImage() {
		ProductScrapBase base=new ProductScrapBase();
		String imgUrl="";
		try {
			imgUrl=base.scrapeImage("https://www.base.com/buy/product/lego-dc-super-villains-ps4/dgc-ldcvps4.htm");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(imgUrl,"https://www.base.com/images/main/LDCVPS4.jpg");
	}

}
