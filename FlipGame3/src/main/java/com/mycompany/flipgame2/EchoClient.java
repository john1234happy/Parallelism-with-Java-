package com.mycompany.flipgame2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.BitSet;

public class EchoClient {
    private static Socket echoSocket = null;
    private static ObjectInputStream in = null;
    private static ObjectOutputStream out = null;

    public static void main(String[] args) {

        System.out.println("starting");

        boolean solutionFound = false;

        while (!solutionFound) {
            Message messin = new Message(0);
            Message toManipulate = null;
            Message messout = new Message(0);
            ArrayList<Message> tasks = new ArrayList<>();
            makeConnection();
            System.out.println("New conncection");
            try {
                messout.setMessageType(1);
                out.writeObject(messout);
                Object object = in.readObject();
                System.out.println("got a new task to do from server");
                    if (object instanceof Message) {
                        messin = (Message) object;
                        int flag = messin.getMessageType();
                        toManipulate = messin;
                        if (flag == 2) {
                            for (int l = 0; l < 10; l++) {
                                if (!toManipulate.getMoves().contains(l)) {
                                    ArrayList<Integer> tempM = new ArrayList<>(toManipulate.getMoves());
                                    tempM.add(l);// adds new move to list of moves
                                    BitSet temp = flipBitz(l, toManipulate.getGameState());
                                    tasks.add(new Message(temp, tempM, 2)); // makes new child with new bitset cording to the move made.
                                }
                            }
                            for (int l = 0; l < tasks.size(); l++) {
                                makeConnection();
                                try {
                                    if (toManipulate.getGameState().isEmpty()) {
                                        messout.setMoves(toManipulate.getMoves());
                                        messout.setMessageType(3);
                                        out.writeObject(messout);
                                        out.flush();
                                        System.out.println("found a solution");
                                        System.out.println(toManipulate.toString());
                                    } else if (tasks.get(l).getGameState().isEmpty()) {
                                        messout.setMoves(toManipulate.getMoves());
                                        messout.setMessageType(3);
                                        out.writeObject(messout);
                                        out.flush();
                                        System.out.println("found a solution");
                                        System.out.println(toManipulate.toString());
                                    } else {
                                        if (tasks.get(l).getMoves().size() <= 9) {
                                            messout = tasks.get(l);
                                            out.writeObject(messout);
                                            out.flush();
                                            System.out.println("sending new tasks to server");
                                            System.out.println(tasks.get(l).toString());

                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (flag == 4) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("wakeing up");
                        }else if(flag == 5){
                            System.out.println("Welp NVM \nI need to shut down");
                            solutionFound = true;
                        }
                    }
                } catch(Exception e){
                e.printStackTrace();
            }
            }
        }
    public static void makeConnection(){
        String host = "172.16.20.124";
        int echoServicePortNumber = 1978;
        try {
            echoSocket = new Socket(host, echoServicePortNumber);
            out = new ObjectOutputStream(echoSocket.getOutputStream());
            in = new ObjectInputStream(echoSocket.getInputStream());


        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection.");
            e.printStackTrace();
            System.exit(1);
        }
    }
    private static BitSet flipBitz ( int biting, BitSet bits) {
        BitSet bit = (BitSet) bits.clone();
        if (biting == 0) {
            bit.flip(0);
            bit.flip(1);
            bit.flip(3);
        }
        if (biting == 1) {
            bit.flip(0);
            bit.flip(1);
            bit.flip(2);
            bit.flip(4);
        }
        if (biting == 2) {
            bit.flip(1);
            bit.flip(2);
            bit.flip(5);
        }
        if (biting == 3) {
            bit.flip(0);
            bit.flip(3);
            bit.flip(4);
            bit.flip(6);
        }
        if (biting == 4) {
            bit.flip(1);
            bit.flip(3);
            bit.flip(4);
            bit.flip(5);
            bit.flip(7);
        }
        if (biting == 5) {
            bit.flip(2);
            bit.flip(4);
            bit.flip(5);
            bit.flip(8);
        }
        if (biting == 6) {
            bit.flip(3);
            bit.flip(6);
            bit.flip(7);
        }
        if (biting == 7) {
            bit.flip(4);
            bit.flip(6);
            bit.flip(7);
            bit.flip(8);
        }
        if (biting == 8) {
            bit.flip(5);
            bit.flip(7);
            bit.flip(8);
        }
        return bit;
    }
}