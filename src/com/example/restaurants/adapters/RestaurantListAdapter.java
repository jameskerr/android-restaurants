package com.example.restaurants.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.QuickContactBadge;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurants.R;
import com.example.restaurants.models.Restaurant;

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> {
	private final Context context;
    private final ArrayList<Restaurant> list;
    
    public RestaurantListAdapter(Context context, ArrayList<Restaurant> list) {
    	super(context, R.layout.restaurat_list_item, list);	
    	this.context = context;
    	this.list = list;
    }
    
    @Override
    public View getView(int position, View convert_view, ViewGroup parent) {
    	// Get an inflater
    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    	// Get the row
    	View row_view = inflater.inflate(R.layout.restaurat_list_item, parent, false);
    	// Get the items in the row
    	QuickContactBadge logo = (QuickContactBadge) row_view.findViewById(R.id.list_restaurant_logo);
    	TextView name = (TextView) row_view.findViewById(R.id.list_restaurant_name);
    	RatingBar rating = (RatingBar) row_view.findViewById(R.id.list_restaurant_rating);
    	// Set the values
    	logo.setBackgroundResource(R.drawable.restaurant_logo); // TODO
    	name.setText(list.get(position).name);
    	rating.setRating(list.get(position).rating);
    	// return the row
    	return row_view;
    }
    
    
}
