package com.example.restaurants.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.AssetManager;

import com.example.restaurants.models.Restaurant;

public class RestaurantLoader {

	private RestaurantParser restaurant_parser;
	
	public RestaurantLoader() {
		restaurant_parser = new RestaurantParser();
	}
	
	public List load(InputStream input_stream) {
		List restaurants = new ArrayList<Restaurant>();
		try {
			 restaurants = restaurant_parser.parse(input_stream);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return restaurants;
	}
}
