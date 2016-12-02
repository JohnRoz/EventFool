package com.example.user1.eventfool;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.user1.eventfool.MainActivity.EVENT_STRING;
import static com.example.user1.eventfool.ManageEventsActivity.SPLIT_DATE_BY;
import static com.example.user1.eventfool.ManageEventsActivity.SPLIT_TIME_BY;

/**
 * This class is to present the Event after clicked in the listView.
 */
public class ShowEventActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

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

            }
        });
    }

    /**
     * This initializes the details of the Event to be presented in the Activity.
     */
    private void initEventDetails(){
        // The Event that was pressed.
        Event thisEvent = (Event)getIntent().getSerializableExtra(EVENT_STRING);

        //Set the Title & the Text of the Event
        showTitle.setText(thisEvent.getTitle());
        showText.setText(thisEvent.getText());

        // The date of the Event that was pressed.
        Date thisEventDate = thisEvent.getDate();// TODO: thisEvent.getDate() is NULL for some reason... FIX IT!

        Calendar cal = Calendar.getInstance();
        cal.setTime(thisEventDate);

        String year = cal.get(Calendar.YEAR) + "";
        String month = cal.get(Calendar.MONTH) + "";
        String day = cal.get(Calendar.DAY_OF_MONTH) + "";
        String hour = cal.get(Calendar.HOUR_OF_DAY) + "";
        String minute = cal.get(Calendar.MINUTE) + "";

        //setting the showDate & showTime TextViews by the date of the Event that was pressed.
        showDate.setText(day + SPLIT_DATE_BY + month + SPLIT_DATE_BY + year);
        showTime.setText(hour + SPLIT_TIME_BY + minute);
    }

}
