package com.dlewburg.taskmanagerandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dlewburg.taskmanagerandroid.R;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        addingTaskButtonFunction();

    }

    public void addingTaskButtonFunction() {
        Button addTaskSubmitButton = findViewById(R.id.addTaskActivityAddTaskButton);
        addTaskSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                TextView addTaskSubmitted = (TextView) findViewById(R.id.addTaskActivitySubmitTextView);
                addTaskSubmitted.setText("TASK SUBMITTED!");
//                Intent goToAllTaskActivity = new Intent(AddTaskActivity.this, AllTasksActivity.class);
//
//                startActivity(goToAllTaskActivity);
            }

        });
    }
}