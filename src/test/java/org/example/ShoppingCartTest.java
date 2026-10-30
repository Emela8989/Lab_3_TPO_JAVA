package org.example;

import org.junit.jupiter.api.BeforeEach;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private Product[] data;
    private ShoppingCart cart;
    @BeforeEach
    public void setup() throws ElemCount{
        data = new Product[]{
                new Product("123", "Iphone 16", 60000, 200),
                new Product("124", "Sumsung Galaxy S8", 55000, 300),
                new Product("125", "Coffee machine", 20000, 150),
                new Product("126", "disk R18", 8000, 1000)
        };
        
        cart = new ShoppingCart();
    }

    @org.junit.jupiter.api.Test
    void add() throws Exception{
        Map<String, Integer> result = cart.add("123", 100, data);
        result = cart.add("124", 100, data);
        result = cart.add("124", 50, data);
        
        assertEquals(100, result.get("123"));
        assertEquals(150, result.get("124"));
        assertEquals(100, data[0].getStock());
        assertEquals(150, data[1].getStock());

        ElemCount exception = assertThrows(ElemCount.class, () -> {
            cart.add("125", 200, data);
        });
        
        assertEquals("Количество добавляемых товаров больше, чем есть на складе.", exception.getMessage());

        exception = assertThrows(ElemCount.class, () -> {
            cart.add("125", 0, data);
        });

        assertEquals("Нельзя добавить 0 единиц товара", exception.getMessage());

        ExcProduct excep = assertThrows(ExcProduct.class, () -> {
            cart.add("127", 200, data);
        });

        assertEquals("Данный товар отсутствует на складе.", excep.getMessage());
    }
    
    @org.junit.jupiter.api.Test
    void lessCount() throws Exception {
        Map<String, Integer> result = cart.add("123", 100, data);
        result = cart.add("126", 300, data);
        
        result = cart.lessСount("123", 50, data);
        result = cart.lessСount("126", 100, data);

        assertEquals(50, result.get("123"));
        assertEquals(200, result.get("126"));
        assertEquals(150, data[0].getStock());
        assertEquals(800, data[3].getStock());

        ElemCount exception = assertThrows(ElemCount.class, () -> {
            cart.lessСount("123", 100, data);
        });

        assertEquals("В корзине нет такого количества товара.", exception.getMessage());

        ExcProduct excep = assertThrows(ExcProduct.class, () -> {
            cart.lessСount("127", 100, data);
        });

        assertEquals("Данного товара нет в корзине.", excep.getMessage());
        
        result = cart.lessСount("123", 50, data);
        result = cart.lessСount("126", 200, data);
        assertEquals(Map.of(), result);
    }

    @org.junit.jupiter.api.Test
    void delProduct() throws Exception {
        Map<String, Integer> result = cart.add("123", 100, data);
        result = cart.add("126", 300, data);
        
        result = cart.delProduct("123", data);
        result = cart.delProduct("126", data);
        
        assertEquals(Map.of(), result);
        assertEquals(200, data[0].getStock());
        assertEquals(1000, data[3].getStock());
        
        ExcProduct exception = assertThrows(ExcProduct.class, () -> {
            cart.delProduct("123", data);
        });
        
        assertEquals("Данного товара нет в корзине", exception.getMessage());
        
    }

    @org.junit.jupiter.api.Test
    void allCost() throws Exception {
        
        ExcProduct exception = assertThrows(ExcProduct.class, ()->{
            cart.allCost(data);
        });
        
        assertEquals("Корзина пуста!", exception.getMessage());
        Map<String, Integer> result;
        
        for (int i = 3; i < 7; i++){
            int del = 1;
            if (i%2 == 0)
                del = 2;
            result = cart.add("12" + String.valueOf(i), 100*del, data);
        }
        
        assertEquals(20600000, cart.allCost(data));
    }

    @org.junit.jupiter.api.Test
    void promoDis() throws Exception {
        PromoError exception = assertThrows(PromoError.class, ()->{
            cart.promoDis("DISCOUNT14");
        });
        
        assertEquals("Неверный промокод", exception.getMessage());
        assertEquals(0.0, cart.getDiscount());

        Map<String, Integer> result;

        for (int i = 3; i < 7; i++){
            int del = 1;
            if (i%2 == 0)
                del = 2;
            result = cart.add("12" + String.valueOf(i), 100*del, data);
        }
        
        cart.promoDis("DISCOUNT10");
        
        assertEquals(18540000, cart.allCost(data));
        
        cart.promoDis("DISCOUNT50");
        assertEquals(10300000, cart.allCost(data));
                
    }
}