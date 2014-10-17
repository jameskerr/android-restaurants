package com.example.restaurants.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.restaurants.R;
import com.example.restaurants.RestaurantsManager;
import com.example.restaurants.models.Restaurant;

public class ViewRestaurant extends Activity {
	
	TextView show_name;
	TextView show_phone;
	TextView show_website;
	TextView show_category;
	RatingBar show_rating;
	
	RestaurantsManager manager;
	String name;
	Restaurant restaurant;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_restaurant);
		
		// Get the name from sending intent
		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		// Get the restaurant with the name
		manager = (RestaurantsManager) getApplication();
		restaurant = manager.findByName(name);
	}
	
	protected void onResume() {
		super.onResume();
		this.onCreate(null);
		
		manager = (RestaurantsManager) getApplication();
		
		show_name = (TextView) findViewById(R.id.show_name);
		show_phone = (TextView) findViewById(R.id.show_phone);
		show_website = (TextView) findViewById(R.id.show_website);
		show_category = (TextView) findViewById(R.id.show_category);
		show_rating = (RatingBar) findViewById(R.id.show_rating);
		
		show_name.setText(restaurant.name);
		show_rating.setRating(restaurant.rating);
		if (manager.view_phone)    show(show_phone, restaurant.phone);       else hide(findViewById(R.id.show_phone_row));
		if (manager.view_website)  show(show_website, restaurant.website);   else hide(findViewById(R.id.show_website_row));
		if (manager.view_category) show(show_category, restaurant.category); else hide(findViewById(R.id.show_category_row));
		
		// Linkify stuff
		Linkify.addLinks(show_phone, Linkify.PHONE_NUMBERS);
		Linkify.addLinks(show_website, Linkify.WEB_URLS);
	}
	
	protected void onDestroy() {
		super.onDestroy();
		restaurant.rating = show_rating.getRating();
	}
	
	private void show(TextView view, String text) {
		view.setVisibility(View.VISIBLE);
		view.setText(text);
	}
	
	private void hide(View view) {
		view.setVisibility(View.GONE);
	}
	
	public void saveRating(View v) {
		restaurant.rating = show_rating.getRating();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_restaurant, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		switch(item.getItemId()) {
		case R.id.new_restaurant:
			onOpenNewRestaurantSelected();
			return true;
		case R.id.preferences:
			onPreferencesSelected();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void onPreferencesSelected() {
		Intent intent = new Intent(this, Preferences.class);
		startActivity(intent);
	}
	
	public void onOpenNewRestaurantSelected() {
		Intent intent = new Intent(this, NewRestaurant.class);
		startActivity(intent);
	}
}
