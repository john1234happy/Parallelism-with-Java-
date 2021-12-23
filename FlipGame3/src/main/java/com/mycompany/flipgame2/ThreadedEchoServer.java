package com.mycompany.flipgame2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class ThreadedEchoServer implements Runnable{

    static final int PORT = 1978;
    public static boolean stopFlag = false;
    private BitSet bitset = null;


    public ThreadedEchoServer(BitSet bitset){
        this.bitset = bitset;
        stopFlag = false;
    }

    public  void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        Message FirstMessage = new Message(bitset);
        FlipGame.taskBoard.pushTask(FirstMessage);


        System.out.println("Game Starting State : " + FirstMessage.getGameState());

       try {
           serverSocket = new ServerSocket(PORT);
       } catch (IOException e) {
           e.printStackTrace();
       }

       while (!stopFlag) {
           if(FlipGame.taskBoard.getFound_flag()) {
               new stopThread(serverSocket).start();
           }
           try {
               socket = serverSocket.accept();
           } catch (IOException e) {
               stopFlag = true;
               System.out.println("I/O error: " + e);
               break;
           }
           // new thread for a client
           new EchoThread(socket).start();
           assert socket != null;

       }
        System.out.println("finished");
    }public static BitSet random_bitset(){
        BitSet bit = new BitSet();
        Random rand = new Random();
        for(int i = 0;i<9;i++){
            if(rand.nextBoolean())
                bit.flip(i);
        }
        return bit;
    }
}
