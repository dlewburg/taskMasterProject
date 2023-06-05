package com.dlewburg.taskmanagerandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dlewburg.taskmanagerandroid.R;

public class ProfileEditActivity extends AppCompatActivity {
    public static final String PROFILE_USERNAME_TAG = "username";

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        showSharedPreferencesData(preferences);
        saveButtonFunction(preferences);
    }

    public void saveButtonFunction(SharedPreferences preferences) {

        Button saveButton = findViewById(R.id.profileEditActivitySaveButton);
        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor preferenceEditor = preferences.edit();
            EditText usernameEditText = findViewById(R.id.profileEditActivityUserEditText);
            String usernameString = usernameEditText.getText().toString();

            preferenceEditor.putString(PROFILE_USERNAME_TAG, usernameString);
            preferenceEditor.apply();

            Toast.makeText(this, "Username Saved!", Toast.LENGTH_SHORT).show();
        });
    }

    public void showSharedPreferencesData(SharedPreferences preferences){
        String usernameData = preferences.getString(PROFILE_USERNAME_TAG, "");
        ((EditText) findViewById(R.id.profileEditActivityUserEditText)).setText(usernameData);
    }
}