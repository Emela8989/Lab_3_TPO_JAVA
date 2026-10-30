package org.example;

public class Product{

    private String id;
    private String name;
    private double price;
    private int stock;

    Product(String id, String name, double price, int stock) throws ElemCount {
        this.id = id;
        this.name = name;
        setPrice(price);
        setStock(stock);
    }

    //геттеры
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    //Внесенные изменения, чтобы корректно устанавливалось значение stock и price,
    //нам необходимо добавить проверку в сеттеры на неотрицательность
    public void setPrice(double price) throws ElemCount {
        if (price >= 0)
            this.price = price;
        else
            throw new ElemCount("Цена не может быть отрицательной!");
    }

    public void setStock(int stock) throws ElemCount {
        if (stock >= 0)
            this.stock = stock;
        else
            throw new ElemCount("Количество товара не может быть отрицательным!");
    }
}