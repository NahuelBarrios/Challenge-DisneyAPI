package com.disneyAPI.exceptions;

public class GenderNotFoundException extends RuntimeException{
    public GenderNotFoundException(String msg){
        super(msg);
    }
}
