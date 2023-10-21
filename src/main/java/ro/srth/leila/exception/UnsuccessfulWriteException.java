package ro.srth.leila.exception;

public class UnsuccessfulWriteException extends Exception{
    public UnsuccessfulWriteException(){
        super();
    }

    public UnsuccessfulWriteException(String msg){
        super(msg);
    }
}
