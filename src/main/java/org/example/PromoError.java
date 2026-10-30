package org.example;

//ошибка в неправильности наличия или отсутствия товаров

public class PromoError extends ShopExceptions{
    public PromoError(String message){
        super (message);
    }
}
