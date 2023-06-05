package com.dlewburg.taskmanagerandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.dlewburg.taskmanagerandroid.MainActivity;
import com.dlewburg.taskmanagerandroid.R;

public class TaskDetailsActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        taskDetailsTaskTitle();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String taskDetailsTitle = preferences.getString(MainActivity.TASK_DETAILS_TITLE_TAG, "");
//        ((TextView) findViewById(R.id.mainActivityMyTasksTextView)).setText(taskDetailsTitle);
//    }
    public void taskDetailsTaskTitle(){
        Intent callingIntent = getIntent();
        String taskNameString = null;

        if(callingIntent != null){
            taskNameString = callingIntent.getStringExtra(MainActivity.TASK_DETAILS_TITLE_TAG);
        }

        TextView currentTaskTitle = findViewById(R.id.taskDetailsActivityTitleTextView);
        if(taskNameString != null) {
            currentTaskTitle.setText(taskNameString);
        } else {
            currentTaskTitle.setText(R.string.no_task_name);
        }
    }

}