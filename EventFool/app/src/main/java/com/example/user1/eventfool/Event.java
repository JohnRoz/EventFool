package com.example.user1.eventfool;

/**
 * Created by USER1 on 27/11/2016.
 */

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Date;

/**
 * This is an Object that extends ParseObject.
 * This object represents an Event.
 */
@ParseClassName("Events")
public class Event extends ParseObject implements Serializable {

    // KEYS:
    private final String TITLE_KEY = "TITLE_KEY";
    private final String TEXT_KEY = "TEXT_KEY";
    private final String DATE_KEY = "DATE_KEY";

    public Event() {
    }//Default constructor is required.


    /**
     * An override to the toString() method.
     *
     * @return If the text of the Event is empty (""), return only the title.
     * If it isn't, return the title, then a colon and the text afterwards.
     */
    @Override
    public String toString() {
        if (!getText().equals(""))
            return getTitle() + ": " + getText();
        else
            return getTitle();
    }

    //Getters & Setters
    public String getTitle() {
        return getString(TITLE_KEY);
    }

    public void setTitle(String title) {
        put(TITLE_KEY, title);
    }

    public String getText() {
        return getString(TEXT_KEY);
    }

    public void setText(String text) {
        put(TEXT_KEY, text);
    }

    public Date getDate() {
        return getDate(DATE_KEY);
    }

    public void setDate(Date date) {
        put(DATE_KEY, date);
    }
}
