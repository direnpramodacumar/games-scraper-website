package com.direnpramode.pricecomparison;

import com.direnpramode.pricecomparison.websites.ProductScrapArgos;
import com.direnpramode.pricecomparison.websites.ProductScrapBase;
import com.direnpramode.pricecomparison.websites.ProductScrapEbay;
import com.direnpramode.pricecomparison.websites.ProductScrapOnBuy;
import com.direnpramode.pricecomparison.websites.ProductScrapSimply;
import com.direnpramode.pricecomparison.websites.WebScraper;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Manage the dependencies between different classes
 * @author Diren Pramodacumar
 */
@Configuration
public class AppConfig {
	
	SessionFactory sessionFactory;

	@Bean
	public GamesDao gamesdao() {
		GamesDao gamesdao = new GamesDao();
		gamesdao.setSessionFactory(sessionFactory());
		return gamesdao;
	}
	@Bean
	public NameProductScraper namescraper() {
		NameProductScraper namescraper = new NameProductScraper();
		namescraper.setGamesdao(gamesdao());
		return namescraper;
	}

	@Bean
	public ProductScrapSimply psimply() {
		ProductScrapSimply psimply = new ProductScrapSimply();
		psimply.setGamesDao(gamesdao());
		psimply.setSleepTime(800);
		return psimply;
	}

	@Bean
	public ProductScrapEbay pebay() {
		ProductScrapEbay pebay = new ProductScrapEbay();
		pebay.setGamesDao(gamesdao());
		pebay.setSleepTime(2000);
		return pebay;
	}

	@Bean
	public ProductScrapOnBuy ponbuy() {
		ProductScrapOnBuy ponbuy = new ProductScrapOnBuy();
		ponbuy.setGamesDao(gamesdao());
		ponbuy.setSleepTime(3000);
		return ponbuy;
	}

	@Bean
	public ProductScrapBase pbase() {
		ProductScrapBase pbase = new ProductScrapBase();
		pbase.setGamesDao(gamesdao());
		pbase.setSleepTime(3000);
		return pbase;
	}

	@Bean
	public ProductScrapArgos pargos() {
		ProductScrapArgos pargos = new ProductScrapArgos();
		pargos.setGamesDao(gamesdao());
		pargos.setSleepTime(2000);
		return pargos;
	}


	@Bean
	public ScraperManager scraper() {
		ScraperManager scraper = new ScraperManager();
		List<WebScraper>scraperList = new ArrayList<WebScraper>();
		scraperList.add(psimply());	
		scraperList.add(pebay());	
		scraperList.add(ponbuy());	
		scraperList.add(pbase());	
		scraperList.add(pargos());	
		scraper.setScraperList(scraperList);

		return scraper;
	}
	
	@Bean
	public SessionFactory sessionFactory() {
		// Build session factory once only
		if (sessionFactory == null) {
			try {
				// Create a builder for the standard service registry
				StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

				// Load configuration from hibernate configuration file.
				// Here we are using a configuration file that specifies Java annotations.
				standardServiceRegistryBuilder.configure("resources/hibernate-annotations.cfg.xml");

				// Create the registry that will be used to build the session factory
				StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
				try {
					// Create the session factory - this is the goal of the init method.
					sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
				} catch (Exception e) {
					/*
					 * The registry would be destroyed by the SessionFactory, but we had trouble
					 * building the SessionFactory, so destroy it manually
					 */
					System.err.println("Session Factory build failed.");
					e.printStackTrace();
					StandardServiceRegistryBuilder.destroy(registry);
				}

				// Ouput result
				System.out.println("Session factory built.");

			} catch (Throwable ex) {
				// Make sure you log the exception, as it might be swallowed
				System.err.println("SessionFactory creation failed. " + ex);
				ex.printStackTrace();
			}
		}

		return sessionFactory;
	}

}
