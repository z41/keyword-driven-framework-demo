package com.demo.hybrid.core.exceptions;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
