package org.example;
//родительский класс для сбора ошибок
public class ShopExceptions extends Exception{
    public ShopExceptions(String message){
        super (message);
    }
}

