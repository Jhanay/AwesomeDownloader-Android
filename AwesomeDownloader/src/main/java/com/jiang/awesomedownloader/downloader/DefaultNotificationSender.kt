package com.jiang.awesomedownloader.downloader

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jiang.awesomedownloader.R
import com.jiang.awesomedownloader.receiver.*


/**
 *
 * @ProjectName:    AwesomeDownloader
 * @ClassName:      DefaultNotificationSender
 * @Description:     java类作用描述
 * @Author:         江
 * @CreateDate:     2020/9/17 15:28
 */


class DefaultNotificationSender(context: Context) :
    NotificationSender(context) {

    private val stopIntent = createIntent(StopReceiver::class.java, "ACTION_STOP")
    private val stopPendingIntent =
        createPendingIntent(context, stopIntent)

    private val cancelIntent = createIntent(CancelReceiver::class.java, "ACTION_CANCEL")
    private val cancelPendingIntent =
        createPendingIntent(context, cancelIntent)

    private val cancelAllIntent = createIntent(CancelAllReceiver::class.java, "ACTION_CANCEL_ALL")
    private val cancelAllPendingIntent = createPendingIntent(context, cancelAllIntent)

    private val resumeIntent = createIntent(ResumeReceiver::class.java, "ACTION_RESUME")
    private val resumePendingIntent = createPendingIntent(context, resumeIntent)

    private fun createIntent(receiverClass: Class<out BroadcastReceiver>, tag: String): Intent {
        return Intent(context, receiverClass).apply {
            action = tag
        }
    }

    private fun createPendingIntent(context: Context, intent: Intent): PendingIntent? =
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


    override fun buildDownloadProgressNotification(progress: Int, fileName: String): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download)
            .addAction(
                R.drawable.ic_baseline_pause,
                context.getString(R.string.STOP),
                stopPendingIntent
            )
            .addAction(R.drawable.ic_baseline_cancel_24, "cancel", cancelPendingIntent)
            .addAction(R.drawable.ic_baseline_delete_forever, "cancel all", cancelAllPendingIntent)
            .setContentTitle("$fileName (downloading)")
            .setContentText("$progress%")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(false)
            .setProgress(NOTIFICATION_PROGRESS_MAX, progress, false)
            .build()
    }

    override fun buildDownloadStopNotification(fileName: String): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download)
            .addAction(
                R.drawable.ic_baseline_play_arrow,
                "resume",
                resumePendingIntent
            )
            .addAction(R.drawable.ic_baseline_cancel_24, "cancel", cancelPendingIntent)
            .addAction(R.drawable.ic_baseline_delete_forever, "cancel all", cancelAllPendingIntent)
            .setContentTitle("$fileName (stop)")
            .setContentText("0%")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(false)
            .setProgress(NOTIFICATION_PROGRESS_MAX, 0, false)
            .build()
    }

    override fun buildDownloadDoneNotification(filePath: String, fileName: String): Notification {
        val openFileIntent = Intent(context, OpenFileReceiver::class.java).apply {
            action = "ACTION_OPEN"
            putExtra("ACTION_OPEN", 0)
            putExtra("PATH", "$filePath/$fileName")
            Log.d(TAG, "showDownloadDoneNotification: $filePath/$fileName")
        }
        val openPendingIntent =
            createPendingIntent(context, openFileIntent)
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle("$fileName (done)")
            .setContentText(fileName)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(openPendingIntent)
            .setAutoCancel(true)
            .build()
    }

}