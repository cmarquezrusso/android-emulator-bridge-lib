package com.cmarquez.android.testing.library;

public enum BatteryStatusType {
    UNKNOWN("unknown"), CHARGING("charging"), DISCHARGING("discharging"), NOTCHARGING(
            "not-charging"), FULL("full");
    String value;

    BatteryStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}