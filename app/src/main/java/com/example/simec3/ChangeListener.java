package com.example.simec3;

import android.widget.ArrayAdapter;

interface ChangeListener {
    void OnChangeListener(boolean isConnect);
    void OnDataReadListener(String data);
    void OnEnableListener(ArrayAdapter<String> devices);
}
