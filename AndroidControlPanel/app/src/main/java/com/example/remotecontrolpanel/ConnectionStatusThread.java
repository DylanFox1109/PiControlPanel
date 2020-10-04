package com.example.remotecontrolpanel;

import android.graphics.Color;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ConnectionStatusThread extends Thread {
    private BluetoothConnection bluetoothSession;
    private ConstraintLayout backgroundElem;

    public ConnectionStatusThread(BluetoothConnection bluetoothSession, ConstraintLayout container){
        this.bluetoothSession = bluetoothSession;
        this.backgroundElem = container;
    }

    @Override
    public void run() {
        while(true) {
            try{
                Thread.sleep(300);
                if (this.bluetoothSession.isConnected == false){
                    this.backgroundElem.setBackgroundColor(Color.parseColor("#ff6b70"));
                    Thread.sleep(300);
                    this.backgroundElem.setBackgroundColor(Color.WHITE);
                } else {
                    this.backgroundElem.setBackgroundColor(Color.WHITE);
                }
            } catch(Exception e) { System.out.println(e.toString());}
        }
    }
}
