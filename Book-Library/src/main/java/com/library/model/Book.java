package com.library.model;

public class Book {

	private String b_id;
	private String b_name;
	private String author;
	private int total_copies;
	private int available_copies;
	
	
	public String getB_id() {
		return b_id;
	}
	public void setB_id(String b_id) {
		this.b_id = b_id;
	}
	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getTotal_copies() {
		return total_copies;
	}
	public void setTotal_copies(int total_copies) {
		this.total_copies = total_copies;
	}
	public int getAvailable_copies() {
		return available_copies;
	}
	public void setAvailable_copies(int available_copies) {
		this.available_copies = available_copies;
	}

}
