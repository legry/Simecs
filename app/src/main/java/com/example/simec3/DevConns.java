package com.example.simec3;

import android.widget.ArrayAdapter;

class DevConns {
    private String titles;
    private ArrayAdapter<String> devices;
    public DevConns(String titles, ArrayAdapter<String> devices) {
        this.titles = titles;
        this.devices = devices;
    }

    String getTitles() {
        return titles;
    }

    ArrayAdapter<String> getDevices() {
        return devices;
    }
}
