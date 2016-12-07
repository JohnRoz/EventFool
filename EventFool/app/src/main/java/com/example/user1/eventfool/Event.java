package com.example.user1.eventfool;

/**
 * Created by USER1 on 27/11/2016.
 */

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import static com.example.user1.eventfool.ManageEventsActivity.SPLIT_DATE_BY;

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
    }// A default constructor is required.


    /**
     * An override to the toString() method.
     *
     * @return If the text of the Event is empty (""), return only the title.
     * If it isn't, return the title, then a colon and the text afterwards.
     */
    @Override
    public String toString() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(getDate());

        String year = cal.get(Calendar.YEAR) + "";
        String month = cal.get(Calendar.MONTH) + 1 + "";// In Calender, the month values are between 0 and 11.
        String day = cal.get(Calendar.DAY_OF_MONTH) + "";

        String date = day + SPLIT_DATE_BY + month + SPLIT_DATE_BY + year;

        String show = date + "\n" + "\n" + getTitle();

        if (!getText().equals(""))
            return show + ": " + getText();

        return show;
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
