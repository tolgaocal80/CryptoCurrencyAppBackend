package com.tolgaocal80.bayzat.exception;


public class UnsupportedCurrencyCreationException extends Exception{

    public UnsupportedCurrencyCreationException(String coinName){
        super("Unsupported Currency : "+coinName);
    }

}
