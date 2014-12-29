package com.example.syst3m.hwproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by syst3m on 12/28/14.
 */
public class ConnectionToServerThread extends Thread {
    Socket socket = null;
    String port = "63403";
    String ip = "192.168.0.10";
   //String ip = "130.229.133.178";
   // String ip = "193.11.93.238";


    PrintWriter out;
    BufferedReader bufferedReaderFrom;
    String msgFromServer;
    String[] commands;
    MyActivity activity;
    PopupRequest popupRequest;

    public ConnectionToServerThread(MyActivity activity){
        this.activity = activity;
    }

    public void run(){
        try {
            socket = new Socket(ip, Integer.parseInt(port));
            bufferedReaderFrom = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (socketReady()){
                msgFromServer =  bufferedReaderFrom.readLine();
                System.out.println("häääääääääääääär " + msgFromServer);
                commands = msgFromServer.split(" ");
                checkCommend(commands[0]);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket(){
        return socket;

    }
    public boolean socketReady(){
        if(socket != null && socket.isConnected()){
            try {
                out = new PrintWriter(getSocket().getOutputStream(),true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public void sendToServer(String msg){
        out.println(msg);
        out.flush();
    }

    public void closeConnection(){
        out.close();
        try {
            bufferedReaderFrom.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getMassage(){
        return msgFromServer;
    }

    public void checkCommend(String command){
      //  System.out.println("showDialog" + command);

        if(command.equals("userList")){
            ArrayList<String> userList = new ArrayList<String>();
                for(String ss : commands){
                    userList.add(ss);
                  //  System.out.println(ss);
                }
        commands = null;
        activity.updateListView(userList);

        }
        if(command.equals("request")){
            activity.showDialog(commands[1]);
        }
    }
}
