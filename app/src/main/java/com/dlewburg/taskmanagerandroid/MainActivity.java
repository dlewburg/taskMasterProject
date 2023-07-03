package com.dlewburg.taskmanagerandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.dlewburg.taskmanagerandroid.activities.AddTaskActivity;
import com.dlewburg.taskmanagerandroid.activities.AllTasksActivity;
import com.dlewburg.taskmanagerandroid.activities.ProfileEditActivity;
import com.dlewburg.taskmanagerandroid.activities.TaskDetailsActivity;
import com.dlewburg.taskmanagerandroid.adapters.TaskListRecyclerViewAdapter;
//import com.dlewburg.taskmanagerandroid.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "main_activity_tag";
    public static final String TASK_DETAILS_TITLE_TAG = "taskTitle";
    public static final String TASK_STATUS_TAG = "taskStatus";
    public static final String TASK_DESCRIPTION_TAG= "taskDescription";

    TaskListRecyclerViewAdapter adapter;
    SharedPreferences preferences;
    List<Task> taskList = new ArrayList<>();
    TaskListRecyclerViewAdapter taskListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        taskList.add(new Task("Interview for SWE Role", "Review DSA & How to WB", Task.TaskStatus.NEW));
//        taskList.add(new Task("Walk the dog", "Pick up Golum and walk him 10K steps", Task.TaskStatus.COMPLETE));
//        taskList.add(new Task("Finish Homework", "Complete labs, code challenges, and readings", Task.TaskStatus.IN_PROGRESS));


        profileEditButtonFunction();
        setUpRecyclerView(taskList);
//        firstTaskButtonFunction();
//        secondTaskButtonFunction();
//        thirdTaskButtonFunction();
        addTaskButtonFunction();
        allTaskButtonFunction();

    }

    @Override
    protected void onResume() {
        super.onResume();
        taskList.clear();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userProfileName = preferences.getString(ProfileEditActivity.PROFILE_USERNAME_TAG, "");
        ((TextView) findViewById(R.id.mainActivityMyTasksTextView)).setText(userProfileName + "'s Tasks");

        readTasksFromDatabase();
        taskListRecyclerViewAdapter.updateTasksData(taskList);


    }

    public void profileEditButtonFunction() {
        ImageView profileEditButton = findViewById(R.id.mainActivityProfileEditButton);

        profileEditButton.setOnClickListener(v -> {
            Intent goToProfileEditIntent = new Intent(MainActivity.this, ProfileEditActivity.class);
            startActivity(goToProfileEditIntent);
        });
    }

    public void readTasksFromDatabase() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String current = preferences.getString(ProfileEditActivity.TEAM_TAG, "All");

        Amplify.API.query(
            ModelQuery.list(Task.class),
            success -> {
                Log.i(TAG, "Read Tasks from Database successfully");
                for (Task task : success.getData()) {
                    if (current.equals("All") || task.getTeam().getName().equals(current)) {
                        taskList.add(task);
                    }
                }
                runOnUiThread(() -> taskListRecyclerViewAdapter.notifyDataSetChanged());
            },
            failure -> Log.i(TAG, "Failed to read Tasks from Databaase")
        );

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


//    public void firstTaskButtonFunction() {
//        Button firstTaskButton = findViewById(R.id.mainActivityFirstTaskButton);
//        firstTaskButton.setOnClickListener(v -> {
//            Intent goToFirstTaskDetailsActivity = new Intent(MainActivity.this, TaskDetailsActivity.class);
//            String taskTitle = firstTaskButton.getText().toString();
//            goToFirstTaskDetailsActivity.putExtra(TASK_DETAILS_TITLE_TAG, taskTitle);
//
//            startActivity(goToFirstTaskDetailsActivity);
//        });
//    }
//
//    public void secondTaskButtonFunction() {
//        Button secondTaskButton = findViewById(R.id.mainActivitySecondTaskButton);
//        secondTaskButton.setOnClickListener(v -> {
//            Intent goToSecondTaskDetailsActivity = new Intent(MainActivity.this, TaskDetailsActivity.class);
//            String taskTitle = secondTaskButton.getText().toString();
//            goToSecondTaskDetailsActivity.putExtra(TASK_DETAILS_TITLE_TAG, taskTitle);
//
//            startActivity(goToSecondTaskDetailsActivity);
//        });
//    }
//
//    public void thirdTaskButtonFunction() {
//        Button thirdTaskButton = findViewById(R.id.mainActivityThirdTaskButton);
//        thirdTaskButton.setOnClickListener(v -> {
//            Intent goToThirdTaskDetailsActivity = new Intent(MainActivity.this, TaskDetailsActivity.class);
//            String taskTitle = thirdTaskButton.getText().toString();
//            goToThirdTaskDetailsActivity.putExtra(TASK_DETAILS_TITLE_TAG, taskTitle);
//
//            startActivity(goToThirdTaskDetailsActivity);
//        });
//    }

    public void setUpRecyclerView(List<Task> taskList) {
        RecyclerView taskListRecyclerView = findViewById(R.id.mainActivityTaskRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskListRecyclerView.setLayoutManager(layoutManager);

        adapter = new TaskListRecyclerViewAdapter(taskList, this);
        taskListRecyclerView.setAdapter(adapter);
    }


}