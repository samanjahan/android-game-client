package com.example.syst3m.hwproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by syst3m on 12/29/14.
 */
public class PopupRequest extends Activity implements Serializable{
    Button okButton,cancelButton;
    TextView requestTextView;
    String requestUser;
    String userName;
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
           requestUser = bundle.getString("request");
            userName = bundle.getString("userName");
            requestTextView.setText("Do you want play with " + requestUser + "?");
        }

    }

    public void setRequestTextView(String msg){
        requestTextView.setText("setRequestTextView" + msg);
    }
    public void exit(View view){
        finish();
    }

    public void accept(View view){
        System.out.println("Accept metod i popuprequest, innan");
        ConnectionToServerThread.sendToServer("accepted " + requestUser + " " + userName );
        finish();
    }
}
