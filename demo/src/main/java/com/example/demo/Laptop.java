package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Laptop {
	
	private int lid;
	private String lName;
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	@Override
	public String toString() {
		return "Laptop [lid=" + lid + ", lName=" + lName + "]";
	}
	public void compile() {
		System.out.println(" here the laptop is compiling");
	}
}
