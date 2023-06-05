package com.dlewburg.taskmanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlewburg.taskmanagerandroid.activities.AddTaskActivity;
import com.dlewburg.taskmanagerandroid.activities.AllTasksActivity;
import com.dlewburg.taskmanagerandroid.activities.ProfileEditActivity;
import com.dlewburg.taskmanagerandroid.activities.TaskDetailsActivity;

public class MainActivity extends AppCompatActivity {
    public static final String TASK_DETAILS_TITLE_TAG = "taskTitle";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        profileEditButtonFunction();
        firstTaskButtonFunction();
        secondTaskButtonFunction();
        thirdTaskButtonFunction();
        addTaskButtonFunction();
        allTaskButtonFunction();

    }

    @Override
    protected void onResume() {
        super.onResume();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userProfileName = preferences.getString(ProfileEditActivity.PROFILE_USERNAME_TAG, "");
        ((TextView) findViewById(R.id.mainActivityMyTasksTextView)).setText(userProfileName + "'s Tasks");
    }

    public void profileEditButtonFunction() {
        ImageView profileEditButton = findViewById(R.id.mainActivityProfileEditButton);

        profileEditButton.setOnClickListener(v -> {
            Intent goToProfileEditIntent = new Intent(MainActivity.this, ProfileEditActivity.class);
            startActivity(goToProfileEditIntent);
        });
    }

    public void addTaskButtonFunction() {

        Button addTaskButton = findViewById(R.id.mainActivityAddTaskButton);

        addTaskButton.setOnClickListener(v -> {
            Intent goToAddTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
            //Start Intent
            startActivity(goToAddTaskIntent);
        });

    }

    public void allTaskButtonFunction() {

        Button allTaskButton = findViewById(R.id.mainActivityAllTasksButton);

        allTaskButton.setOnClickListener(v -> {
            Intent goToAllTaskIntent = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(goToAllTaskIntent);
        });

    }

    public void firstTaskButtonFunction() {
        Button firstTaskButton = findViewById(R.id.mainActivityFirstTaskButton);
        firstTaskButton.setOnClickListener(v -> {
            Intent goToFirstTaskDetailsActivity = new Intent(MainActivity.this, TaskDetailsActivity.class);
            String taskTitle = ((Button) findViewById(R.id.mainActivityFirstTaskButton)).getText().toString();
            goToFirstTaskDetailsActivity.putExtra(TASK_DETAILS_TITLE_TAG, taskTitle);

            startActivity(goToFirstTaskDetailsActivity);
        });
    }

    public void secondTaskButtonFunction() {
        Button secondTaskButton = findViewById(R.id.mainActivitySecondTaskButton);
        secondTaskButton.setOnClickListener(v -> {
            Intent goToSecondTaskDetailsActivity = new Intent(MainActivity.this, TaskDetailsActivity.class);
            String taskTitle = ((Button) findViewById(R.id.mainActivitySecondTaskButton)).getText().toString();
            goToSecondTaskDetailsActivity.putExtra(TASK_DETAILS_TITLE_TAG, taskTitle);

            startActivity(goToSecondTaskDetailsActivity);
        });
    }

    public void thirdTaskButtonFunction() {
        Button thirdTaskButton = findViewById(R.id.mainActivityThirdTaskButton);
        thirdTaskButton.setOnClickListener(v -> {
            Intent goToThirdTaskDetailsActivity = new Intent(MainActivity.this, TaskDetailsActivity.class);
            String taskTitle = ((Button) findViewById(R.id.mainActivityThirdTaskButton)).getText().toString();
            goToThirdTaskDetailsActivity.putExtra(TASK_DETAILS_TITLE_TAG, taskTitle);

            startActivity(goToThirdTaskDetailsActivity);
        });
    }


}