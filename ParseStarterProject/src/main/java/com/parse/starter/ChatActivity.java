package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.sql.Time;
import java.text.DateFormat;
//import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    String activeuser="";
    ArrayList<String> messages=new ArrayList<>();
    ArrayAdapter arrayAdapter;

            public  void sendchat(View view){
                final EditText editText=findViewById(R.id.chatEditText);
                final String messagecontent=editText.getText().toString();
                ParseObject message=new ParseObject("Message");
                message.put("sender",ParseUser.getCurrentUser().getUsername());
                message.put("recipient",activeuser);
                message.put("message",messagecontent);
           LocalDateTime now = LocalDateTime.now();
                    message.put ( "timeofdelivery",now.toString ());

                editText.setText("");// for clear the message form edit text
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            Log.i("Sucess","done");
                            //Toast.makeText(ChatActivity.this,"Done",Toast.LENGTH_SHORT).show();
                            // now i am add the message to the list user
                            messages.add(messagecontent);

                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
            public void schudle(View view){

                final EditText editText=findViewById(R.id.chatEditText);
                final String messagecontent=editText.getText().toString();
                ParseObject message=new ParseObject("Message");
                message.put("sender",ParseUser.getCurrentUser().getUsername());
                message.put("recipient",activeuser);
                message.put("message",messagecontent);


                Intent intent=new Intent(getApplicationContext(),TextscheduleActivity.class);// it take use on the thext schedule activity
                intent.putExtra ( "recipient",activeuser );
                startActivity(intent);

            }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
      //  setTitle("CHAT");
        Intent intent=getIntent();
        activeuser=intent.getStringExtra("username");//we are using this for reciver
        setTitle("Chat with "+activeuser);
       Log.e("info",activeuser);
        //hee we are working with list view
        ListView listView=findViewById(R.id.chatlistview);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,messages);//messages is obj of array
        listView.setAdapter(arrayAdapter);
        ParseQuery<ParseObject> query1=new ParseQuery<ParseObject>("Message");
        // we are use double query because one message is sending other is reciving
        query1.whereEqualTo("sender",ParseUser.getCurrentUser().getUsername());
        query1.whereEqualTo("recipient",activeuser);
        ParseQuery<ParseObject> query2=new ParseQuery<ParseObject>("Message");
        query2.whereEqualTo("recipient",ParseUser.getCurrentUser().getUsername());
        query2.whereEqualTo("sender",activeuser);

        List<ParseQuery<ParseObject>> queries=new ArrayList<ParseQuery<ParseObject>>() ;// adding both the query tov array list
        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> query=ParseQuery.or(queries);//we are adding the list in this or use for combine the indivisual query
        query.orderByAscending("createdAt");
       // query.setLimit(3);
        final LocalDateTime now2 = LocalDateTime.now();
        //final SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd+"T"+HH:mm" );
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null && objects.size()>0){
                    messages.clear();
                    for (ParseObject message:objects){
                        String tod = message.getString ( "timeofdelivery" );
                        LocalDateTime dd = LocalDateTime.parse ( tod );

                       if( now2.compareTo ( dd ) > 0) {//when we schedule the time then dd is timeofdelivery and when we send normal message dd is send time now2 is receiver time
                        String messagecontent=message.getString("message");// where it is containing the message
                        if (!message.getString("sender").equals(ParseUser.getCurrentUser().getUsername())){// adding this for recipitent
                            messagecontent='>'+messagecontent;

                        }
                        messages.add(messagecontent);

                    }
                }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });


    }}
