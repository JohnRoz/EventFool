package com.example.user1.eventfool;

/**
 * Created by USER1 on 27/11/2016.
 */

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@ParseClassName("Event")
public class Event extends ParseObject implements Serializable {


    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String TEXT_KEY = "TITLE_KEY";
    public static final String DATE_KEY = "DATE_KEY";

    public Event() {
    }//Default constructor is required.

    public Event(String title, String text, Date date) {
        super();
        setTitle(title);
        setText(text);
        setDate(date);
    }

    public Event(String title, String text) {
        super();
        setTitle(title);
        setText(text);
        setDatetoNow();
    }

    @Override
    public String toString() {
        return getText();
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

    public void setDatetoNow() {
        Calendar cal = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        Date date = cal.getTime();
        put(DATE_KEY, date);
    }
}
