package com.example.user1.eventfool;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;

import com.parse.Parse;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.addEvent)
    FloatingActionButton addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("iioJkrW7NrDjXrqFQcxJCm7HhkFIrEEbMd0vmgPp")
                .clientKey("PvzhaxHueoNlOJYXtf5JvlUotMZW5L7XJGRaiTEI")
                        .server("https://parseapi.back4app.com/").build());


        initNewEventButton();
    }

    private void initNewEventButton() {
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this);
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
}
