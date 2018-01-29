// IArduino.aidl
package com.example.ArduinoAIDL;

// Declare any non-default types here with import statements

interface IArduino {
    String getShowsPribor(String dev, String priborName);
    String setPusk(String dev, String puskName);
}
