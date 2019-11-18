package com.direnpramode.pricecomparison.entities;


import javax.persistence.*;

/** Represents a Format of the game.
Java annotation is used for the mapping. */
@Entity
@Table(name="format")
public class FormatTable {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "name")
    private String name;
    
    /** Empty constructor */
	public FormatTable() {
	}
	
	//Getters and setters
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
    
}
