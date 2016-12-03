package com.example.user1.eventfool;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.user1.eventfool.MainActivity.ACTION_CREATE_EVENT;
import static com.example.user1.eventfool.MainActivity.ACTION_EDIT_EVENT;
import static com.example.user1.eventfool.MainActivity.EVENT_LIST_POSITION_STRING;
import static com.example.user1.eventfool.MainActivity.EVENT_STRING;

/**
 * This is an Activity that exists in order to create & edit Events.
 */
public class ManageEventsActivity extends AppCompatActivity {

    // The constants separators between Date & Time Strings. These are used to .split() the Strings.
    // date: ("[year] / [month] / [day]")
    // Time: ("[hour] : [minutes]")
    static final String SPLIT_DATE_BY = "/";
    static final String SPLIT_TIME_BY = ":";

    // Binding views using ButterKnife:
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.set_event_id)
    TextView setEventTextView;
    @BindView(R.id.date_WGT)
    AppCompatTextView dateWidget;
    @BindView(R.id.time_WGT)
    AppCompatTextView timeWidget;
    @BindView(R.id.save_FAB)
    FloatingActionButton saveBtn;
    @BindView(R.id.event_title_ID)
    EditText eventTitle;
    @BindView(R.id.event_text_ID)
    EditText eventText;

    ParseUsageMethods parseUsageMethods;
    Event editedEvent;
    int editedEventPosition;
    String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        parseUsageMethods = new ParseUsageMethods();

        initDatePicker();

        initTimePicker();

        initSaveButton();

        action = getIntent().getAction();
        switch (action) {
            case ACTION_CREATE_EVENT:
                initWidgetsText_CurrentDateAndTime();
                break;
            case ACTION_EDIT_EVENT:
                initPreviousDetails();
                break;
        }
    }

    /**
     * This runs when the Save button is clicked.
     */
    private void initSaveButton() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!areDetailsReady()) {
                    Toast.makeText(getApplicationContext(), "Finish to fill the details first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // The names of the details in a more organized way.
                String title = eventTitle.getText().toString();
                String text = eventText.getText().toString();
                Date date = createDateFromEventDetails();

                switch (action) {
                    case ACTION_CREATE_EVENT:
                        saveEvent(new Event(), title, text, date);
                        break;
                    case ACTION_EDIT_EVENT:
                        saveEvent(editedEvent, title, text, date);
                        break;
                }
            }
        });
    }

    /**
     * This is for saving the Event, whether it's a new Event or an edited Event.
     *
     * @param event The Event to be saved.
     * @param title The title value of the Event.
     * @param text  The text value of the Event.
     * @param date  The Date of the Event.
     */
    private void saveEvent(Event event, String title, String text, Date date) {
        event.setTitle(title);
        event.setText(text);
        event.setDate(date);

        parseUsageMethods.saveEvent(event);

        Intent resultIntent = new Intent();
        resultIntent.putExtra(EVENT_STRING, event);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }


    /**
     * Setting the texts of the Date & Time widgets as the current date & time.
     * This is called when the user creates a new Event -
     * the default date & time for the new Event are the current date & time.
     */
    private void initWidgetsText_CurrentDateAndTime() {
        Calendar cal = Calendar.getInstance();

        String currentYear = cal.get(Calendar.YEAR) + "";
        String currentMonth = cal.get(Calendar.MONTH) + 1 + "";// In Calender, the month values are between 0 and 11.
        String currentDay = cal.get(Calendar.DAY_OF_MONTH) + "";
        String currentHour = cal.get(Calendar.HOUR_OF_DAY) + "";
        String currentMinute = cal.get(Calendar.MINUTE) + "";

        String[] fixedTimeStrings = TimePickerFragment.fixTimeStrings(currentHour, currentMinute);
        currentHour = fixedTimeStrings[0];
        currentMinute = fixedTimeStrings[1];

        dateWidget.setText(currentDay + SPLIT_DATE_BY + currentMonth + SPLIT_DATE_BY + currentYear);
        timeWidget.setText(currentHour + SPLIT_TIME_BY + currentMinute);


    }

    /**
     * Setting the texts of the Title, Text, Date & Time details as the previous details chosen by the user.
     * This is called when the user Edits an existing Event.
     */
    private void initPreviousDetails() {
        editedEventPosition = getIntent().getIntExtra(EVENT_LIST_POSITION_STRING, editedEventPosition);

        parseUsageMethods.getAllEvents(new EventSystemInterface.EventArrayListCallback() {
            @Override
            public void returnArrayList(ArrayList<Event> eventsArrayList) {
                // The Event that was pressed.
                editedEvent = eventsArrayList.get(editedEventPosition);

                //Set the Title & the Text of the Event
                eventTitle.setText(editedEvent.getTitle());
                eventText.setText(editedEvent.getText());

                // The date of the Event that was pressed.
                Date thisEventDate = editedEvent.getDate();


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

                //setting the showDate & showTime TextViews by the date of the Event that was pressed.
                dateWidget.setText(day + SPLIT_DATE_BY + month + SPLIT_DATE_BY + year);
                timeWidget.setText(hour + SPLIT_TIME_BY + minute);
            }
        });
    }

    /**
     * This runs when the Date widget is pressed.
     * Initiating the Fragment of the DatePickerDialog.
     */
    private void initDatePicker() {
        dateWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });
    }

    /**
     * This runs when the Time widget is pressed.
     * Initiating the Fragment of the TimePickerDialog.
     */
    private void initTimePicker() {
        timeWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });
    }

    /**
     * Creating the date to be inserted to the Event, by taking the texts
     * of the Date & Time widgets and turning them to a Date Object.
     *
     * @return The date object created by the texts of the Date & Time widgets.
     */
    private Date createDateFromEventDetails() {
        String[] pickedDate = dateWidget.getText().toString().split(SPLIT_DATE_BY);
        int day = Integer.parseInt(pickedDate[0]);
        int month = Integer.parseInt(pickedDate[1]) - 1; // In Calender, the month values are between 0 and 11.
        int year = Integer.parseInt(pickedDate[2]);

        String[] pickedTime = timeWidget.getText().toString().split(SPLIT_TIME_BY);
        int hour = Integer.parseInt(pickedTime[0]);
        int minute = Integer.parseInt(pickedTime[1]);

        Calendar cal = new GregorianCalendar(year, month, day, hour, minute);

        return cal.getTime();

    }

    /**
     * Checking if the user finished to fill at least one of the details: Title or Text.
     *
     * @return True, if the title of the Event or the text of the Event are not empty (""). False, if both are empty ("").
     */
    private boolean areDetailsReady() {
        String title = eventTitle.getText().toString();
        String text = eventText.getText().toString();

        return !title.equals("") || !text.equals("");
    }

}
