/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean signUpModeActive = true;

    TextView loginTextView;

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.loginTextView){

            Button signUpBUtton = findViewById(R.id.signUpButton);

            if(signUpModeActive){

                signUpModeActive = false;

                signUpBUtton.setText("Log in");

                loginTextView.setText("or, Sign Up");

            } else {

                signUpModeActive = true;

                signUpBUtton.setText("Sign Up");

                loginTextView.setText("or, Log In");

            }

            Log.i("Switch","Was tapped");
        }
    }

    public void signUpClicked (View view){
      EditText usernameEditText = findViewById(R.id.usernameEditText);

      EditText passwordEditText = findViewById(R.id.passwordEditText);

      if(usernameEditText.getText().toString().matches("")|| passwordEditText.getText().toString().matches("") ){

          Toast.makeText(this, "Both Username and Password are Required", Toast.LENGTH_SHORT).show();

      } else {

          if(signUpModeActive){

              ParseUser user = new ParseUser();

          user.setUsername(usernameEditText.getText().toString());

          user.setPassword(passwordEditText.getText().toString());

          user.signUpInBackground(new SignUpCallback() {
              @Override
              public void done(ParseException e) {

                  if (e == null) {

                      Log.i("Sign Up", "Successfull Sign Up");

                  } else {

                      Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                  }
              }
          });
      } else {

          ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
              @Override
              public void done(ParseUser user, ParseException e) {
                  if(user != null){

                      Log.i("Login", "Okay");
                  }
              }
          });
            }
        }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    loginTextView = (TextView) findViewById(R.id.loginTextView);

    loginTextView.setOnClickListener(this);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}