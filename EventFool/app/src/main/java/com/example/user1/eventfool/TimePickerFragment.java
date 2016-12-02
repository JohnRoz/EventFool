package com.example.user1.eventfool;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import static com.example.user1.eventfool.ManageEventsActivity.SPLIT_TIME_BY;

/**
 * Created by USER1 on 01/12/2016.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    AppCompatTextView timeWidget;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        timeWidget = (AppCompatTextView) getActivity().findViewById(R.id.time_WGT);

        int hour;
        int minute;

        if (timeWidget.getText().equals("")) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        } else {
            String[] pickedTime = timeWidget.getText().toString().split(SPLIT_TIME_BY);
            hour = Integer.parseInt(pickedTime[0]);
            minute = Integer.parseInt(pickedTime[1]);

        }


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    /**
     * Set the text of the timeWidget with the time chosen by the user.
     *
     * @param view
     * @param hourOfDay
     * @param minute
     */
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeWidget = (AppCompatTextView) getActivity().findViewById(R.id.time_WGT);

        //The next 3 If (or else If) statements are in the cases that the hour or the minute is "00", so i want it
        // to present is as "00" and not as just "0".
        if ((hourOfDay + "").equals("00") && (minute + "").equals("00"))
            timeWidget.setText("00:00");

        else if ((hourOfDay + "").equals("00"))
            timeWidget.setText("00:" + minute);

        else if ((minute + "").equals("00"))
            timeWidget.setText(hourOfDay + ":00");

        else
            timeWidget.setText(hourOfDay + ":" + minute);


    }
}
