package com.example.simec3;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

class BluetoothConnect {
    private BluetoothAdapter bluetoothAdapter;
    private BroadcastReceiver receiver;
    private BluetoothDevice bluetoothDevice = null;
    private BluetoothSocket bluetoothSocket = null;
    private InputStream is = null;
    private OutputStream os = null;
    private ChangeListener changeListener;
    private Handler handler;
    private String data = "";
    private BufferedReader bfRdr;
    private Context context;
    private ArrayAdapter<String> devices;
    private ArrayAdapter<BluetoothDevice> bluetoothdevices;

    BluetoothConnect(Context context, final ChangeListener changeListener) {
        this.changeListener = changeListener;
        handler = new Handler();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.context = context;
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    if (state == BluetoothAdapter.STATE_ON) {
                        getBondeds();
                    }
                }
                if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                    if (bluetoothSocket != null) {
                        try {
                            bluetoothSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        context.registerReceiver(receiver, filter);
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        } else {
            getBondeds();
        }
    }

    void ConnectDestroy() {

    }

    void ConnectCreat() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (bluetoothSocket == null) {
                    try {
                        bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                while (!bluetoothSocket.isConnected()) {
                    try {
                        bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        bluetoothSocket.connect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                    try {
                        os = bluetoothSocket.getOutputStream();
                        is = bluetoothSocket.getInputStream();
                        InputStreamReader inStRdr = new InputStreamReader(is);
                        bfRdr = new BufferedReader(inStRdr);
                        handler.post(connectreader);
                        while (bluetoothSocket.isConnected()) {
                            if (is.available() > 0) {
                                data = bfRdr.readLine();
                                handler.post(runreader);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                handler.post(connectreader);
            }
        }).start();
    }

    @Override
    protected void finalize() throws Throwable {
        context.unregisterReceiver(receiver);
        super.finalize();
    }

    private Runnable runreader = new Runnable() {
        @Override
        public void run() {
            changeListener.OnDataReadListener(data);
        }
    };

    private Runnable connectreader = new Runnable() {
        @Override
        public void run() {
            changeListener.OnChangeListener(bluetoothSocket.isConnected());
            if (!bluetoothSocket.isConnected()) {
                ConnectCreat();
            }
        }
    };

    private void getBondeds() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                devices = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
                bluetoothdevices = new ArrayAdapter<>(context, android.R.layout.select_dialog_singlechoice);
                Set<BluetoothDevice> bondedDevices = null;
                while (bondedDevices == null) {
                    bondedDevices = bluetoothAdapter.getBondedDevices();
                }
                for (BluetoothDevice device : bondedDevices) {
                    devices.add(device.getName() + "\n" + device.getAddress());
                    bluetoothdevices.add(device);
                }
                handler.post(bondsNotyf);
            }
        }).start();
    }

    void setDevice(int pos) {
        bluetoothDevice = bluetoothdevices.getItem(pos);
    }

    private Runnable bondsNotyf = new Runnable() {
        @Override
        public void run() {
            changeListener.OnEnableListener(devices);
        }
    };

    void WriteData(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    os.write(data.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
