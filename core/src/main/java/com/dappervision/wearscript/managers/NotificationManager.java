package com.dappervision.wearscript.managers;

import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.dappervision.wearscript.BackgroundService;
import com.dappervision.wearscript.R;
import com.dappervision.wearscript.events.NotificationEvent;

public class NotificationManager extends Manager{
    private Context context;
    android.app.NotificationManager mSystemNotificationManager;

    public NotificationManager(Context context, BackgroundService bs) {
        super(bs);
        this.context = context;
        reset();
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    public void onEvent(NotificationEvent event) {
        int notificationId = event.getId();

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_interpunc)
                        .setContentTitle(event.getTitle())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(event.getText()))
                        .setContentText(event.getText());

        mSystemNotificationManager = (android.app.NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        mSystemNotificationManager.notify(notificationId, builder.build());
    }
}
