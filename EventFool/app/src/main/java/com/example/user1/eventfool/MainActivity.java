package com.example.user1.eventfool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This is the Main Activity.
 */
public class MainActivity extends AppCompatActivity {

    // CONSTANTS:
    static final int CREATE_EVENT_FOR_RESULT = 100; // The requestCode for onActivityResult().
    static final String EVENT_STRING = "Events";
    static final String EVENT_LIST_POSITION_STRING = "position"; // A String for the Intents to pass Date objects.

    // ACTIONS:
    static final String ACTION_CREATE_EVENT = "com.example.user1.eventfool.action.CREATE_EVENT";
    static final String ACTION_EDIT_EVENT = "com.example.user1.eventfool.action.EDIT_EVENT";

    // Binding views using ButterKnife:
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.addEvent_FAB)
    FloatingActionButton addEvent;

    private ArrayList<Event> eventList;// The list of Events.
    private ArrayAdapter<Event> adapter;// The ArrayAdapter to contain the list of the Events and present it in the ListView.
    private ParseUsageMethods parseUsageMethods;// An instance of the class that holds the methods to handle the Parse actions.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);// ButterKnife is Awesome !

        parseUsageMethods = new ParseUsageMethods();

        initParseStuff();

        initEventList();

        initNewEventButton();

        initShowEvent();

        initDeleteEvent();
    }

    /**
     * This runs when an Item from the ListView is pressed.
     * This starts ShowEventActivity.
     * The Event that was pressed is passed to ShowEventActivity as an extra.
     */
    private void initShowEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Sending an Intent holding the position of the Event in the eventList.
                // Using that position the ShowEventActivity will be able to get the REAL Event from the server
                // and NOT the Serialized Event.
                // Serialized Events DON"T hold the values of their keys as same as the real Events from the server.
                Intent intent = new Intent(MainActivity.this, ShowEventActivity.class);
                intent.putExtra(EVENT_LIST_POSITION_STRING, position);

                startActivity(intent);
            }
        });
    }

    /**
     * This runs when an Item from the ListView is pressed and held.
     * This activates a Snackbar to ask the user if he wants to delete the Event.
     * If the user chooses to delete the Event, this function will delete it and remove it from the adapter.
     */
    private void initDeleteEvent() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // The Event that was pressed.
                final Event event = adapter.getItem(position);

                // Asking the user if he's SURE he wants to delete the event.
                Snackbar.make(view, "Are you SURE you want to delete the Event ?", Snackbar.LENGTH_LONG)
                        .setAction("YES", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // If the user pressed "YES" in the Snackbar, the Event will be deleted.
                                parseUsageMethods.deleteEvent(event);
                                adapter.remove(event);
                                Toast.makeText(getApplicationContext(), "Event is being deleted...", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                return true;
            }
        });
    }

    /**
     * This runs after the addEvent button was pressed.
     * This starts ManagerEventsActivity for a result.
     */
    private void initNewEventButton() {
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ManageEventsActivity.class);
                intent.setAction(ACTION_CREATE_EVENT);
                startActivityForResult(intent, CREATE_EVENT_FOR_RESULT);
            }
        });
    }

    /**
     * This initiates the eventList, the ArrayAdapter, and sets the adapter as the adapter of the ListView.
     * This function contains a callback method to run after the list of Events stored in the Parse server returns, so that
     * the eventList would set as the list of Events on the Parse server.
     */
    private void initEventList() {
        parseUsageMethods.getAllEvents(new EventSystemInterface.EventArrayListCallback() {
            @Override
            public void returnArrayList(ArrayList<Event> eventsArrayList) {
                eventList = sortEventsByDate(eventsArrayList);
                adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, eventList);
                adapter.setNotifyOnChange(true);
                listView.setAdapter(adapter);
            }
        });
    }

    /**
     * This methods receives an ArrayList of Events and returns a new ArrayList with the same items only sorted by date.
     *
     * @param eventsArrayList The ArrayList to be sorted.
     * @return An ArrayList identical to the one received, only this one is sorted by the date of each Event in it's items
     */
    public static ArrayList<Event> sortEventsByDate(ArrayList<Event> eventsArrayList) {
        ArrayList<Event> sortedEvents = new ArrayList<>();// The ArrayList to be returned
        boolean isMostEarly;// A boolean to check if the current item is the earliest.

        while (eventsArrayList.size() != 0) // Repeat until eventsArrayList has no more Events left in it.

            // These 2 loops get the earliest Event, add it to the sortedEvents ArrayList, and remove it from eventsArrayList.
            for (int i = 0; i < eventsArrayList.size(); i++) {
                isMostEarly = true;
                for (int j = 0; j < eventsArrayList.size(); j++) {
                    if (eventsArrayList.get(i).getDate().after(eventsArrayList.get(j).getDate())) {
                        isMostEarly = false;
                        break;
                    }
                }
                if (isMostEarly) {
                    sortedEvents.add(eventsArrayList.get(i));
                    eventsArrayList.remove(eventsArrayList.get(i));
                    break;
                }
            }


        return sortedEvents;
    }

    /**
     * This function registers the Event class as a subclass of ParseObject
     * and initializes the Parse connection for this application.
     */
    private void initParseStuff() {
        ParseObject.registerSubclass(Event.class);

        try {
            Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId("iioJkrW7NrDjXrqFQcxJCm7HhkFIrEEbMd0vmgPp")
                    .clientKey("PvzhaxHueoNlOJYXtf5JvlUotMZW5L7XJGRaiTEI")
                    .server("https://parseapi.back4app.com/").build());
        } catch (IllegalStateException ex) {// NEVER KILL YOUR WITNESSES !
            Toast.makeText(getApplicationContext(), "CHECK PARSE initialize FOR EXCEPTIONS", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param intent      An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == CREATE_EVENT_FOR_RESULT && resultCode == Activity.RESULT_OK) {

            initEventList();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initEventList();
    }
}
