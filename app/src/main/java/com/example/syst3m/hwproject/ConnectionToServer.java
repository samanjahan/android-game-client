package com.example.syst3m.hwproject;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by syst3m on 12/18/14.
 */
public class ConnectionToServer extends AsyncTask<Void,Void,Socket> {
    @Override
    protected Socket doInBackground(Void... voids) {
        Socket socket = null;
        String port = "63403";
        String ip = "192.168.0.10";
       // String ip = "130.229.133.178";
        try {
            socket = new Socket(ip, Integer.parseInt(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
}
