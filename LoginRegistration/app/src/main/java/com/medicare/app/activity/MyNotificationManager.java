package com.medicare.app.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.medicare.launch.app.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MyNotificationManager extends BaseActivty {

    private final static String sample_url = "https://firebasestorage.googleapis.com/v0/b/medicare-b3328.appspot.com/o/gallery.png?alt=media&token=904d44dd-4cf6-4097-bbbc-77d696c898ec";

    private final static int NORMAL = 0x00;
    private final static int BIG_PICTURE_STYLE = 0x02;
    private static NotificationManager mNotificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationmain);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void setNormalStyle(View view) {
        new CreateNotification(NORMAL).execute();
    }



    public void setBigPictureStyle(View view) {
        new CreateNotification(BIG_PICTURE_STYLE).execute();
    }


    /**
     * Notification AsyncTask to create and return the
     * requested notification.
     *
     * @see CreateNotification#CreateNotification(int)
     */
    public class CreateNotification extends AsyncTask<Void, Void, Void> {

        int style = NORMAL;

        /**
         * Main constructor for AsyncTask that accepts the parameters below.
         *
         * @param style {@link #NORMAL},  {@link #BIG_PICTURE_STYLE}
         * @see #doInBackground
         */
        public CreateNotification(int style) {
            this.style = style;
        }

        /**
         * Creates the notification object.
         *
         * @see #setNormalNotification
         * @see #setBigPictureStyleNotification
         */
        @Override
        protected Void doInBackground(Void... params) {
            Notification noti = new Notification();
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(MyNotificationManager.this);
            switch (style)
            {
                case NORMAL:
                    noti = setNormalNotification();
                    break;

                case BIG_PICTURE_STYLE:
                    noti = setBigPictureStyleNotification();
                    break;

            }

            noti.defaults |= Notification.DEFAULT_LIGHTS;
            noti.defaults |= Notification.DEFAULT_VIBRATE;
            noti.defaults |= Notification.DEFAULT_SOUND;

            noti.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
            mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
            mNotificationManager.notify(0, noti);

            return null;

        }
    }

    /**
     * Normal Notification
     *
     * @return Notification
     * @see CreateNotification
     */
    private Notification setNormalNotification() {
        Bitmap remote_picture = null;

        try {
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(sample_url).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Setup an explicit intent for an ResultActivity to receive.
        Intent resultIntent = new Intent(this, DirectionsMapActivity.class);

        // TaskStackBuilder ensures that the back button follows the recommended convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself).
        stackBuilder.addParentStack(DirectionsMapActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack.
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.med)
                .setAutoCancel(true)
                .setLargeIcon(remote_picture)
                .setContentIntent(resultPendingIntent)
                .addAction(R.mipmap.med, "Cancel", resultPendingIntent)
                .addAction(R.mipmap.med, "Ok", resultPendingIntent)
                .setContentTitle("Yes, I have the mentioned medicine.")
                .setContentText("You can check the map for directions").setColor(Color.CYAN).build();

    }


    /**
     * Big Picture Style Notification
     *
     * @return Notification
     * @see CreateNotification
     */
    private Notification setBigPictureStyleNotification() {
        Bitmap remote_picture = null;

        // Create the style object with BigPictureStyle subclass.
        NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
        notiStyle.setBigContentTitle("Hey, I am searching for this medicine!");
        notiStyle.setSummaryText("medicine name");

        try {
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(sample_url).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the big picture to the style.
        notiStyle.bigPicture(remote_picture);

        // Creates an explicit intent for an ResultActivity to receive.
        Intent resultIntent = new Intent(this, ConfirmMedicine.class);

        // This ensures that the back button follows the recommended convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself).
        stackBuilder.addParentStack(ConfirmMedicine.class);

        // Adds the Intent that starts the Activity to the top of the stack.
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.med)
                .setAutoCancel(true)
                .setLargeIcon(remote_picture)
                .setContentIntent(resultPendingIntent)
                .addAction(R.mipmap.med, "Cancel", resultPendingIntent)
                .addAction(R.mipmap.med, "Reply", resultPendingIntent)
                .setContentTitle("Hey, I am searching for this medicine!")
                .setContentText("medicine name").setColor(Color.CYAN)
                .setStyle(notiStyle).build();

    }



}
/*
package com.medicare.app.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

import com.medicare.launch.app.R;

public class MyNotificationManager extends BaseActivty  {
    private Context mCtx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationmain);

        Button bnotify = (Button) findViewById(R.id.notification);

        Button bcustomnotify = (Button) findViewById(R.id.customnotification);

        bnotify.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Notification();
                // TODO Auto-generated method stub
            }
        });

        bcustomnotify.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                CustomNotification();
                // TODO Auto-generated method stub
            }
        });

    }

    public void Notification() {
        // Set Notification Title
        String strtitle = getString(R.string.notificationtitle);
        // Set Notification Text
        String strtext = getString(R.string.notificationtext);

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, NotificationView.class);
        // Send data to NotificationView Class
        intent.putExtra("title", strtitle);
        intent.putExtra("text", strtext);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.mipmap.med)
                // Set Ticker Message
                .setTicker(getString(R.string.notificationticker))
                // Set Title
                .setContentTitle(getString(R.string.notificationtitle))
                // Set Text
                .setContentText(getString(R.string.notificationtext))
                // Add an Action Button below Notification
                .addAction(R.mipmap.med, "Action Button", pIntent)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());

    }

    public void CustomNotification() {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.customnotification);

        // Set Notification Title
        String strtitle = getString(R.string.customnotificationtitle);
        // Set Notification Text
        String strtext = getString(R.string.customnotificationtext);

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, ConfirmMedicine.class);
        // Send data to NotificationView Class
        intent.putExtra("title", strtitle);
        intent.putExtra("text", strtext);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.mipmap.med)
                // Set Ticker Message
                .setTicker(getString(R.string.customnotificationticker))
                // Dismiss Notification
                .setAutoCancel(true)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
//                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.med))

                // Set RemoteViews into Notification
                .setContent(remoteViews).setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.MAGENTA, 3000, 3000)
                .setSound(Uri.parse("uri://unique_notification.mp3"))
                .addAction(R.mipmap.ic_launcher,"Cancel",pIntent)
                .addAction(R.mipmap.ic_launcher,"Reply",pIntent)
              .setColor(Color.CYAN)
               // .setSubText("Hey! I am searching this medicine")
                              ;
        // Locate and set the Image into customnotificationtext.xml ImageViews
        //remoteViews.setImageViewResource(R.id.imagenotileft,R.drawable.medical);
        remoteViews.setImageViewResource(R.id.imagenotiright,R.drawable.icon);

        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.title,getString(R.string.customnotificationtitle));
        remoteViews.setTextViewText(R.id.text,getString(R.string.customnotificationtext));

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());

    }

  */
