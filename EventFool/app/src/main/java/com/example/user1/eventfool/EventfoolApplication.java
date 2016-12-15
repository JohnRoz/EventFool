package com.example.user1.eventfool;

import android.app.Application;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by USER1 on 15/12/2016.
 */
public class EventfoolApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initParseStuff();
    }

    /**
     * This function registers the Event class as a subclass of ParseObject
     * and initializes the Parse connection for this application.
     */
    private void initParseStuff() {
        ParseObject.registerSubclass(Event.class);

        try {
            Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId("iioJkrW7NrDjXrqFQcxJCm7HhkFIrEEbMd0vmgPp")
                    .clientKey("PvzhaxHueoNlOJYXtf5JvlUotMZW5L7XJGRaiTEI")
                    .server("https://parseapi.back4app.com/").build());
        } catch (IllegalStateException ex) {// NEVER KILL YOUR WITNESSES !
            Toast.makeText(getApplicationContext(), "CHECK PARSE initialize FOR EXCEPTIONS", Toast.LENGTH_SHORT).show();
        }
    }
}
