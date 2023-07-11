package com.dlewburg.taskmanagerandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
//import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dlewburg.taskmanagerandroid.MainActivity;
import com.dlewburg.taskmanagerandroid.R;

//import org.w3c.dom.Text;

public class TaskDetailsActivity extends AppCompatActivity {

//    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        taskDetailsTaskTitle();
        setUpLocationDetails();
    }

    public void taskDetailsTaskTitle(){
        Intent callingIntent = getIntent();
        String taskNameString = null;
        String taskDescriptionString = null;
        String taskStatusString = null;

        if(callingIntent != null){
            taskNameString = callingIntent.getStringExtra(MainActivity.TASK_DETAILS_TITLE_TAG);
            taskDescriptionString = callingIntent.getStringExtra(MainActivity.TASK_DESCRIPTION_TAG);
            taskStatusString = callingIntent.getStringExtra(MainActivity.TASK_STATUS_TAG);
        }

        TextView currentTaskTitle = findViewById(R.id.taskDetailsActivityTitleTextView);
        if(taskNameString != null) {
            currentTaskTitle.setText(taskNameString);
        } else {
            currentTaskTitle.setText(R.string.no_task_name);
        }

        EditText taskDetailsEditText = findViewById(R.id.taskDetailsActivityMultiLineEditText);
        if(taskDescriptionString != null) {
            taskDetailsEditText.setText(taskDescriptionString);
        }

        TextView taskStatusTextView = findViewById(R.id.taskDetailsActivityTaskStatusTextView);
        if(taskStatusString != null) {
            taskStatusTextView.setText(taskStatusString);
        }

    }

    public void setUpLocationDetails() {
        Intent callingIntent = getIntent();
        String taskLatitude = null;
        String taskLongitude = null;

        if(callingIntent != null){
            taskLatitude = callingIntent.getStringExtra(MainActivity.TASK_LATITUDE_EXTRA_TAG);
            taskLongitude = callingIntent.getStringExtra(MainActivity.TASK_LONGITUDE_EXTRA_TAG);
        }

        TextView taskDetailsInfoTextView = findViewById(R.id.taskDetailsActivityLocationTextView);
        if(taskLatitude!= null && taskLongitude != null){
            taskDetailsInfoTextView.setText("Location: " + taskLatitude + ", " + taskLongitude);
        } else {
            taskDetailsInfoTextView.setText(R.string.no_location);
        }
    }
}