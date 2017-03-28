package common;

/**
 * Created by Greeting on 2017/3/28.
 */
public class RPCException extends Exception{
    public RPCException() {
        super();
    }

    public RPCException(String message) {
        super(message);
    }
}
