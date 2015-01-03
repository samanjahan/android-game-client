package com.example.syst3m.hwproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Game extends Activity {

    Button sendButton;
    TextView questionTextView,pointLabelTextView,pointTextView;
    EditText answerEditText;
    private String question;
    private String point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sendButton = (Button) findViewById(R.id.send);
        questionTextView = (TextView) findViewById(R.id.questionText);
        answerEditText = (EditText) findViewById(R.id.question);
        pointLabelTextView = (TextView) findViewById(R.id.textViewPointLabel);
        pointTextView = (TextView) findViewById(R.id.textViewPoint);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            question = bundle.getString("msg");
            point = bundle.getString("point");
        }
        questionTextView.setText(question);
        pointTextView.setText(point);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
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
        Intent intentMessage=new Intent();
        intentMessage.putExtra("MESSAGE","exit");
        setResult(2,intentMessage);
        finish();
    }

    public void send(View view){
        Intent intentMessage=new Intent();
        intentMessage.putExtra("MESSAGE",answerEditText.getText().toString());
        setResult(2,intentMessage);
        finish();
    }
}
