package com.example.cydrop_frontend;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationScheduler {

    private static final String CHANNEL_ID = "mychannel";

    // Function to schedule the notification at the given time
    public static void scheduleNotificationAtTime(Context context, String title, String description, long triggerAtMillis) {
        // Check for POST_NOTIFICATIONS permission (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // If permission is not granted, request permission
                if (context instanceof Activity) {
                    ActivityCompat.requestPermissions(
                            (Activity) context,
                            new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                            100 // Request code
                    );
                }
                Toast.makeText(context, "Error getting notifications perms", Toast.LENGTH_LONG).show();
                return; // Don't proceed if permission isn't granted
            }
        }

        // Check for exact alarm permission for Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!context.getSystemService(AlarmManager.class).canScheduleExactAlarms()) {
                // Request permission to schedule exact alarms
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                context.startActivity(intent);
                Toast.makeText(context, "Error getting alarm perms", Toast.LENGTH_LONG).show();
                return; // Don't proceed if permission isn't granted
            }
        }


        // Create an intent to fire when the alarm goes off
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("title", title);  // Pass the title to the receiver
        intent.putExtra("description", description);  // Pass the description to the receiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Get the system AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            // Set the alarm to go off at the specific time in triggerAtMillis
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }

    // Receiver to show the notification
    public static class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            // Get the title and description passed to the receiver
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");

            // Call the method to create and show the notification
            createNotification(context, title, description);
        }

        private void createNotification(Context context, String title, String description) {

            // Create the notification channel for Android 8.0 (Oreo) and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "mychannel";
                int importance = NotificationManager.IMPORTANCE_HIGH; // Set channel importance to high

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                // Register the channel with the system
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            // Build the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_pets_24)
                    .setContentTitle("Reminder for " + title)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setContentText(description)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setAutoCancel(true);

            // Check and request POST_NOTIFICATIONS permission for Android 13 (API level 33) and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // Request the POST_NOTIFICATIONS permission
                    ActivityCompat.requestPermissions(
                            (Activity) context,
                            new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                            100 // Request code
                    );
                    return;
                }
            }

            // Show the notification using NotificationManagerCompat
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(1, builder.build()); // 1 is the notification ID
        }
    }
}

