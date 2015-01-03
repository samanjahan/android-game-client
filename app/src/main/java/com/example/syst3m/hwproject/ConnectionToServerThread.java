package com.example.syst3m.hwproject;

import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by syst3m on 12/28/14.
 */
public class ConnectionToServerThread extends Thread implements Serializable {
   public static Socket socket = null;
    String port = "63403";
   // String ip = "130.237.238.174";
  // String ip = "130.229.133.178";
    String ip = "130.229.129.150";

    // String ip = "193.11.93.238";


    static PrintWriter out;
    private BufferedReader bufferedReaderFrom;
    private String msgFromServer;
    private String[] commands;
    private MyActivity activity;
    private Game game;

    public ConnectionToServerThread(MyActivity activity){
        System.out.println("Konstruktor: myactivity");
        this.activity = activity;
    }

    public ConnectionToServerThread(Game game){
        System.out.println("Konstruktor: game");
        this.game = game;
    }


    public void run(){
        try {
            socket = new Socket(ip, Integer.parseInt(port));
            bufferedReaderFrom = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (socketReady()){
                msgFromServer =  bufferedReaderFrom.readLine();
                System.out.println("häääääääääääääär " + msgFromServer);
                commands = msgFromServer.split(" ");
                checkCommand(commands[0]);
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

    public static void sendToServer(String msg){
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

    public void checkCommand(String command){
        if(command.equals("userList")){
            ArrayList<String> userList = new ArrayList<String>();
                for(String ss : commands){
                    userList.add(ss);
                }
        commands = null;
        activity.updateListView(userList);

        }
        if(command.equals("request")){
            activity.showDialog(commands[1]);
        }
        if(command.equals("question")){
            System.out.println("got question... " + commands[1]);
            StringBuilder st = new StringBuilder();
            for(int i = 1 ; i < commands.length - 1; ++i){
                st.append(commands[i] + " ");
            }
           int lastIndex = commands.length - 1;
            String lastElement = commands[lastIndex];
            System.out.println("lastElement " + commands[lastIndex]);
            activity.switchToGameMode(st.toString(),lastElement);

        }
        if(command.equals("leavesGame")){
            activity.switchToLeavesMode(commands[1]);
        }
        if (command.equals("loser")){
            StringBuilder st = new StringBuilder();
            for(int i = 1 ; i < commands.length; ++i){
                st.append(commands[i] + " ");
            }
            activity.switchToResultMode("Loser" ,st.toString());
            st = null;
        }
        if (command.equals("winner")){
            StringBuilder st = new StringBuilder();
            for(int i = 1 ; i < commands.length; ++i){
                st.append(commands[i] + " ");
            }
            System.out.println("WINNERRR " + st.toString());
            activity.switchToResultMode("Winner" , st.toString());
            st = null;
        }
        if (command.equals("samePoint")){
            StringBuilder st = new StringBuilder();
            for(int i = 1 ; i < commands.length; ++i){
                st.append(commands[i] + " ");
            }
            activity.switchToResultMode("Equal Score" ,st.toString());
            st = null;
        }
    }
}
