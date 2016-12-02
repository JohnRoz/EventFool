package com.example.user1.eventfool;

import java.util.List;

/**
 * Created by USER1 on 27/11/2016.
 */

public interface EventSystemInterface {
    void saveEvent(Event event);

    List getAllEvent();

    void deleteEvent(Event event);
}
