package com.example.restaurants.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.restaurants.R;
import com.example.restaurants.RestaurantsManager;

public class Preferences extends Activity {

	public RestaurantsManager manager;
	CheckBox view_phone;
	CheckBox view_website;
	CheckBox view_category;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
	}
	
	protected void onResume() {
		super.onResume();
		
		manager = (RestaurantsManager) getApplication();
		
		view_phone = (CheckBox) findViewById(R.id.view_phone);
		view_website = (CheckBox) findViewById(R.id.view_website);
		view_category = (CheckBox) findViewById(R.id.view_category);
		
		view_phone.setChecked(manager.view_phone);
		view_website.setChecked(manager.view_website);
		view_category.setChecked(manager.view_category);
	}
	
	public void savePreferences(View v) {
		view_phone = (CheckBox) findViewById(R.id.view_phone);
		view_website = (CheckBox) findViewById(R.id.view_website);
		view_category = (CheckBox) findViewById(R.id.view_category);
		manager = (RestaurantsManager) getApplication();
		manager.view_phone = view_phone.isChecked();
		manager.view_website = view_website.isChecked();
		manager.view_category = view_category.isChecked();
		Toast.makeText(this, "Preferences Updated", Toast.LENGTH_LONG);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferences, menu);
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
