package com.lexlobov.deliveryCostApp;

public class Main {
    public static void main(String[] args) {
        DeliveryCalculator calculator = new DeliveryCalculator();
        int distance = 27;
        boolean isLarge = false;
        boolean isFragile = true;
        System.out.println(calculator.calculateDeliveryCost(distance, isLarge, isFragile, DeliveryWorkLoad.INTENSIVE));
    }
}
