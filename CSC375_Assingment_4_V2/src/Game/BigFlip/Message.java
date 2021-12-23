package Game.BigFlip;

import java.util.ArrayList;
import java.util.BitSet;
import java.io.Serializable;

public class Message implements Serializable{
    private BitSet gameState = new BitSet();
    private ArrayList<Integer> moves = new ArrayList<>();
    private int messageType = 0;

    public Message(BitSet gameState,ArrayList<Integer> moves,int messageType){
        this.gameState = gameState;
        this.moves = moves;
        this.messageType = messageType;
    }
    public Message(BitSet gameState){
        this.gameState = gameState;
    }

    public Message(int messageType){
        this.messageType = messageType;
    }

    public Message(int messageType, ArrayList<Integer> moves){
        this.messageType  = messageType;
        this.moves = moves;
    }

    public Message(){

    }

    public void setGameState(BitSet gameState) {
        this.gameState = gameState;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void setMoves(ArrayList<Integer> moves) {
        this.moves = moves;
    }

    public ArrayList<Integer> getMoves() {
        return moves;
    }

    public BitSet getGameState() {
        return gameState;
    }

    public int getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "gameState=" + gameState +
                ", moves=" + moves +
                ", messageType=" + messageType +
                '}';
    }
}
