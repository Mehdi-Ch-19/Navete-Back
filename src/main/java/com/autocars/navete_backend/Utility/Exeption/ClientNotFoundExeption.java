package com.autocars.navete_backend.Utility.Exeption;

public class ClientNotFoundExeption extends RuntimeException{
    public ClientNotFoundExeption(String message){
        super(message);
    }
}
