package com.dlewburg.taskmanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addTaskButton = findViewById(R.id.mainActivityAddTaskButton);
        Button allTaskButton = findViewById(R.id.mainActivityAllTasksButton);

        addTaskButton.setOnClickListener(v -> {
            Intent goToAddTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
            //Start Intent
            startActivity(goToAddTaskIntent);
        });

        allTaskButton.setOnClickListener(v -> {
            Intent goToAllTaskIntent = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(goToAllTaskIntent);
        });


    }

}