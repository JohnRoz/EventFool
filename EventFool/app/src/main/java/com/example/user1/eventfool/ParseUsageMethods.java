package com.example.user1.eventfool;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER1 on 29/11/2016.
 */

public class ParseUsageMethods implements EventSystemInterface {

    @Override
    public void saveEvent(Event event) {
        event.saveInBackground();
    }

    @Override
    public List getAllEvent() {

        List<Event> eventList = new ArrayList<>();

        ParseQuery<Event> query = ParseQuery.getQuery("Event");
        query.findInBackground(new FindCallback<Event>() {
            public void done(List<Event> events, ParseException e) {
                if (e == null) {

                } else {
                    throw new RuntimeException();
                }
            }
        });
        return eventList;
    }

    @Override
    public void deleteEvent(Event event) {

    }
}
