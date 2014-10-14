package com.example.restaurants.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.example.restaurants.models.Restaurant;

public class RestaurantParser {
	
	private static final String namespace = null;
	
	
	// Constructor
	public RestaurantParser() {}
	
	// Parse Function
	public List parse(InputStream input_stream) throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(input_stream, null);
			parser.nextTag();
			return readFeed(parser);
		} finally {
			input_stream.close();
		}
	}
	
	// Read feed function
	private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		List restaurants = new ArrayList<Restaurant>();
		
		parser.require(XmlPullParser.START_TAG, namespace, "restaurants");
		while(parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.END_TAG) continue;
			String field_name = parser.getName();
			if (field_name.equals("restaurant")) {
				restaurants.add(readRestaurant(parser));
			} else {
				skip(parser);
			}
		}
		return restaurants;
	}
	
	private Restaurant readRestaurant(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, namespace, "restaurant");
		
		String name = null;
		String phone = null;
		String website = null;
		int rating = 0;
		String category = null;
		
		while(parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) continue;
			String field_name = parser.getName();
			
			if (field_name.equals("name")) {
				name = readString(parser, "name");
			} else if (field_name.equals("phone")) {
				phone = readString(parser, "phone");
			} else if (field_name.equals("website")) {
				website = readString(parser, "website");
			} else if (field_name.equals("rating")) {
				rating = readInt(parser, "rating");
			} else if (field_name.equals("category")) {
				category = readString(parser, "category");
			}
		}
		
		return new Restaurant(name, phone, website, rating ,category);
	}
	
	private String readString(XmlPullParser parser, String field_name) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, namespace, field_name);
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	    	result = parser.getText();
	    	parser.nextTag();
	    }
	    parser.require(XmlPullParser.END_TAG, namespace, field_name);
	    return result;
	}
	
	private int readInt(XmlPullParser parser, String field_name) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, namespace, field_name);
	    int result = 0;
	    if (parser.next() == XmlPullParser.TEXT) {
	    	result = Integer.parseInt(parser.getText());
	    	parser.nextTag();
	    }
	    parser.require(XmlPullParser.END_TAG, namespace, field_name);
	    return result;
	}
	
	private void skip(XmlPullParser parser) throws IOException, XmlPullParserException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
		int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	}
}
