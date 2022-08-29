package com.bobocode.service;

public interface VehicleService extends Info{

    default void move(String name) {
        System.out.println("Abstract vehicle: " + name);
    }
}
