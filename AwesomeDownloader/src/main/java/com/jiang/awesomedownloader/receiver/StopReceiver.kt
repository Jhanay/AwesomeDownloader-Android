package com.jiang.awesomedownloader.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jiang.awesomedownloader.AwesomeDownloader
import com.jiang.awesomedownloader.TAG

class StopReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        // val pendingResult = goAsync()
        Log.d(TAG, "onReceive: stop")
        AwesomeDownloader.stopAll()

    }
}
