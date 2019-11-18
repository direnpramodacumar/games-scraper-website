package com.direnpramode.pricecomparison.entities;

import javax.persistence.*;

/**
 * Represents a Retailer Price. Java annotation is used for the mapping.
 */
@Entity
@Table(name = "retailerprice")
public class RetailerPriceTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "price")
	private double price;

	@Column(name = "url")
	private String url;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private ProductTable productid;

	@Column(name = "retailer_id")
	private int retailId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "productimage_id")
	private ProductImageTable productimageid;
	
	
	/** Empty constructor */
	public RetailerPriceTable() {

	}
	//Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public ProductTable getProductid() {
		return productid;
	}

	public void setProductid(ProductTable productid) {
		this.productid = productid;
	}

	public int getRetailId() {
		return retailId;
	}

	public void setRetailId(int retailId) {
		this.retailId = retailId;
	}
	public ProductImageTable getProductimageid() {
		return productimageid;
	}

	public void setProductimageid(ProductImageTable productimageid) {
		this.productimageid = productimageid;
	}

}
