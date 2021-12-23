package com.mycompany.flipgame2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class stopThread extends Thread {
    ServerSocket socket;

    public stopThread(ServerSocket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
