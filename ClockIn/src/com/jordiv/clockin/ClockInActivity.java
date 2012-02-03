package com.jordiv.clockin;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class ClockInActivity extends Activity {
	private TicketDataSource datasource;
	private Button showLogButton;
	private TimePicker timePicker;
	private DatePicker datePicker;
	private ToggleButton toggleButton;
	private Button button;
	
	static final int TIME_DIALOG_ID = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        datasource = new TicketDataSource(this);
		datasource.open();
		
		
		showLogButton = (Button) findViewById(R.id.showLogButton);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        toggleButton= (ToggleButton) findViewById(R.id.toggleButton);
        button = (Button) findViewById(R.id.button);
        
        timePicker.setIs24HourView(true);
        
        // add a click listener to the button
        timePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            }
        });
        
        showLogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(v.getContext(), TicketListActivity.class);
            	startActivity(intent);
            }
        });
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	/*
            	String string = "";
            	string += timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
            	string += "#";
            	string += datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
            	string += "#";
            	string += toggleButton.isChecked();
            	string += "\n";
            	*/
            	
            	Calendar calendar = Calendar.getInstance();
            	Date date = new Date(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            	calendar.setTime(date);
            	
    			// Save the new comment to the database
    			datasource.createTicket(calendar.getTimeInMillis(), toggleButton.isChecked());
    			
    			/*
            	FileOutputStream fos;
				try {
					fos = openFileOutput(FILENAME, Context.MODE_APPEND);
					fos.write(string.getBytes());
	            	fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				*/
            }
        });
    }
}