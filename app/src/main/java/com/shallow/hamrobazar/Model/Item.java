package com.shallow.hamrobazar.Model;

public class Item {

    private String name;
    private int imgId;
    private  int price;
    private  String condition;
    private String type;

    public Item(String name, int imgId, int price, String condition,String type) {
        this.name = name;
        this.imgId = imgId;
        this.price = price;
        this.condition = condition;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

}
