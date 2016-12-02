package com.example.user1.eventfool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    static final int START_ACTIVITY_FOR_RESULT_CONST = 100;
    static final String EVENT_STRING = "Events";
    static final String ACTION_CREATE_EVENT = "com.example.user1.eventfool.action.CREATE_EVENT";
    static final String ACTION_EDIT_EVENT = "com.example.user1.eventfool.action.EDIT_EVENT";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.addEvent)
    FloatingActionButton addEvent;

    ArrayList<Event> eventList;
    ArrayAdapter<Event> adapter;
    ParseUsageMethods parseUsageMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        parseUsageMethods = new ParseUsageMethods();

        initParseStuff();

        initEventList();

        initNewEventButton();
    }

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

    private void initParseStuff() {
        ParseObject.registerSubclass(Event.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("iioJkrW7NrDjXrqFQcxJCm7HhkFIrEEbMd0vmgPp")
                .clientKey("PvzhaxHueoNlOJYXtf5JvlUotMZW5L7XJGRaiTEI")
                .server("https://parseapi.back4app.com/").build());
    }


    private void initNewEventButton() {
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ManageEventsActivity.class);
                intent.setAction(ACTION_CREATE_EVENT);
                startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_CONST);

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        initEventList();

    }
}
