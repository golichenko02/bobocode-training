package com.bobocode.service;

public interface Info {

    default String getInfo(){
        return this.getClass().getSimpleName();
    }
}
