package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    ArrayList<String> users=new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.logout){
        ParseUser.logOut();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        }
        // for game

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setTitle("USERS");
        ListView listView=(ListView) findViewById(R.id.listviewuser);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),ChatActivity.class);// from this we o to chatactivity or next page
                intent.putExtra("username",users.get(i));//we are get the username index
                startActivity(intent);
            }
        });
      //  users.add("Manisha");
        users.clear();
        arrayAdapter =new ArrayAdapter(this,android.R.layout.simple_list_item_1,users);
        listView.setAdapter(arrayAdapter);
        ParseQuery<ParseUser> q=ParseUser.getQuery();
        q.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        q.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e==null && objects.size()>0){
                    for (ParseUser user:objects){
                        users.add(user.getUsername());

                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });


    }
}
