package com.disneyAPI.exceptions;

public class CharacterNotFoundException extends RuntimeException{
    public  CharacterNotFoundException(String msg){
        super(msg);
    }
}
