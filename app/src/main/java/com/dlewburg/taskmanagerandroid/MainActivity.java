package com.dlewburg.taskmanagerandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.dlewburg.taskmanagerandroid.activities.AddTaskActivity;
import com.dlewburg.taskmanagerandroid.activities.AllTasksActivity;
import com.dlewburg.taskmanagerandroid.activities.ProfileEditActivity;
//import com.dlewburg.taskmanagerandroid.activities.TaskDetailsActivity;
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
//    TaskListRecyclerViewAdapter taskListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // signs username up and sends email when application is run
        // comment out code when confirmation email is received and user is in cognito on AWS console

//        Amplify.Auth.signUp("ezdaisy2707@yahoo.com", //email address is username
//            "12qw!@QW",
//            AuthSignUpOptions.builder()
//                .userAttribute(AuthUserAttributeKey.email(), "ezdaisy2707@yahoo.com")
//                .userAttribute(AuthUserAttributeKey.nickname(), "Dasha")
//                .build(),
//            success -> {
//              Log.i(TAG, "Successfully Signed Up: " + success.toString());
//            },
//            failure -> {
//              Log.i(TAG, "Sign up Failed: " + "ezdaisy2707@yahoo.com " + "with message: " + failure.toString());
//            }
//        );

        // Run after getting confirmation code from email
        // Comment out after confirming email-> verify on cognito console

//        Amplify.Auth.confirmSignUp("ezdaisy2707@yahoo.com",
//            "816492", //verification code from email
//            success -> {
//              Log.i(TAG, "Sign Up Verified: " + success.toString());
//            },
//            failure -> {
//              Log.i(TAG, "Sign Up Failed: " + failure.toString());
//            });



        profileEditButtonFunction();
        setUpRecyclerView(taskList);
        addTaskButtonFunction();
        allTaskButtonFunction();

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        taskList.clear();

//        Team team1 = Team.builder()
//            .name("First Team")
//            .build();
//        Amplify.API.mutate(
//            ModelMutation.create(team1),
//            success -> Log.i(TAG, "Team Added Successfully"),
//            failure -> Log.i(TAG, "Failed to Add Team")
//        );
//
//        Team team2 = Team.builder()
//            .name("Second Team")
//            .build();
//        Amplify.API.mutate(
//            ModelMutation.create(team2),
//            success -> Log.i(TAG, "Teams Added Successfully"),
//            failure -> Log.i(TAG, "Failed to Add Team")
//        );
//
//        Team team3 = Team.builder()
//            .name("Third Team")
//            .build();
//        Amplify.API.mutate(
//            ModelMutation.create(team3),
//            success -> Log.i(TAG, "Teams Added Successfully"),
//            failure -> Log.i(TAG, "Failed to Add Team")
//        );

        //noinspection deprecation
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userProfileName = preferences.getString(ProfileEditActivity.PROFILE_USERNAME_TAG, "");
        ((TextView) findViewById(R.id.mainActivityMyTasksTextView)).setText(userProfileName + "'s Tasks");

        readTasksFromDatabase();
//        taskListRecyclerViewAdapter.updateTasksData(taskList);


    }

    public void profileEditButtonFunction() {
        ImageView profileEditButton = findViewById(R.id.mainActivityProfileEditButton);

        profileEditButton.setOnClickListener(v -> {
            Intent goToProfileEditIntent = new Intent(MainActivity.this, ProfileEditActivity.class);
            startActivity(goToProfileEditIntent);
        });
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NotifyDataSetChanged")
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
                runOnUiThread(() -> adapter.notifyDataSetChanged());
            },
            failure -> Log.i(TAG, "Failed to read Tasks from Database")
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


    public void setUpRecyclerView(List<Task> taskList) {
        RecyclerView taskListRecyclerView = findViewById(R.id.mainActivityTaskRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskListRecyclerView.setLayoutManager(layoutManager);

        adapter = new TaskListRecyclerViewAdapter(taskList, this);
        taskListRecyclerView.setAdapter(adapter);
    }


}

//        firstTaskButtonFunction();
//        secondTaskButtonFunction();
//        thirdTaskButtonFunction();

//        taskList.add(new Task("Interview for SWE Role", "Review DSA & How to WB", Task.TaskStatus.NEW));
//        taskList.add(new Task("Walk the dog", "Pick up Golum and walk him 10K steps", Task.TaskStatus.COMPLETE));
//        taskList.add(new Task("Finish Homework", "Complete labs, code challenges, and readings", Task.TaskStatus.IN_PROGRESS));



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