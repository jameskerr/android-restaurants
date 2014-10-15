package com.example.restaurants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;

import com.example.restaurants.models.Restaurant;

public class RestaurantsManager extends Application {
	
	public List<Restaurant> RESTAURANTS = new ArrayList<Restaurant>();
	public Map<String, Restaurant> RESTAURANT_MAP = new HashMap<String, Restaurant>();
	
	public boolean view_phone = true;
	public boolean view_website = true;
	public boolean view_category = true;
	
	public void addRestaurant(Restaurant new_restaurant) {
		RESTAURANTS.add(new_restaurant);
		RESTAURANT_MAP.put(new_restaurant.name, new_restaurant);
	}
	
	public void addRestaurants(List<Restaurant> new_list) {
		for (int i = 0; i < new_list.size(); ++i) {
			addRestaurant(new_list.get(i));
		}
	}
	
	public List<Map<String,String>> convertRestaurantsToListItems() {
		List<Map<String,String>> list_items = new ArrayList<Map<String,String>>(RESTAURANTS.size());
		for (int i = 0; i < RESTAURANTS.size(); ++i) {
			Map<String, String> list_item = new HashMap<String, String>();
			list_item.put("name", RESTAURANTS.get(i).name);
			list_item.put("rating", String.valueOf(RESTAURANTS.get(i).rating));
			list_items.add(list_item);
		}
		return list_items;
	}
}
