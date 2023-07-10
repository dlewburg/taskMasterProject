package com.dlewburg.taskmanagerandroid;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;

//PLUG IN ORDER -> COGNITIO #1
//MUST BE BEFORE AWS AMPLIFY
public class TaskMasterApplication extends Application {
  public static final String TAG = "TaskMasterApplication";

  @Override
  public void onCreate(){
    super.onCreate();

    try{
//      COGNITO HERE FIRST
      Amplify.addPlugin(new AWSCognitoAuthPlugin());
      Amplify.addPlugin(new AWSApiPlugin());
      Amplify.configure(getApplicationContext());
    } catch (AmplifyException ae) {
      Log.e(TAG, "Error Initializing Amplify: " + ae.getMessage(), ae);
    }
  }
}
