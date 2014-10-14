package com.example.restaurants.activities;

import com.example.restaurants.R;
import com.example.restaurants.R.id;
import com.example.restaurants.R.layout;
import com.example.restaurants.R.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ListRestaurants extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_restaurants);
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
		Toast.makeText(ListRestaurants.this, "Restaurants Cleared", Toast.LENGTH_SHORT).show();
	}
}
