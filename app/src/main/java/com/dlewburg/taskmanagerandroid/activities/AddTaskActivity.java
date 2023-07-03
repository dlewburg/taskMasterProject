package com.dlewburg.taskmanagerandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskStatus;
import com.amplifyframework.datastore.generated.model.Team;
import com.dlewburg.taskmanagerandroid.R;
import com.dlewburg.taskmanagerandroid.utils.TaskStatusUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AddTaskActivity extends AppCompatActivity {
    public static final String TAG = "add_task_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        CompletableFuture<List<Team>> teamsFuture = new CompletableFuture<>();
        List<Team> teamList = new ArrayList<>();
        List<String> teamListAsString = new ArrayList<>();
        Spinner taskTeamSpinner = findViewById(R.id.addTaskActivityTeamSpinner);
        Amplify.API.query(
            ModelQuery.list(Team.class),
            success -> {
                Log.i(TAG, "Successfully Read Team");
                for (Team team : success.getData()) {
                    teamList.add(team);
                }
                teamsFuture.complete(teamList);
                runOnUiThread(() -> {
                    for (Team team : teamList)
                        teamListAsString.add(team.getName());
                    taskTeamSpinner.setAdapter(new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        teamListAsString
                    ));
                });
            },
            failure -> Log.i(TAG, "Failed to read Teams")
        );


        List<String> statusList = TaskStatusUtility.getTaskStatusList();

        Spinner taskStatusSpinner = findViewById(R.id.addTaskActivitySpinnerTaskStatus);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            statusList
        ));

        TaskStatus newStatus = TaskStatusUtility.taskStatusFromString(taskStatusSpinner.getSelectedItem().toString());
        Task newTask = Task.builder()
            .title(getTitle().toString())
//            .body(get.toString())
            .status(newStatus)
            .build();

        Amplify.API.mutate(
            ModelMutation.create(newTask),
            successResponse -> Log.i(TAG, "AddTaskActivity.onCreate() : added a task"),
            failureResponse -> Log.i(TAG, "AddTaskActivity.onCreate() : adding task failed" + failureResponse)
        );



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

