package com.dlewburg.taskmanagerandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.dlewburg.taskmanagerandroid.MainActivity;
import com.dlewburg.taskmanagerandroid.R;

public class LoginActivity extends AppCompatActivity {
  public static final String TAG = "LoginActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    setUpLoginButton();
    setUpSignUpButton();
  }

  public void setUpLoginButton() {
    Intent callingIntent = getIntent();
    String userEmail = callingIntent.getStringExtra(VerifyAccountActivity.VERIFICATION_EMAIL_TAG);
    EditText loginEmail = findViewById(R.id.loginActivityEmailEditText);
    if (loginEmail != null) {
      loginEmail.setText(userEmail);
    }

    EditText loginPassword = findViewById(R.id.loginActivityPasswordEditText);
    Button loginButton = findViewById(R.id.loginActivityLoginButton);

    loginButton.setOnClickListener(v -> {


    Amplify.Auth.signIn(loginEmail.getText().toString(),
            loginPassword.getText().toString(),
            success -> {
              Log.i(TAG, "Sign In Successful: " + success.toString());
              Intent goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
              startActivity(goToMainActivity);
            },
            failure -> {
              Log.i(TAG, "Sign in Failed: " + failure.toString());
            });
    });
  }
  public void setUpSignUpButton(){
    Button signUpButton = findViewById(R.id.loginActivitySignUpButton);

    signUpButton.setOnClickListener(v -> {
      Intent goToSignUpActivity = new Intent(LoginActivity.this, SignUpActivity.class);
      startActivity(goToSignUpActivity);
    });
  }
}