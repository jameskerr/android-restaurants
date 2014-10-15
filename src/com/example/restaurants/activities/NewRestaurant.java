package com.example.restaurants.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.restaurants.R;
import com.example.restaurants.RestaurantsManager;
import com.example.restaurants.models.Restaurant;

public class NewRestaurant extends Activity {

	RestaurantsManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_restaurant);
		
		// Populate the category spinner
		populateCategories();
	}
	
	public void saveRestaurant(View view) {
		EditText name = (EditText) findViewById(R.id.edit_name);
		EditText phone = (EditText) findViewById(R.id.edit_phone);
		EditText website = (EditText) findViewById(R.id.edit_website);
		RatingBar rating = (RatingBar) findViewById(R.id.edit_rating);
		Spinner category = (Spinner) findViewById(R.id.edit_category);
		
		manager = (RestaurantsManager) getApplication();
		manager.addRestaurant(new Restaurant(
				name.getText().toString(),
				phone.getText().toString(),
				website.getText().toString(),
				rating.getRating(),
				category.getSelectedItem().toString()
				));
		Toast.makeText(this, "New Restaurant Added", Toast.LENGTH_SHORT).show();
		finish();
	}
	
	public void clearForm(View view) {
		
	}
	
	public void populateCategories() {
		Spinner categories = (Spinner) findViewById(R.id.edit_category);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(NewRestaurant.this, 
				                                                             R.array.categories_array, 
				                                                             android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categories.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_restaurant, menu);
		return true;	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
