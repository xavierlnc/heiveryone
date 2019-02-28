package Exceptions;

public class IdentifiantAlreadyExistsException extends Exception {

    public IdentifiantAlreadyExistsException(String message) {
        super(message);
    }

    public IdentifiantAlreadyExistsException() {
        super();
    }
}
