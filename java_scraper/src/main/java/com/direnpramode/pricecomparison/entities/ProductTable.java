package com.direnpramode.pricecomparison.entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Product. Java annotation is used for the mapping.
 */
@Entity
@Table(name = "product")
public class ProductTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy="productId")
	private List<ProductImageTable> productID = new ArrayList<ProductImageTable>();
	
	@OneToMany(mappedBy="productid")
	private List<RetailerPriceTable> retailpriceID = new ArrayList<RetailerPriceTable>();
	/** Empty constructor */
	public ProductTable() {
	}

	// Getters and setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<ProductImageTable> getProductID() {
		return productID;
	}

	public void setProductID(List<ProductImageTable> productID) {
		this.productID = productID;
	}

	public List<RetailerPriceTable> getRetailpriceID() {
		return retailpriceID;
	}

	public void setRetailpriceID(List<RetailerPriceTable> retailpriceID) {
		this.retailpriceID = retailpriceID;
	}


}
