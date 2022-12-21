package exception;

public class BadArgumentsException extends RuntimeException {

    public BadArgumentsException() {
        super();
    }

    public BadArgumentsException(String message) {
        super(message);
    }

}
