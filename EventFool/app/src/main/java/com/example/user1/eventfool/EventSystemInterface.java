package com.example.user1.eventfool;

import java.util.ArrayList;

/**
 * Created by USER1 on 27/11/2016.
 */

/**
 * This is an interface to be implemented by a class to manage methods that will take care of Events.
 */
public interface EventSystemInterface {
    void saveEvent(Event event);

    void getAllEvents(EventArrayListCallback callback);

    void deleteEvent(Event event);

    // A callback interface to retrieve the list of Events from the Parse server.
    interface EventArrayListCallback {
        void returnArrayList(final ArrayList<Event> eventsArrayList);
    }
}
