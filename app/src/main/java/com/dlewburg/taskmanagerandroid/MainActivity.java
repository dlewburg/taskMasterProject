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
//        addTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////               TextView myTasksTextView = (TextView) findViewById(R.id.mainActivityMyTasksTextView);
//            }
//        });

        addTaskButton.setOnClickListener(v -> {
            Intent goToOrderFormIntent = new Intent(MainActivity.this, AddTaskActivity.class);
            //Start Intent
            startActivity(goToOrderFormIntent);
        });
    }

}