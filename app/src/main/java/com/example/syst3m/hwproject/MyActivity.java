package com.example.syst3m.hwproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;


public class MyActivity extends Activity {
    Button button ;
    ListView userView;
    TextView testStatus;
    EditText editUserName;
    ConnectionToServerThread connection;
    String userName;
    ArrayAdapter arrayAdapter;
    ArrayList<String> userList = new ArrayList<String>();
    private static final int DIALOG_ALERT = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        button = (Button) findViewById(R.id.ok);
        editUserName = (EditText) findViewById(R.id.userName);
        testStatus = (TextView) findViewById(R.id.status);
        userView = (ListView) findViewById(R.id.listView);
        connection = new ConnectionToServerThread(this);
        connection.start();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userList);
        userView.setAdapter(arrayAdapter);
        userView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final String item = (String) adapterView.getItemAtPosition(position);
                connection.sendToServer("play " + item);
            }
        });
    }

    public void onClick(View view){
        userName = String.valueOf(editUserName.getText());
        editUserName.setEnabled(false);
        connection.sendToServer(userName);
        testStatus.setText("You are logging " +  userName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            connection.sendToServer("quit");
            connection.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateListView(ArrayList list){
        if(!userList.isEmpty()) {
          userList.clear();
        }
        for (int i = 1; i < list.size(); ++i) {
            userList.add(list.get(i).toString());
        }
        System.out.println(userList.size());
        runOnUiThread(new Runnable() {
            public void run() {
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showDialog(String msg){
       final String request = msg;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),PopupRequest.class);
                intent.putExtra("request",request);
                startActivity(intent);


            }
        });

    }
}
