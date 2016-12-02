package com.example.user1.eventfool;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageEventsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.set_event_id)
    TextView setEventTextView;
    @BindView(R.id.date_WGT)
    AppCompatTextView dateWidget;
    @BindView(R.id.time_WGT)
    AppCompatTextView timeWidget;
    @BindView(R.id.save_BTN)
    FloatingActionButton saveBtn;
    @BindView(R.id.event_title_ID)
    EditText eventTitle;
    @BindView(R.id.event_text_ID)
    EditText eventText;

    ParseUsageMethods parseUsageMethods;

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
    }

    private void initDatePicker() {
        dateWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });
    }

    private void initTimePicker() {
        timeWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });
    }

    private void initSaveButton() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!areDetailsReady()) {
                    Toast.makeText(getApplicationContext(), "Finish to fill the details first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String title = eventTitle.getText().toString();
                String text = eventText.getText().toString();
                Date date = createDatefromEventDetails();

                Event newEvent = new Event();
                newEvent.setTitle(title);
                newEvent.setText(text);
                newEvent.setDate(date);

                parseUsageMethods.saveEvent(newEvent);
            }
        });
    }

    private Date createDatefromEventDetails() {
        String[] pickedTime = timeWidget.getText().toString().split(":");
        int hour = Integer.parseInt(pickedTime[0]);
        int minute = Integer.parseInt(pickedTime[1]);

        String[] pickedDate = dateWidget.getText().toString().split("/");
        int day = Integer.parseInt(pickedDate[0]);
        int month = Integer.parseInt(pickedDate[1]);
        int year = Integer.parseInt(pickedDate[2]);

        Calendar cal = new GregorianCalendar(year, month, day, hour, minute);

        return cal.getTime();

    }

    private boolean areDetailsReady() {
        String title = eventTitle.getText().toString();
        String text = eventText.getText().toString();
        String date = dateWidget.getText().toString();
        String time = timeWidget.getText().toString();

        return !title.equals("") && !text.equals("") && !date.equals("") && !time.equals("");
    }


}
