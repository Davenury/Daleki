package model;

public class EndGameException extends Exception{
    public EndGameException(){
        super("Game lost");
    }
}
