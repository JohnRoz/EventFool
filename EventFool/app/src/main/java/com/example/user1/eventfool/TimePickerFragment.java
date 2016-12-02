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

/**
 * This is a Fragment of a TimePickerDialog
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    AppCompatTextView timeWidget;// The Time widget.

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
     * Called when the user is done setting a new time and the dialog has closed.
     * This sets the text of the timeWidget with the time chosen by the user.
     *
     * @param view      The view associated with this listener.
     * @param hourOfDay The hour that was set.
     * @param minute    The minute that was set.
     */
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeWidget = (AppCompatTextView) getActivity().findViewById(R.id.time_WGT);

        String hourString = hourOfDay + "";
        String minuteString = minute + "";

        // 00 in int turns to 0
        if (hourString.equals("0"))
            hourString = "00";

        if (minuteString.equals("0"))
            minuteString = "00";


        timeWidget.setText(hourString + ":" + minuteString);


    }

}
