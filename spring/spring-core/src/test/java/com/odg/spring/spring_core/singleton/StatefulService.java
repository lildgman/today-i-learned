package com.odg.spring.spring_core.singleton;

public class StatefulService {

    private int price;

    public void order(String name, int price) {
        System.out.println("name = " + name + ", price = " + price);
        this.price = price; // 호출 시에 필드값이 바뀜
    }

    public int getPrice() {
        return price;
    }
}
