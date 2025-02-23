package com.lexlobov.deliveryCostApp;

import static java.lang.Math.max;

public class DeliveryCalculator {
    
    public double calculateDeliveryCost(
            int distance,
            boolean isLarge,
            boolean isFragile,
            DeliveryWorkLoad load
    ) {
        if (isFragile & distance > 30) {
            throw new IllegalArgumentException("?????????? ???????????? ???????? ??????? ????? ?? ????????? ????? 30??");
        }

        if (distance <= 0) {
            throw new IllegalArgumentException("????????? ???????? ?????? ???? ?????? 0");
        }

        double cost = 0;
        cost += calculateDistanceComponent(distance);
        cost += calculateSizeComponent(isLarge);
        cost += calculateRigidityComponent(isFragile);
        cost *= load.getQuotient();

        return finalizeCost(cost);
    }

    private int calculateDistanceComponent(int distance) {
        if (distance <= 2) {
            return 50;
        } else if (distance <= 10) {
            return 100;
        } else if (distance <= 30) {
            return 200;
        } else {
            return 300;
        }
    }

    private int calculateSizeComponent(boolean isLarge) {
        return isLarge ? 200 : 100;
    }

    private int calculateRigidityComponent(boolean isFragile) {
        return isFragile ? 300 : 0;
    }

    private double finalizeCost(double cost) {
        return max(cost, 400);
    }
}
