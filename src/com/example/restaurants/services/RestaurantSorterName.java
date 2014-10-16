package com.example.restaurants.services;

import java.util.Comparator;

import com.example.restaurants.models.Restaurant;

public class RestaurantSorterName implements Comparator<Restaurant> {
    public int compare(Restaurant a, Restaurant b) {
        return a.name.compareTo(b.name);
    }
}
