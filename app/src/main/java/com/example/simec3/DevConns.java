package com.example.simec3;

import android.content.Context;

class DevConns {
    private String titles;
    private BluetoothConnect bluetoothConnect;
    DevConns(String titles, Context context) {
        this.titles = titles;
        bluetoothConnect = new BluetoothConnect(context);
    }

    String getTitles() {
        return titles;
    }

    BluetoothConnect getBluetoothConnect() {
        return bluetoothConnect;
    }
}
