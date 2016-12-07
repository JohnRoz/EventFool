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

        String[] fixedTimeStrings = fixTimeStrings(hourString, minuteString);
        hourString = fixedTimeStrings[0];
        minuteString = fixedTimeStrings[1];

        timeWidget.setText(hourString + ":" + minuteString);
    }

    /**
     * This method is used to check that the hour or the minute that are supposed to be between "00" and "09", are not
     * turned to "0" - "9". This happens because of the int type properties.
     *
     * @param hour   The hour to fix.
     * @param minute The minute to fix.
     * @return A String array containing the 2 fixed params.
     */
    public static String[] fixTimeStrings(String hour, String minute) {
        for (int i = 0; i < 10; i++) {
            if (hour.equals(i + ""))
                hour = "0" + i;
            if (minute.equals(i + ""))
                minute = "0" + i;
        }

        return new String[]{hour, minute};
    }

}
