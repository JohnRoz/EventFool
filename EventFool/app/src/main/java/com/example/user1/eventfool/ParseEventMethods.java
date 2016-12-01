package com.example.user1.eventfool;

import java.util.List;

/**
 * Created by USER1 on 01/12/2016.
 */
public class ParseEventMethods implements EventSystemInterface {

    @Override
    public void saveEvent(Event event) {
        event.saveInBackground();
    }

    @Override
    public List getAllEvent() {
        return null;
    }

    @Override
    public void deleteEvent(Event event) {

    }
}
