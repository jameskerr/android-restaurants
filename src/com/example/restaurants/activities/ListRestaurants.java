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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurants.R;
import com.example.restaurants.RestaurantsManager;
import com.example.restaurants.adapters.RestaurantListAdapter;
import com.example.restaurants.models.Restaurant;
import com.example.restaurants.services.RestaurantLoader;

public class ListRestaurants extends Activity implements OnItemSelectedListener, OnItemClickListener {
	
	RestaurantsManager manager;
	Spinner sort_by;
	TextView sort_by_label;
	TextView no_restaurants;
	ListView restaurant_list;
	static public RestaurantListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_restaurants);
		manager = (RestaurantsManager) getApplication();
		sort_by = (Spinner) findViewById(R.id.sort_by);
		sort_by_label = (TextView) findViewById(R.id.sort_by_label);
		no_restaurants = (TextView) findViewById(R.id.no_restaurants);
		sort_by.setOnItemSelectedListener(this);
		restaurant_list = (ListView) findViewById(R.id.restaurant_list);
		adapter = new RestaurantListAdapter(this, ((ArrayList<Restaurant>) manager.RESTAURANTS));	
		restaurant_list.setAdapter(adapter);
		restaurant_list.setOnItemClickListener(this);
		adapter.notifyDataSetChanged();
	}
	
	protected void onResume() {
		super.onResume();
		checkListState();
	}
	
	public void checkListState() {
		if (manager.RESTAURANTS.isEmpty()) {
			setEmptyState();
		}
		else {
			setNonEmptyState();
		}
	}
	
	public void setEmptyState() {
		no_restaurants.setVisibility(View.VISIBLE);
		sort_by.setVisibility(View.INVISIBLE);
		sort_by_label.setVisibility(View.INVISIBLE);
	}
	
	public void setNonEmptyState() {
		no_restaurants.setVisibility(View.INVISIBLE);
		sort_by.setVisibility(View.VISIBLE);
		sort_by_label.setVisibility(View.VISIBLE);
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
	
	
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String selection = (String) parent.getItemAtPosition(pos);
        if (selection.equals("Name")) {
        	manager.sortByName();
        	adapter.notifyDataSetChanged();
        } else if (selection.equals("Rating")) {
        	manager.sortByRating();
        	adapter.notifyDataSetChanged();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
       
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
					adapter.notifyDataSetChanged();
					setEmptyState();
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
			checkListState();
			adapter.notifyDataSetChanged();
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
		adapter.notifyDataSetChanged();
		Toast.makeText(ListRestaurants.this, "Restaurants Cleared", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent show_class_intent = new Intent(ListRestaurants.this, ViewRestaurant.class);
		show_class_intent.putExtra("name", manager.RESTAURANTS.get(position).name);
		startActivity(show_class_intent);
	}
}
