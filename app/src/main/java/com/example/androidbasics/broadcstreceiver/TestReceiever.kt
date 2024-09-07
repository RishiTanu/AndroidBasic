package com.example.androidbasics.broadcstreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TestReceiever : BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {
        if(intent?.action == "TEST_ACTION"){
            println("ACTION_RECEIVE")
        }
    }
}