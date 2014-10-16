package com.example.restaurants.activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.restaurants.R;
import com.example.restaurants.RestaurantsManager;
import com.example.restaurants.models.Restaurant;
import com.example.restaurants.services.RestaurantLoader;

public class ListRestaurants extends Activity {
	
	RestaurantsManager manager;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_restaurants);

		manager = (RestaurantsManager) getApplication();
	}
	
	protected void onResume() {
		super.onResume();
		populateRestaurantListView();
	}
	
	public void populateRestaurantListView() {
		ListView restaurant_list_view = (ListView) findViewById(R.id.restaurant_list_view);
		SimpleAdapter restaurant_list_adapter = new SimpleAdapter(this,
																  manager.convertRestaurantsToListItems(),
																  android.R.layout.simple_expandable_list_item_2,
																  new String[] {"name", "rating"},
																  new int [] {android.R.id.text1, android.R.id.text2});
		restaurant_list_view.setAdapter(restaurant_list_adapter);
		
		restaurant_list_view.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {		
				Intent show_class_intent = new Intent(ListRestaurants.this, ViewRestaurant.class);
				show_class_intent.putExtra("name", manager.RESTAURANTS.get(position).name);
				startActivity(show_class_intent);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_restaurants, menu);
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
		case R.id.clear_restaurants:
			onClearRestaurantsSelected();
			return true;
		case R.id.load_restaurants:
			onLoadRestaurantsSelected();
			return true;
		case R.id.preferences:
			onPreferencesSelected();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	/**
	 * Navigation Helpers
	 */
	
	public void onOpenNewRestaurantSelected() {
		Intent intent = new Intent(this, NewRestaurant.class);
		startActivity(intent);
	}
	
	public void onClearRestaurantsSelected() {
		AlertDialog dialog = new AlertDialog.Builder(this)
			.setTitle("Delete all restaurants?")
			.setMessage("This cannot be undone.")
			.setNegativeButton("Cancel", null)
			.setPositiveButton("Yes, do it NAOUW!", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					deleteAllRestaurants();
				}
			})
			.show();
	}
	
	public void onLoadRestaurantsSelected() {
		RestaurantLoader loader = new RestaurantLoader();
		InputStream input_stream;
		
		try {
			input_stream = this.getAssets().open("restaurants.xml");
			manager.addRestaurants(loader.load(input_stream));
			manager.sortByRating();
			populateRestaurantListView();
		} catch (IOException e) {
			Toast.makeText(this, "Failed to load restaurants.", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		Toast.makeText(this, "Restaurants locked & loaded.", Toast.LENGTH_SHORT).show();
	}
	
	public void onPreferencesSelected() {
		Intent intent = new Intent(this, Preferences.class);
		startActivity(intent);
	}
	
	
	/**
	 *  Business Logic
	 */
	
	public void deleteAllRestaurants() {
		manager.RESTAURANTS.clear();
		manager.RESTAURANT_MAP.clear();
		populateRestaurantListView();
		Toast.makeText(ListRestaurants.this, "Restaurants Cleared", Toast.LENGTH_SHORT).show();
	}
}
