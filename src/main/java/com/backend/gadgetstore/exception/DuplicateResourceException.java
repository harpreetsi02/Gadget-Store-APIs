package com.backend.gadgetstore.exception;

public class DuplicateResourceException extends RuntimeException{

    public DuplicateResourceException(String resource, String name){
        super(resource + " already exists with name: " + name);
    }
}
