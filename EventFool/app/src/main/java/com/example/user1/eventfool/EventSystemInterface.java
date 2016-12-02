package com.example.user1.eventfool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER1 on 27/11/2016.
 */

public interface EventSystemInterface {
    void saveEvent(Event event);

    void getAllEvents(EventArrayListCallback callback);

    void deleteEvent(Event event);

    interface EventArrayListCallback{
        void returnArrayList(final ArrayList<Event> notesArrayList);
    }
}
