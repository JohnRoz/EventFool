package com.example.user1.eventfool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.user1.eventfool.MainActivity.ACTION_EDIT_EVENT;
import static com.example.user1.eventfool.MainActivity.EVENT_LIST_POSITION_STRING;
import static com.example.user1.eventfool.ManageEventsActivity.SPLIT_DATE_BY;
import static com.example.user1.eventfool.ManageEventsActivity.SPLIT_TIME_BY;

/**
 * This class is to present the Event after clicked in the listView.
 */
public class ShowEventActivity extends AppCompatActivity {

    static final int EDIT_EVENT_FOR_RESULT = 200; // The requestCode for onActivityResult().

    // Binding views using ButterKnife:
    @BindView(R.id.showTitle)
    TextView showTitle;
    @BindView(R.id.showText)
    TextView showText;
    @BindView(R.id.showDate)
    TextView showDate;
    @BindView(R.id.showTime)
    TextView showTime;
    @BindView(R.id.editEvent_FAB)
    FloatingActionButton editEvent;

    ParseUsageMethods parseUsageMethods;
    Event thisEvent;
    int eventPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        parseUsageMethods = new ParseUsageMethods();

        initEventDetails();

        initEditEventButton();
    }

    /**
     * This runs after the editEvent button was pressed.
     * This will start ManagerEventsActivity for a result.
     */
    private void initEditEventButton() {
        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowEventActivity.this, ManageEventsActivity.class);
                intent.setAction(ACTION_EDIT_EVENT);
                intent.putExtra(EVENT_LIST_POSITION_STRING, eventPosition);

                startActivityForResult(intent, EDIT_EVENT_FOR_RESULT);
            }
        });
    }

    /**
     * This initializes the details of the Event to be presented in the Activity.
     */
    private void initEventDetails() {

        eventPosition = getIntent().getIntExtra(EVENT_LIST_POSITION_STRING, eventPosition);

        parseUsageMethods.getAllEvents(new EventSystemInterface.EventArrayListCallback() {
            @Override
            public void returnArrayList(ArrayList<Event> eventsArrayList) {
                // The Event that was pressed.
                thisEvent = eventsArrayList.get(eventPosition);

                //Set the Title & the Text of the Event
                showTitle.setText("Title: " + thisEvent.getTitle());
                showText.setText("Text: " + thisEvent.getText());

                // The date of the Event that was pressed.
                Date thisEventDate = thisEvent.getDate();


                Calendar cal = Calendar.getInstance();
                cal.setTime(thisEventDate);

                String year = cal.get(Calendar.YEAR) + "";
                String month = cal.get(Calendar.MONTH) + 1 + "";// In Calender, the month values are between 0 and 11.
                String day = cal.get(Calendar.DAY_OF_MONTH) + "";
                String hour = cal.get(Calendar.HOUR_OF_DAY) + "";
                String minute = cal.get(Calendar.MINUTE) + "";

                String[] fixedTimeStrings = TimePickerFragment.fixTimeStrings(hour, minute);
                hour = fixedTimeStrings[0];
                minute = fixedTimeStrings[1];

                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");
                String dayOfWeek = dateFormat.format(thisEventDate);

                //setting the showDate & showTime TextViews by the date of the Event that was pressed.
                showDate.setText("Date: " + dayOfWeek + ", " + day + SPLIT_DATE_BY + month + SPLIT_DATE_BY + year);
                showTime.setText("Time: " + hour + SPLIT_TIME_BY + minute);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initEventDetails();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_EVENT_FOR_RESULT && resultCode == Activity.RESULT_OK)
            initEventDetails();
    }
}
