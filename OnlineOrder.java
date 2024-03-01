package com.example.jkrnkaur;

public class OnlineOrder {
    private String product;
    private int customerId;
    private String customName;
    private int age;
    private double bill;

    public OnlineOrder(String product, int customerId, String customName, int age, double bill) {
        this.product = product;
        this.customerId = customerId;
        this.customName = customName;
        this.age = age;
        this.bill = bill;
    }

    public String getProduct() {
        return product;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomName() {
        return customName;
    }

    public int getAge() {
        return age;
    }

    public double getBill() {
        return bill;
    }
}
