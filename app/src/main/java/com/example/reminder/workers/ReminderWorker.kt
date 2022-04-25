package com.example.reminder.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.reminder.R
import com.example.reminder.domain.dispatchers.AppDispatchers
import com.example.reminder.domain.usecase.GetReminderByIdUseCase
import com.example.reminder.domain.usecase.UpdateComplitionByIdUseCase
import com.example.reminder.ui.activity.MainActivity
import com.example.reminder.ui.extensions.emptyString
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ReminderWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params), KoinComponent {
    private val updateReminderComplitionByIdUseCase: UpdateComplitionByIdUseCase by inject()
    private val getReminderByIdUseCase: GetReminderByIdUseCase by inject()
    private val appDispatchers: AppDispatchers by inject()
    override suspend fun doWork(): Result {
        val context = applicationContext
        val id = inputData.getString(REMINDER_ID_KEY) ?: emptyString()
        val reminder = getReminderById(id)
        return reminder?.let {
            val title = it.title
            val message = it.description
            showReminderNotification(context, title, message)
            updateReminderComplitionById(id)
            Result.success()
        } ?: Result.failure()
    }

    private suspend fun getReminderById(id: String) = withContext(appDispatchers.ioDispatcher) {
        return@withContext getReminderByIdUseCase(id)
    }

    private suspend fun updateReminderComplitionById(id: String) = withContext(appDispatchers.ioDispatcher) {
        updateReminderComplitionByIdUseCase(id)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showReminderNotification(context: Context, title: String, message: String) {
        // Make a channel if necessary
        val intent = Intent(context, MainActivity::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /*
            * Create the NotificationChannel, but only on API 26+ because
            * he NotificationChannel class is new and not in the support library
            */
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            // Add the channel
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_note)
            .setContentTitle(title)
            .setContentText(message)
            .setSound(soundUri)
            .setStyle(NotificationCompat.InboxStyle())
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)


        // Show the notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())

    }
}