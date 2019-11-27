package com.ligen.extension.points;

public class ExtensionPointsException extends RuntimeException {

    private static final long serialVersionUID = -8122366169428234846L;

    public ExtensionPointsException(String message) {
        super(message);
    }

    public ExtensionPointsException(Throwable cause) {
        super(cause);
    }
}
