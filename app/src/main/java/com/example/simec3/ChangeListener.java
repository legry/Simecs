package com.example.simec3;

interface ChangeListener {
    void OnChangeListener(boolean isConnect);
    void OnDataReadListener(String data);
    void OnEnableListener(boolean isEnabled);
}
