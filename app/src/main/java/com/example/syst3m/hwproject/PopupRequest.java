package com.example.syst3m.hwproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by syst3m on 12/29/14.
 */
public class PopupRequest extends Activity {
    Button okButton,cancelButton;
    TextView requestTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_game_request);
        okButton= (Button) findViewById(R.id.ok);
        cancelButton = (Button) findViewById(R.id.cancel);
        requestTextView = (TextView) findViewById(R.id.textView);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String request = bundle.getString("request");
            requestTextView.setText("Do you want play with " + request + "!");
        }

    }

    public void setRequestTextView(String msg){
        requestTextView.setText("setRequestTextView" + msg);
    }
    public void exit(View view){
        finish();
    }
}
