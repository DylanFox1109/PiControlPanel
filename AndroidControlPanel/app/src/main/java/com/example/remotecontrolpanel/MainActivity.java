package com.example.remotecontrolpanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import java.lang.Thread;

public class MainActivity extends AppCompatActivity {
    private TextView textBox;
    private boolean bluetoothConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        this.bluetoothConnected = false;

        this.textBox = (TextView)findViewById(R.id.DebugTextBox);
        this.textBox.setText("");

        ConstraintLayout container= (ConstraintLayout)findViewById(R.id.appBackgroud);
        container.setBackgroundColor(Color.RED);

        //Flash background red
        BluetoothConnection bluetoothSesh = new BluetoothConnection();
        Thread flashBackgroundThread = new ConnectionStatusThread(bluetoothSesh,container);
        flashBackgroundThread.start();
    }

    public void onButtonUpEvent(View v){
        String txt = this.textBox.getText() + "\n" + "Button Up Event";
        this.textBox.setText(txt);
        return;
    }

    public void onButtonDownEvent(View v){
        String txt = this.textBox.getText() + "\n" + "Button Down Event";
        this.textBox.setText(txt);
        return;
    }

    public void onSwitchClickEvent(View v){
        Switch sw = (Switch) v;
        return;
    }

//    protected void setupBluetooth() {
//        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//        // Check if Bluetooth is on
//        if (!bluetoothAdapter.isEnabled()){
//            Intent eintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(eintent,0);
//        }
//    }
//
//    protected void discoverBluetoothDevices(BluetoothAdapter adapter){
//        boolean rv = adapter.startDiscovery();
//        if(rv == false){
//            throw new RuntimeException("Error while attempting to discover bluetooth devices");
//        }
//        mReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String action = intent.getAction();
//                // When discovery finds a device
//                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                    // Get the BluetoothDevice object from the Intent
//                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                    device.getName();
//                }
//            }
//        };
//
//        // Register the BroadcastReceiver
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
//
//    }
}