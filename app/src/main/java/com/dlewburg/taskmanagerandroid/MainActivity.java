package com.dlewburg.taskmanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.dlewburg.taskmanagerandroid.activities.AddTaskActivity;
import com.dlewburg.taskmanagerandroid.activities.AllTasksActivity;
import com.dlewburg.taskmanagerandroid.activities.ProfileEditActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileEditButtonFunction();
        addTaskButtonFunction();
        allTaskButtonFunction();

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



}