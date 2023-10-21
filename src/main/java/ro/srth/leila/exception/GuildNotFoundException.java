package ro.srth.leila.exception;

public class GuildNotFoundException extends Exception{
    public GuildNotFoundException(){
        super();
    }

    public GuildNotFoundException(String msg){
        super(msg);
    }
}
