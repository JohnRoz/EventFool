package com.example.user1.eventfool;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.widget.DatePicker;

import java.util.Calendar;

import static com.example.user1.eventfool.MainActivity.ACTION_CREATE_EVENT;
import static com.example.user1.eventfool.ManageEventsActivity.SPLIT_DATE_BY;

/**
 * Created by USER1 on 01/12/2016.
 */

/**
 * This is a Fragment of a DatePickerDialog
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    AppCompatTextView dateWidget;//The date widget.

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateWidget = (AppCompatTextView) getActivity().findViewById(R.id.date_WGT);

        int day;
        int month;
        int year;

        if (getActivity().getIntent().getAction().equals(ACTION_CREATE_EVENT)) {
            // Use the current date as the default date in the picker
            final Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        } else {// If the user already picked a date and wants to change it

            // Splitting the text of the dateWidget to day, month and year variables.
            String[] pickedDate = dateWidget.getText().toString().split(SPLIT_DATE_BY);
            day = Integer.parseInt(pickedDate[0]);
            month = Integer.parseInt(pickedDate[1])-1;
            year = Integer.parseInt(pickedDate[2]);
        }


        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * This is called when the user is done setting a new Date and the dialog has closed.
     * This sets the text of the dateWidget to the date chosen by the user.
     *
     * @param view  The picker associated with the dialog.
     * @param year  The selected year.
     * @param month The selected month (0-11 for compatibility with Calendar.MONTH).
     * @param day   The selected day of the month (1-31, depending on month).
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // In Calender, the month values are between 0 and 11
        month += 1;

        dateWidget = (AppCompatTextView) getActivity().findViewById(R.id.date_WGT);//Initialising the widget.
        dateWidget.setText(day + SPLIT_DATE_BY + month + SPLIT_DATE_BY + year);//Setting the text of the widget.

    }
}
