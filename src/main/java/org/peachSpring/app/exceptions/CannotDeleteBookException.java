package org.peachSpring.app.exceptions;

public class CannotDeleteBookException extends RuntimeException{
    public CannotDeleteBookException() {
        super();
    }

    public CannotDeleteBookException(String message) {
        super(message);
    }
}
