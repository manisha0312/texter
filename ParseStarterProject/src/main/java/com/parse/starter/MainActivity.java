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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity {

  Boolean signupmodeisActive = false;
  TextView textView;
  EditText username;
  EditText password;
  public void redirect(){
    if (ParseUser.getCurrentUser()!=null){
      Intent intent=new Intent(getApplicationContext(),UserListActivity.class);
      startActivity(intent);
    }
  }

// for login
  public void login(View view) {
    Log.i("info", "done");
    Button button=findViewById(R.id.Signup);
    TextView textView=findViewById(R.id.login);//for login

// code for the switching between login and signup
    if(signupmodeisActive){
          signupmodeisActive=false;
          textView.setText("or, Login");
          button.setText(" Signup");
    }
    else {
      signupmodeisActive=true;
      textView.setText("or, Signup");
      button.setText("Login");

    }

  }
  public void signupobclick(View view){
    username = findViewById(R.id.username);
    password = findViewById(R.id.Password);
if(signupmodeisActive){
      ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {
          if (e==null){
            Log.i("user ","Loggedin ");
            redirect();
          }
          else
          {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });
  }
  //for signup
  else{

      ParseUser user = new ParseUser();
      user.setUsername(username.getText().toString());
      user.setPassword(password.getText().toString());
      user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null) {
            if (username.getText().toString().matches("") || password.getText().toString().matches("")) {
              Toast.makeText(MainActivity.this, "A username and passwod requied", Toast.LENGTH_SHORT).show();
            } else {
              Log.i("Sign", "up");
              Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
              redirect();
            }
          } else {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle ( "TEXTER" );
    redirect();
  }
}