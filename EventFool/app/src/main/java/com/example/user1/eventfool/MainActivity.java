package com.example.user1.eventfool;

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

    static final int START_ACTIVITY_FOR_RESULT_REQUEST_CODE = 100; // The requestCode for onActivityResult().
    static final String EVENT_STRING = "Events";

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

        initPresentEvent();

        initDeleteEvent();
    }

    /**
     * This runs when an Item from the ListView is pressed.
     * This starts ShowEventActivity.
     * The Event that was pressed is passed to ShowEventActivity as an extra.
     */
    private void initPresentEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event thisEvent = adapter.getItem(position);// The Event that was pressed.

                Intent intent = new Intent(MainActivity.this, ShowEventActivity.class);
                intent.putExtra(EVENT_STRING, thisEvent);
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
                startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_REQUEST_CODE);
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
                eventList = eventsArrayList;
                adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, eventList);
                listView.setAdapter(adapter);
            }
        });
    }

    /**
     * This function registers the Event class as a subclass of ParseObject
     * and initializes the Parse connection for this application.
     */
    private void initParseStuff() {
        ParseObject.registerSubclass(Event.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("iioJkrW7NrDjXrqFQcxJCm7HhkFIrEEbMd0vmgPp")
                .clientKey("PvzhaxHueoNlOJYXtf5JvlUotMZW5L7XJGRaiTEI")
                .server("https://parseapi.back4app.com/").build());
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

        initEventList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initEventList();
    }
}
