package com.example.simec3;

import android.widget.ArrayAdapter;

class DevConns {
    private String titles;
    private ArrayAdapter<String> devices;
    private BluetoothConnect bluetoothConnect;
    DevConns(String titles, ArrayAdapter<String> devices, BluetoothConnect bluetoothConnect) {
        this.titles = titles;
        this.devices = devices;
        this.bluetoothConnect = bluetoothConnect;
    }

    String getTitles() {
        return titles;
    }

    public BluetoothConnect getBluetoothConnect() {
        return bluetoothConnect;
    }

    ArrayAdapter<String> getDevices() {
        return devices;
    }
}
