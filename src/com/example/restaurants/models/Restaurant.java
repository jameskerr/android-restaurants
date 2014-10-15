package com.example.restaurants.models;


public class Restaurant {

	public String name;
	public String phone;
	public String website;
	public float rating;
	public String category;
	public int id;
	
	
	
	public Restaurant(String name, String phone, String website, float rating, String category) {
		this.name = name;
		this.phone = phone;
		this.website = website;
		this.rating = rating;
		this.category = category;
	}
}
