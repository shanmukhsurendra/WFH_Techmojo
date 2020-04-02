package com.example.demo;

public class Book {

	private String title;
	private String publications;
	private String author;
	public Book(String title, String publications, String author) {
		super();
		this.title = title;
		this.publications = publications;
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public String getPublications() {
		return publications;
	}
	public String getAuthor() {
		return author;
	}
	@Override
	public String toString() {
		return "Book [title=" + title + ", publications=" + publications + ", author=" + author + "]";
	}
	
	
}
