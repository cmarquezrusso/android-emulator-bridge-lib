package com.cmarquez.android.testing.library;

import android.annotation.SuppressLint;

public enum GsmStates {
    UNREGISTERED("UNREGISTERED"), HOME("HOME"), ROAMING("ROAMING"), SEARCHING(
            "SEARCHING"), DENIED("DENIED"), OFF("OFF"), ON("ON");

    private String name;

    private GsmStates(String name) {
        this.name = name;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return name.toLowerCase();
    }
}