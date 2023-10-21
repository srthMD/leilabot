package ro.srth.leila.exception;

public class UnsucessfulReadException extends Exception{
    public UnsucessfulReadException(){
        super();
    }

    public UnsucessfulReadException(String msg){
        super(msg);
    }
}
