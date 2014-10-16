package com.example.restaurants.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class FiveStarReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
	    String name = intent.getStringExtra("name");
	    Toast.makeText(context, "Whoa! " + name + " just got five stars!", Toast.LENGTH_SHORT).show();
	}
}
