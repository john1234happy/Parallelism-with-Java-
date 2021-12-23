package Game.BigFlip;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.BitSet;
import java.util.Random;

public class ThreadedEchoServer {

    static final int PORT = 1978;
    public static taskBoard taskBoard = new taskBoard();
    public static boolean stopFlag = false;



    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        Message FirstMessage = new Message(random_bitset());
        taskBoard.pushTask(FirstMessage);


        System.out.println("Game Starting State : " + FirstMessage.getGameState());

       try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!stopFlag) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new EchoThread(socket).start();
        }
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
