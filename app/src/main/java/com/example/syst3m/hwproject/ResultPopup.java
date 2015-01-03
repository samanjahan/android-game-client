package com.example.syst3m.hwproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ResultPopup extends Activity {
    TextView msgTextView , statusTextView;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulut_popup);
        msgTextView = (TextView) findViewById(R.id.msgTextView);
        statusTextView = (TextView) findViewById(R.id.statusTextView);
        okButton = (Button) findViewById(R.id.okButton);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        String msg;
        String status;
        if(bundle != null){
            status = bundle.getString("status");
            msg = bundle.getString("msg");
            statusTextView.setText(status);
            msgTextView.setText(msg);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resulut_popup, menu);
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
