package com.dlewburg.taskmanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button addTaskSubmitButton = findViewById(R.id.addTaskActivityAddTaskButton);
        addTaskSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                TextView addTaskSubmitted = (TextView) findViewById(R.id.addTaskActivitySubmitTextView);
                        addTaskSubmitted.setText("TASK SUBMITTED!");
            }

        });
    }
}