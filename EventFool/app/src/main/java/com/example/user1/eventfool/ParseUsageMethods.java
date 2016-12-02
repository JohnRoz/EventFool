package com.example.user1.eventfool;

import android.os.AsyncTask;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static com.example.user1.eventfool.MainActivity.EVENT_STRING;

/**
 * Created by USER1 on 29/11/2016.
 */

public class ParseUsageMethods implements EventSystemInterface {

    ArrayList<Event> eventList;//The List of All Events to be returned in getAllEvent().

    @Override
    public void saveEvent(Event event) {
        event.saveInBackground();
    }

    @Override
    public void getAllEvents(EventArrayListCallback callback) {

        eventList = new ArrayList<>();

        final ParseQuery<Event> query = ParseQuery.getQuery(EVENT_STRING);
        /*query.findInBackground(new FindCallback<Event>() {
            public void done(List<Event> events, ParseException e) {
                if (e == null) {
                    eventList = (ArrayList<Event>) events;
                } else {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        });*/

        new AsyncTask<Void, Void, ArrayList<Event>>() {
            @Override
            protected ArrayList<Event> doInBackground(Void... params) {
                try {
                    eventList = (ArrayList<Event>) query.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage(), e);
                }
                return eventList;
            }
        }.execute();
    }

    @Override
    public void deleteEvent(Event event) {

    }
}
