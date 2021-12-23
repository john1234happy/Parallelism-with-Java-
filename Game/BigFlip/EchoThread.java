package Game.BigFlip;

import java.io.*;
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
                    ThreadedEchoServer.taskBoard.pushTask(messin);
                }else if(flag == 1){
                    if(ThreadedEchoServer.taskBoard.board.isEmpty()) {
                        System.out.println("board is empty, they need to sleep");
                        messout.setMessageType(4);
                        out.writeObject(messout);
                    }else if (ThreadedEchoServer.taskBoard.getFound_flag()){
                        System.out.println("i got the solution yall are done");
                        System.out.println("this is the solution");
                        System.out.println("Solution :" + ThreadedEchoServer.taskBoard.solution);
                        messout.setMessageType(5);
                        out.writeObject(messout);
                    }else {
                        ThreadedEchoServer.taskBoard.peekTask().setMessageType(2);
                        out.writeObject(ThreadedEchoServer.taskBoard.pullTask());
                    }
                }else if(flag == 3){
                    ThreadedEchoServer.taskBoard.setSolution(messin);
                    System.out.println("YOOO we found teh solution");
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
