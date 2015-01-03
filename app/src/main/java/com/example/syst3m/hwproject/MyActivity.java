package com.example.syst3m.hwproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class MyActivity extends Activity implements Serializable{
    Button button;
    Button exitButton;
    ListView userView;
    EditText editUserName;
    ConnectionToServerThread connection;
    String userName;
    ArrayAdapter arrayAdapter;
    ArrayList<String> userList = new ArrayList<String>();
    Game game = new Game();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        button = (Button) findViewById(R.id.ok);
        editUserName = (EditText) findViewById(R.id.userName);
        userView = (ListView) findViewById(R.id.listView);
        exitButton = (Button) findViewById(R.id.exit);
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

    public void create(View view){
        userName = String.valueOf(editUserName.getText());
        editUserName.setEnabled(false);
        connection.sendToServer(userName);
  //      testStatus.setText("You are logging " +  userName);
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


    public void exit(View view) {
        try {
            connection.sendToServer("quit");
            connection.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
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
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

    }

    public void switchToGameMode(String msg, String point){
        Intent intent = new Intent(getApplicationContext(),Game.class);
        intent.putExtra("msg",msg);
        intent.putExtra("point",point);
        startActivityForResult(intent, 2);
    }

    public void switchToLeavesMode(String userName){
        Intent intent = new Intent(getApplicationContext(),LeavesGamePopup.class);
        intent.putExtra("msg",userName);
        startActivity(intent);

    }

    public void switchToResultMode(String status , String msg){
        Intent intent = new Intent(getApplicationContext(),ResultPopup.class);
        intent.putExtra("status",status);
        intent.putExtra("msg",msg);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        String message = data.getStringExtra("MESSAGE");
        if(message.equals("exit")){
            connection.sendToServer("leaves");
        }
        connection.sendToServer("answer " + message);
        System.out.println(message);
    }
}
