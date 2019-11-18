package com.direnpramode.pricecomparison.entities;

import javax.persistence.*;

/** Represents a Retailer.
Java annotation is used for the mapping. */
@Entity
@Table(name="retailer")
public class RetailerTable {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "name")
    private String name;
    
    /** Empty constructor */
	public RetailerTable() {
	}
	//Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
}
