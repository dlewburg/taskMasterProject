package com.dlewburg.taskmanagerandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.dlewburg.taskmanagerandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ProfileEditActivity extends AppCompatActivity {
    public static final String PROFILE_USERNAME_TAG = "username";
    public static final String TEAM_TAG = "team";
//    public static final String TEAM_TAG = "team";
    public static final String TAG = "settings_activity";
    Spinner taskTeamSpinner = null;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        //noinspection deprecation
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        showSharedPreferencesData(preferences);
        saveButtonFunction(preferences);
        populateTeamSpinner(preferences);
    }

    public void populateTeamSpinner(SharedPreferences preferences) {

        String teamString = preferences.getString(TEAM_TAG, "");
        CompletableFuture<List<Team>> teamsFuture = new CompletableFuture<>();
        List<Team> teamList = new ArrayList<>();
        List<String> teamListAsString = new ArrayList<>();
        taskTeamSpinner = findViewById(R.id.profileEditActivityTeamSpinner);
        Amplify.API.query(
            ModelQuery.list(Team.class),
            success -> {
                Log.i(TAG, "Read Teams successfully");
                for (Team team : success.getData()) {
                    teamList.add(team);
                }
                teamsFuture.complete(teamList);
                runOnUiThread(() -> {
                    teamListAsString.add("All");
                    for (Team team : teamList)
                        teamListAsString.add(team.getName());
                    taskTeamSpinner.setAdapter(new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        teamListAsString
                    ));
                    if (!teamString.isEmpty()) {
                        taskTeamSpinner.setSelection(teamListAsString.indexOf(teamString));
                    }
                });
            },
            failure -> Log.i(TAG, "Failed to read Teams")
        );
    }
    public void saveButtonFunction(SharedPreferences preferences) {

        Button saveButton = findViewById(R.id.profileEditActivitySaveButton);
        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor preferenceEditor = preferences.edit();
            EditText usernameEditText = findViewById(R.id.profileEditActivityUserEditText);
            String usernameString = usernameEditText.getText().toString();

            preferenceEditor.putString(PROFILE_USERNAME_TAG, usernameString);
            preferenceEditor.apply();
            String teamString = taskTeamSpinner.getSelectedItem().toString();

            preferenceEditor.putString(TEAM_TAG, teamString);

            Toast.makeText(this, "Username Saved!", Toast.LENGTH_SHORT).show();
        });
    }

    public void showSharedPreferencesData(SharedPreferences preferences){
        String usernameData = preferences.getString(PROFILE_USERNAME_TAG, "");
        ((EditText) findViewById(R.id.profileEditActivityUserEditText)).setText(usernameData);
    }
}