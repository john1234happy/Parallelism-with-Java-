package com.mycompany.flipgame2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EchoThread extends Thread {

    protected Socket socket;


    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {

        Message messin = new Message();
        Message messout = new Message();
        ObjectInputStream in  = null;
        ObjectOutputStream out = null;

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection.");
            e.printStackTrace();
            System.exit(1);
        }try{
            Object object = in.readObject();
            if(object instanceof Message){
                messin = (Message) object;
                int flag = messin.getMessageType();
                if(flag == 2) {
                    FlipGame.taskBoard.pushTask(messin);
                }else if(flag == 1){
                    if(FlipGame.taskBoard.board.isEmpty()) {
                        System.out.println("board is empty, they need to sleep");
                        messout.setMessageType(4);
                        out.writeObject(messout);
                    }else if (FlipGame.taskBoard.getFound_flag()){
                        System.out.println("i got the solution, yall are done");
                        System.out.println("this is the solution");
                        System.out.println("Solution :" + FlipGame.taskBoard.solution);
                        messout.setMessageType(5);
                        out.writeObject(messout);
                    }else {
                        FlipGame.taskBoard.peekTask().setMessageType(2);
                        out.writeObject(FlipGame.taskBoard.pullTask());
                    }
                }else if(flag == 3){
                    FlipGame.taskBoard.setSolution(messin);
                    System.out.println("YOOO we found the solution");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            out.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }return;

    }
}
