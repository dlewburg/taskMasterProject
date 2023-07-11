package com.dlewburg.taskmanagerandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.Intent;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
//import android.view.View;
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
import java.util.stream.Collectors;

public class AddTaskActivity extends AppCompatActivity {
    public static final String TAG = "add_task_tag";

    List<Team> teamList = new ArrayList<>();
    Team team;
    String teamString;
    Spinner taskStatusSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        CompletableFuture<List<Team>> teamsFuture = new CompletableFuture<>();
//        List<Team> teamList = new ArrayList<>();
        List<String> teamListAsString = new ArrayList<>();
        Spinner taskTeamSpinner = findViewById(R.id.addTaskActivityTeamSpinner);
        Amplify.API.query(
            ModelQuery.list(Team.class),
            success -> {
                Log.i(TAG, "Successfully Read Team");
                for (Team teams : success.getData()) {
                    teamList.add(teams);
                }
                teamsFuture.complete(teamList);
                runOnUiThread(() -> {
                    for (Team teams : teamList)
                        teamListAsString.add(teams.getName());
                    taskTeamSpinner.setAdapter(new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        teamListAsString
                    ));
                taskTeamSpinner.setSelection(0);
                teamString = taskTeamSpinner.getSelectedItem().toString();
//                team = teamList.stream().filter(e -> e.getName().equals(teamString)).collect(Collectors.toList()).get(0);
                });
            },

            failure -> {
                teamsFuture.complete(null);
                Log.i(TAG, "Failed to Read Team");
            }
            );

        List<String> statusList = TaskStatusUtility.getTaskStatusList();

        taskStatusSpinner = findViewById(R.id.addTaskActivitySpinnerTaskStatus);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            statusList
        ));



        addingTaskButtonFunction();

    }

    @SuppressLint("SetTextI18n")
    public void addingTaskButtonFunction() {
        Button addTaskSubmitButton = findViewById(R.id.addTaskActivityAddTaskButton);
        addTaskSubmitButton.setOnClickListener(V -> {
            TextView addTaskSubmitted = (TextView) findViewById(R.id.addTaskActivitySubmitTextView);
            addTaskSubmitted.setText("TASK SUBMITTED!");
//                Intent goToAllTaskActivity = new Intent(AddTaskActivity.this, AllTasksActivity.class);
//
//                startActivity(goToAllTaskActivity);
            team = teamList.stream().filter(e -> e.getName().equals(teamString)).collect(Collectors.toList()).get(0);

            TaskStatus newStatus = TaskStatusUtility.taskStatusFromString(taskStatusSpinner.getSelectedItem().toString());

            Task newTask = Task.builder()
                .title(getTitle().toString())
//            .body(get.toString())
                .status(newStatus)
                .team(team)
                .build();

            Amplify.API.mutate(
                ModelMutation.create(newTask),
                successResponse -> Log.i(TAG, "AddTaskActivity.onCreate() : added a task"),
                failureResponse -> Log.i(TAG, "AddTaskActivity.onCreate() : adding task failed" + failureResponse)
            );

        });
    }
}

