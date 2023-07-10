package com.dlewburg.taskmanagerandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.dlewburg.taskmanagerandroid.R;

public class SignUpActivity extends AppCompatActivity {
  public static final String TAG = "SignUpActivity";
  public static final String SIGN_UP_EMAIL_TAG = "Sign_Up_Email_Tag";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    signUpSetUpButton();
  }

  public void signUpSetUpButton() {
    Button signUpButton = findViewById(R.id.signUpActivityySignUpButton);
    String userEmail = ((EditText)findViewById(R.id.signUpActivityEmailEditText)).getText().toString();
    String userPassword = ((EditText)findViewById(R.id.signUpActivityPasswordEditText)).getText().toString();
    signUpButton.setOnClickListener(v -> {
      Amplify.Auth.signUp(userEmail, //email address is username
            userPassword,
            AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), userEmail)
//                .userAttribute(AuthUserAttributeKey.nickname(), "Dasha")
                .build(),
            success -> {
              Log.i(TAG, "Successfully Signed Up: " + success.toString());
              // move to the verify account activity and pass email as intent extra(in theory)
              Intent goToVerificationIntent = new Intent(SignUpActivity.this, VerifyAccountActivity.class);
              goToVerificationIntent.putExtra(SIGN_UP_EMAIL_TAG, userEmail);
              startActivity(goToVerificationIntent);
            },
            failure -> {
              Log.i(TAG, "Sign up Failed: " + "ezdaisy2707@yahoo.com " + "with message: " + failure.toString());
            }
        );
    });
  }
}