package com.example.restaurants.services;

import java.util.Comparator;

import com.example.restaurants.models.Restaurant;

public class RestaurantSorterRating implements Comparator<Restaurant> {
    public int compare(Restaurant a, Restaurant b) {
        if (a.rating < b.rating) return 1;
        if (a.rating > b.rating) return -1;
        return 0;
    }
}

