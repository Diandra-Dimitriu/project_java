package org.example.visuals;

public class ZoneException extends Exception {

    public ZoneException(String message) {
        super(message);
    }

    public ZoneException(String message, Throwable cause) {
        super(message, cause);
    }
}

