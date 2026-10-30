package org.example;




//класс enum для добавления скидки к итоговой стоимости

public enum PromoCode {
    DISCOUNT10(0.10),
    DISCOUNT20(0.20),
    DISCOUNT50(0.50);

    private final double discount;
    
    PromoCode(double discount) {
        this.discount = discount;
    }
    
    public double getDiscount(){
        return discount;
    }
}
