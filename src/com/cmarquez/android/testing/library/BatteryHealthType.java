package com.cmarquez.android.testing.library;

public enum BatteryHealthType {
    UNKNOWN("unknown"), GOOD("good"), OVERHEAT("overheat"), DEAD("dead"), OVERVOLTAGE(
            "overvoltage"), FAILURE("failure");

    String value;

    BatteryHealthType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}