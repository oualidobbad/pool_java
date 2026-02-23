package logic;

public class IllegalTransactionException extends RuntimeException {

    public IllegalTransactionException(String message) {
        super(message);
    }
}
