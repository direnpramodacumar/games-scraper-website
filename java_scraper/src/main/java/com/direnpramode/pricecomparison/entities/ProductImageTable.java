package com.direnpramode.pricecomparison.entities;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Represents a Product Image. Java annotation is used for the mapping.
 */
@Entity
@Table(name = "productimage")
public class ProductImageTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "image")
	private String image;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private ProductTable productId;

	@Column(name = "format_id")
	private int formatTable;

	@OneToMany(mappedBy="productimageid")
	private List<RetailerPriceTable> retailpriceID = new ArrayList<RetailerPriceTable>();
	
	
	/** Empty constructor */
	public ProductImageTable() {

	}
	
	//Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	public ProductTable getProductId() {
		return productId;
	}

	public void setProductId(ProductTable productId) {
		this.productId = productId;
	}

	public int getFormatTable() {
		return formatTable;
	}

	public void setFormatTable(int formatTable) {
		this.formatTable = formatTable;
	}

	public List<RetailerPriceTable> getRetailpriceID() {
		return retailpriceID;
	}

	public void setRetailpriceID(List<RetailerPriceTable> retailpriceID) {
		this.retailpriceID = retailpriceID;
	}


	
	
}
