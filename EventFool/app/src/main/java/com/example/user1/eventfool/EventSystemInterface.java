package com.example.user1.eventfool;

/**
 * Created by USER1 on 27/11/2016.
 */

public interface EventSystemInterface {
    void createEvent(Event event);
    void updateEvent(Event event);
    Event readEvent();
    void deleteEvent(Event event);
}
