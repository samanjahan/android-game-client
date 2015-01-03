package com.example.syst3m.hwproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class LeavesGamePopup extends Activity {

    TextView msgTextView;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaves_game_popup);
        msgTextView = (TextView) findViewById(R.id.msgTextView);
        okButton = (Button) findViewById(R.id.okButton);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        String msgLeaves;
        if(bundle != null){
            msgLeaves = bundle.getString("msg");
            msgTextView.setText(msgLeaves + " has leaves the game!");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.leaves_game_popup, menu);
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

    public void exit(View view){
        finish();
    }
}
