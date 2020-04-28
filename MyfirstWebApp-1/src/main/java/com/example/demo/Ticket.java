package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Ticket")
public class Ticket {
	@Id
	@GeneratedValue
	private int id;
	private String catagory;
	private double price;
}
