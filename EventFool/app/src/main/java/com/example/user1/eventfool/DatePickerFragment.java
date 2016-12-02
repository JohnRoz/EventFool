package com.example.user1.eventfool;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.widget.DatePicker;

import java.util.Calendar;

import static com.example.user1.eventfool.ManageEventsActivity.SPLIT_DATE_BY;

/**
 * Created by USER1 on 01/12/2016.
 */

/**
 * This is a Fragment of a DatePickerDialog
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    AppCompatTextView dateWidget;//The widget of the date.

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateWidget = (AppCompatTextView) getActivity().findViewById(R.id.date_WGT);

        int day;
        int month;
        int year;

        if (dateWidget.getText().equals("")) {
            // Use the current date as the default date in the picker
            final Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        } else {// If the user already picked a date and wants to change it

            // Splitting the text of the dateWidget to day, month and year variables.
            String[] pickedDate = dateWidget.getText().toString().split(SPLIT_DATE_BY);
            day = Integer.parseInt(pickedDate[0]);
            month = Integer.parseInt(pickedDate[1]);
            year = Integer.parseInt(pickedDate[2]);
        }


        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * This runs after the user is done editing the DatePicker.
     * This sets the text of the dateWidget to the date chosen by the user.
     *
     * @param view
     * @param year
     * @param month
     * @param day
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //For some reason, the month is always 1 month smaller (is writes 0 fo Jan, 11 fo Dec etc).
        month += 1;

        dateWidget = (AppCompatTextView) getActivity().findViewById(R.id.date_WGT);//Initialising the widget.
        dateWidget.setText(day + SPLIT_DATE_BY + month + SPLIT_DATE_BY + year);//Setting the text of the widget.

    }
}
