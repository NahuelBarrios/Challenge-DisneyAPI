package com.disneyAPI.exceptions;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(String msg){
        super(msg);
    }
}
