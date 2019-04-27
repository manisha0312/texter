package com.parse.starter;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TimePicker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.List;


public class TextscheduleActivity extends AppCompatActivity implements
        View.OnClickListener {
// schduling text and time
        Button btnDatePicker, btnTimePicker;
        EditText txtDate, txtTime;
        private int mYear, mMonth, mDay, mHour, mMinute;
    String date;
    String time;
    //String activeuser;
    ArrayList<String> messages=new ArrayList<>();
    ArrayAdapter arrayAdapter;
    public  void Done(View view){

        Button button=findViewById ( R.id.Doneschedule );
        date =  txtDate.getText().toString();
        time =  txtTime.getText().toString();
        Intent intent=getIntent();
        String activeuser=intent.getStringExtra("recipient");//we are using this for reciver

        Log.i ( "click",date+":"+time );

        final EditText editText=findViewById(R.id.chatEditTextfromSchedule);
        final String messagecontent=editText.getText().toString();
        ParseObject message=new ParseObject("Message");
        message.put("sender",ParseUser.getCurrentUser().getUsername());
        message.put("recipient", activeuser);
        message.put("message",messagecontent);
        LocalDateTime now = LocalDateTime.now();
        message.put ( "timeofdelivery",date+"T"+time);

        editText.setText("");// for clear the message form edit text
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Log.i("Sucess","done");
                    //Toast.makeText(ChatActivity.this,"Done",Toast.LENGTH_SHORT).show();
                    // now i am add the message to the list user
                    messages.add(messagecontent);

//                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
//        Intent intent1=new Intent(getApplicationContext(),ChatActivity.class);
//       startActivity ( intent1 );

    }

            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_textschedule );
        setTitle ( "TEXT SCHEDULE" );

                btnDatePicker=(Button)findViewById(R.id.btn_date);
                btnTimePicker=(Button)findViewById(R.id.btn_time);
                txtDate=(EditText)findViewById(R.id.in_date);
                txtTime=(EditText)findViewById(R.id.in_time);

                btnDatePicker.setOnClickListener(this);
                btnTimePicker.setOnClickListener(this);
//                Intent intent1=new Intent(getApplicationContext(),ChatActivity.class);
//                startActivity ( intent1 );
            }





        @Override
        public void onClick(View v) {

                if (v == btnDatePicker) {

                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                                new DatePickerDialog.OnDateSetListener() {

                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {
                                            if(monthOfYear<9 && dayOfMonth <10) {

                                                txtDate.setText(year + "-" + "0"+(monthOfYear + 1) + "-" +"0"+ dayOfMonth);

                                        }
                                            else if(monthOfYear < 9) {
                                                txtDate.setText(year + "-" + "0"+(monthOfYear + 1) + "-" + dayOfMonth);
                                            }
                                            else if(dayOfMonth < 10) {
                                                txtDate.setText(year + "-" + (monthOfYear + 1) + "-" +"0"+ dayOfMonth);
                                            }
                                            else {
                                                txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                            }
                                        }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                }
                if (v == btnTimePicker) {

                        // Get Current Time
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                                new TimePickerDialog.OnTimeSetListener() {

                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay,
                                                              int minute) {
                                            if(hourOfDay<10 && minute<10) {

                                                txtTime.setText("0"+hourOfDay + ":" + "0"+minute+":00.000");
                                            }
                                            else if(minute<10) {

                                                txtTime.setText(hourOfDay + ":" +"0"+ minute+":00.000");
                                        }else if(hourOfDay<10) {

                                                txtTime.setText("0"+hourOfDay + ":" + minute+":00.000");
                                            }
                                            else {
                                                txtTime.setText(hourOfDay + ":" + minute+":00.000");
                                            }
                                        }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();
                }
        }

        }




