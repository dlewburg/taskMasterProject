package com.dlewburg.taskmanagerandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.dlewburg.taskmanagerandroid.R;

public class VerifyAccountActivity extends AppCompatActivity {
  public static final String TAG = "VerifyAccountActivity";
  public static final String VERIFICATION_EMAIL_TAG = "Verification_Email_Tag";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verify_account);

    setUpVerificationButton();
  }

  public void setUpVerificationButton() {
    Button verifyButton = findViewById(R.id.verifyAccountActivityVerifyButton);
    Intent callingIntent = getIntent();
    String userEmail = callingIntent.getStringExtra(SignUpActivity.SIGN_UP_EMAIL_TAG);
    EditText verifyEmail = findViewById(R.id.verifyAccountActivityEmailEditText);
    verifyEmail.setText(userEmail);

    verifyButton.setOnClickListener(v -> {
      String verifyNumber = ((EditText) findViewById(R.id.verifyAccountActivityNumberVerificationEditText)).getText().toString();

      Amplify.Auth.confirmSignUp(userEmail,
            verifyNumber,
            success -> {
              Log.i(TAG, "Sign Up Verified: " + success.toString());
              Intent goToLoginActivity = new Intent(VerifyAccountActivity.this, LoginActivity.class);
              goToLoginActivity.putExtra(VERIFICATION_EMAIL_TAG, userEmail);
              startActivity(goToLoginActivity);
            },
            failure -> {
              Log.i(TAG, "Sign Up Failed: " + failure.toString());
            });
    });
  }
}