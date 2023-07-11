package com.dlewburg.taskmanagerandroid.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

//import android.content.Intent;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.LocationRequest;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AddTaskActivity extends AppCompatActivity {
    public static final String TAG = "add_task_tag";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;
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

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        fusedLocationProviderClient.flushLocations();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.e(TAG, "Application does not have access to either ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION!");
            return;
        }

        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }
        }).addOnSuccessListener(location -> {
            if(location == null) {
                Log.e(TAG, "Location callback was null");
            } else {
                String currentLatitude = Double.toString(location.getLatitude());
                String currentLongitude = Double.toString(location.getLongitude());
                Log.i(TAG, "Our current latitude: " + currentLatitude);
                Log.i(TAG, "Our current longitude: " + currentLongitude);
            }
        });

        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
            .build();

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                try {
                    String address = geocoder.getFromLocation(
                            locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude(),
                            1)
                        .get(0)
                        .getAddressLine(0);

                    Log.i(TAG, "Repeating  current location is: " + address);
                } catch (IOException ioe) {
                    Log.e(TAG, "Could not get subscribed location: " + ioe.getMessage());
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());

        addingTaskButtonFunction();


    }

    @SuppressLint("SetTextI18n")
    public void addingTaskButtonFunction() {
        Button addTaskSubmitButton = findViewById(R.id.addTaskActivityAddTaskButton);
        addTaskSubmitButton.setOnClickListener(v -> {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
              && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Application does not have access to either ACCESS_FINE_LOCATION or ACCESS_COURSE_LOCATION");
                return;
            }
            TextView addTaskSubmitted = findViewById(R.id.addTaskActivitySubmitTextView);
            addTaskSubmitted.setText("TASK SUBMITTED!");
//                Intent goToAllTaskActivity = new Intent(AddTaskActivity.this, AllTasksActivity.class);
//
//                startActivity(goToAllTaskActivity);
            team = teamList.stream().filter(e -> e.getName().equals(teamString)).collect(Collectors.toList()).get(0);

            TaskStatus newStatus = TaskStatusUtility.taskStatusFromString(taskStatusSpinner.getSelectedItem().toString());

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                Task newTask = null;
                if(location == null) {
                    Log.e(TAG, "Location callback was NULL!!");
                } else {
                    String currentLat = Double.toString(location.getLatitude());
                    String currentLong = Double.toString(location.getLongitude());
                    Log.i(TAG, "LATITUDE: " + location.getLatitude());
                    Log.i(TAG, "LONGITUDE: " + location.getLongitude());

                    newTask = Task.builder()
                        .title(getTitle().toString())
//                      .body(get.toString())
                        .status(newStatus)
                        .team(team)
//                        .latitude(currentLat)
//                        .longitude(currentLong)
                        .build();
                }
                Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    successResponse -> Log.i(TAG, "AddTaskActivity.onCreate() : added a task"),
                    failureResponse -> Log.i(TAG, "AddTaskActivity.onCreate() : adding task failed" + failureResponse)
                );
            });

        });
    }
}

