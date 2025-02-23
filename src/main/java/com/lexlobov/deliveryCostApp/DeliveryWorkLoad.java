package com.lexlobov.deliveryCostApp;

public enum DeliveryWorkLoad {

    VERY_INTENSIVE(1.6),
    INTENSIVE(1.4),
    HIGH(1.2),
    NORMAL(1);

    DeliveryWorkLoad(double quotient) {
        this.quotient = quotient;
    }

    private final double quotient;
    public double getQuotient() {
        return this.quotient;
    }
}
