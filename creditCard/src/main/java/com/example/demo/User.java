package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import org.springframework.beans.factory.annotation.Autowired;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
@Entity
@Table(name = "User")
//@NoArgsConstructor
//@AllArgsConstructor
public class User {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue
	@Column(name="id")
	private int id;
	@Column(name="address")
	private String address;
	@Column(name="name")
	private String name;
	@Column(name="phoneNumber")
	private int phoneNumber;
	private String cardNum;
	public User() {
		
	}
	public User(String address, String name, int phoneNumber) {
		super();
//		this.id = id;
		this.address = address;
		this.name = name;
		this.phoneNumber = phoneNumber;
//		this.setCreditcard(creditcard);
	}
//	public int getCreditcard() {
//		return creditcard;
//	}
//	public void setCreditcard(int creditcard2) {
//		this.creditcard = creditcard2;
//	}
	
	public int getId() {
		return id;
	}
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", address=" + address + ", name=" + name + ", phoneNumber=" + phoneNumber
				+ ", cardNum=" + cardNum + "]";
	}
	
//	@Autowired
//	private CreditCard craditCard;
//	private String creditcard;
//	private int creditAvailable;
//	private int cashAvaialble;
}
