package com.example.user1.eventfool;

import android.os.AsyncTask;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;

import static com.example.user1.eventfool.MainActivity.EVENT_STRING;

/**
 * Created by USER1 on 29/11/2016.
 */

/**
 * This class implements the EventSystemInterface, in order to manage methods that will take care of Events.
 * This class is responsible for the methods that manage the interaction between the Events, and the Parse server.
 */
public class ParseUsageMethods implements EventSystemInterface {

    ArrayList<Event> eventList;// The List of All Events to be returned in getAllEvent().

    /**
     * This is used to save an Event in the Parse server.
     *
     * @param event The Event to be saved to the Parse server.
     */
    @Override
    public void saveEvent(Event event) {
        event.saveInBackground();
    }

    /**
     * This function retrieves all the objects in the "Events" class in the Parse server.
     *
     * @param callback This is a method you give to this function as a parameter.
     *                 That method will run after the list of all the objects in the "Events"
     *                 class in the Parse server is retrieved.
     *                 You implement that callback method from where you call this function.
     */
    @Override
    public void getAllEvents(final EventArrayListCallback callback) {

        // Initializing the eventList in case the list to be returned from the Parse server is empty.
        eventList = new ArrayList<>();

        // A Query (some kind of collection) that contains the list from the Parse server.
        final ParseQuery<Event> query = ParseQuery.getQuery(EVENT_STRING);

        // I used AsyncTask instead of query.findInBackground() because with findInBackground() I can't use a callback
        // method like here, and without it' the code won't manage to finish findInBackground() before
        // the function returns.
        new AsyncTask<Void, Void, ArrayList<Event>>() {
            @Override
            protected ArrayList<Event> doInBackground(Void... params) {
                try {
                    // Setting the eventList as the list return from the Parse server.
                    eventList = (ArrayList<Event>) query.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage(), e);
                    // NEVER KILL YOUR WITNESSES !
                }
                return eventList;
            }

            @Override // This runs when the background task is finished.
            protected void onPostExecute(ArrayList<Event> eventList) {
                // Run the callback method returnArrayList() with the eventList as a parameter.
                // The callback method is implemented where it was called from !
                callback.returnArrayList(eventList);
            }
        }.execute();
    }

    /**
     * This is used to delete an Event from the Parse server.
     *
     * @param event The Event to be deleted from the Parse server.
     */
    @Override
    public void deleteEvent(Event event) {
        event.deleteInBackground();
    }
}
