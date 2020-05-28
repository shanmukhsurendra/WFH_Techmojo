package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class CreditCard {
	@Id
	@GeneratedValue
	private int id;
	private int cashLimit;
	private int creditLimit;
	private String expiryDate;
	private String name;
	private String cardNum;
	private int pastCashDue;
	private int pastCreditDue;
	private int currentCashDue;
	private int currentCreditDue;
	private String cardStatus;
	private int minimumDue;
	private int lateCharges;
//	private String address;
//	private int phoneNumber;
	
	public String getName() {
		return name;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public int getMinimumDue() {
		return minimumDue;
	}
	public void setMinimumDue(int minimumDue) {
		this.minimumDue = minimumDue;
	}
	public int getLateCharges() {
		return lateCharges;
	}
	public void setLateCharges(int lateCharges) {
		this.lateCharges = lateCharges;
	}
	public int getPastCreditDue() {
		return pastCreditDue;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCashLimit() {
		return cashLimit;
	}
	public void setCashLimit(int cashLimit) {
		this.cashLimit = cashLimit;
	}
	public int getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(int creditLimit) {
		this.creditLimit = creditLimit;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public int getPastCashDue() {
		return pastCashDue;
	}
	public void setPastCashDue(int pastCashDue) {
		this.pastCashDue = pastCashDue;
	}
	public int getPastCreditdDue() {
		return pastCreditDue;
	}
	public void setPastCreditDue(int pastCardDue) {
		this.pastCreditDue = pastCardDue;
	}
	public int getCurrentCashDue() {
		return currentCashDue;
	}
	public void setCurrentCashDue(int currentCashDue) {
		this.currentCashDue = currentCashDue;
	}
	public int getCurrentCreditDue() {
		return currentCreditDue;
	}
	public void setCurrentCreditDue(int currentCreditDue) {
		this.currentCreditDue = currentCreditDue;
	}
	

}
