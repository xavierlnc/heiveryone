package Exceptions;

public class InputsEmptyException extends Exception {

    public InputsEmptyException() {
        super();
    }

    public InputsEmptyException(String msg) {
        super(msg);
    }
}
