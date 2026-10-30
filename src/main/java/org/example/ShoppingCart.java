package org.example;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;


public class ShoppingCart{
    private final Map<String, Integer> array = new HashMap<>();
    private double discount = 0.0;

    
    //добавление товара в корзину
    public Map<String, Integer> add(String id, int stock, Product[] data) throws ExcProduct, ElemCount {
        if (stock <= 0)
            throw new ElemCount("Неправильное число единиц товара");
        for (Product y : data) {
            if (y.getId().equals(id)) {
                if (y.getStock() >= stock) {
                    if (this.array.containsKey(id)) {
                        this.array.compute(id, (k, currentCount) -> currentCount + stock);
                    } else {
                        this.array.put(id, stock);
                    }
                    y.setStock(y.getStock() - stock);
                    return this.array;
                } else {
                    throw new ElemCount("Количество добавляемых товаров больше, чем есть на складе.");
                }
            }
        }
        throw new ExcProduct("Данный товар отсутствует на складе.");
    }
    
    // уменьшение количества товара в корзине
    public Map<String, Integer> lessСount(String id, int stock, Product[] data) throws ElemCount, ExcProduct{
        if (this.array.containsKey(id)){
            if (this.array.get(id) >= stock && stock > 0)
            {
                //уменьшение количества товара в корзине
                this.array.compute(id, (k, currentStock) -> currentStock - stock);
                //если количество товара равно нулю, то мы его удаляем из корзины
                if (this.array.get(id) == 0)
                    this.array.remove(id);
                //изменение количества товара на складе
                for (Product x : data)
                {
                    if (x.getId().equals(id))
                    {
                        x.setStock(x.getStock() + stock);
                        break;
                    }
                }
                return this.array;
            }
            else 
                throw new ElemCount("В корзине нет такого количества товара.");
        }
        
        throw new ExcProduct("Данного товара нет в корзине.");
    }

    //Удаление товара из корзины
    public Map<String, Integer>  delProduct(String id, Product[] data) throws ExcProduct, ElemCount {
        if (this.array.containsKey(id)){
            for (Product x : data){
                if (x.getId().equals(id)){
                    int currentStock = this.array.get(id);
                    x.setStock(x.getStock() + currentStock);
                    this.array.remove(id);
                    return this.array;
                }
            }
            return this.array;
        }
        
        throw new ExcProduct ("Данного товара нет в корзине");
    }
    
    //Полная стоимость товаров в корзине
    public double allCost(Product[] data) throws ExcProduct{
        if (!(this.array).isEmpty())
        {
            double cost = 0;
            for (Product x : data)
            {
                if (this.array.containsKey(x.getId())){
                    cost += this.array.get(x.getId()) * x.getPrice();
                }
            }
            
            return cost - cost * this.discount;
        }
        throw new ExcProduct("Корзина пуста!");
    }
    
    //Метод для применения скидки
    public void promoDis(String code)throws PromoError {

        try{
            PromoCode promo = PromoCode.valueOf(code);
            this.discount = promo.getDiscount();
            
        } catch(IllegalArgumentException e){
            throw new PromoError("Неверный промокод");
        }
    }
    
    public double getDiscount() {
        return this.discount;
    }
}
