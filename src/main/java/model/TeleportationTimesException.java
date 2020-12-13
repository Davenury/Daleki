package model;

public class TeleportationTimesException extends Exception{
    public TeleportationTimesException(){
        super("You have no more teleportations!");
    }
}
