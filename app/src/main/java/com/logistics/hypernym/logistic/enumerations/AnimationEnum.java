package com.logistics.hypernym.logistic.enumerations;

/**
 * Created by Metis on 08-Dec-17.
 */

public enum AnimationEnum {
    HORIZONTAL(0),
    VERTICAL(1);

    private final int id;

    AnimationEnum(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }
}

