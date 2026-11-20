package com.backend.gadgetstore.exception;

public class ResourceInUseException extends RuntimeException{

    public ResourceInUseException(String message){
        super(message);
    }
}