/*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*//*

}*/
/*
package com.medicare.app.activity;

import com.medicare.app.R;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.text.Html;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


*//*

*/
/**
 * Created by satveer on 13-06-2017.
 *//*
*/
/*


public class MyNotificationManager {

    private Context mCtx;
    public static final int NOTIFICATION_ID = 231;

    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;


    public MyNotificationManager(Context mCtx) {

        this.mCtx = mCtx;
    }

    public void showNotification(String from, String notification, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(
                mCtx,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mCtx);
        Notification mNotification = builder.setSmallIcon(R.mipmap.icon).setAutoCancel(true).
                setContentIntent(pendingIntent).setContentTitle(from).setContentText(notification).
                setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.icon)).build();
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        MyNotificationManager myNotificationManager = (MyNotificationManager) mCtx.getSystemService
                (Context.NOTIFICATION_SERVICE);
        //myNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }




    //the method will show a big notification with an image
    //parameters are title for message title, message for message text, url of the big image and an intent that will open
    //when you will tap on the notification
    public void showBigNotification(String title, String message, String url, Intent intent) {
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_BIG_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                .setContentText(message)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        MyNotificationManager myNotificationManager = (MyNotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(ID_BIG_NOTIFICATION, notification);
    }

    //the method will show a small notification
    //parameters are title for message title, message for message text and an intent that will open
    //when you will tap on the notification
    public void showSmallNotification(String title, String message, Intent intent) {
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                .setContentText(message)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        MyNotificationManager myNotificationManager = (MyNotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(ID_SMALL_NOTIFICATION, notification);
    }

    //The method will return Bitmap from an image URL
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}*/

