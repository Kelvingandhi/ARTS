

    package com.example.soham.arts;

    import android.app.Notification;
    import android.app.NotificationManager;
    import android.app.PendingIntent;
    import android.content.Context;
    import android.content.Intent;
    import android.util.Log;

    import com.example.soham.fragment.HomeFragment;
    import com.google.android.gcm.GCMBaseIntentService;

    import org.json.JSONException;
    import org.json.JSONObject;


    public class GCMIntentService extends GCMBaseIntentService {

        public GCMIntentService() {
            super("590542472124");
        }

        private static final String TAG = "===GCMIntentService===";


        @Override
        protected void onRegistered(Context arg0, String registrationId) {
            Log.i(TAG, "Device registered: regId = " + registrationId);
        }

        @Override
        protected void onUnregistered(Context arg0, String arg1) {
            Log.i(TAG, "unregistered = " + arg1);
        }

        @Override
        protected void onMessage(Context arg0, Intent arg1) {
            Log.d(TAG, "new message= ");
            if (arg1.getExtras() != null) {
                generateNotification(arg0, arg1.getExtras().getString("message"), arg1.getExtras().getString("title"), arg1.getExtras().getString("image"), arg1.getExtras().getString("link"), arg1.getExtras().getString("date"));
            }

        }

        @Override
        protected void onError(Context arg0, String errorId) {
            Log.i(TAG, "Received error: " + errorId);
        }

        @Override
        protected boolean onRecoverableError(Context context, String errorId) {
            return super.onRecoverableError(context, errorId);
        }

        private static void generateNotification(Context context, String message, String Title, String img, String Link, String date) {
            int icon = R.drawable.ic_launcher;
            long when = System.currentTimeMillis();

            JSONObject object;
            String msg = "";
            try {
                object = new JSONObject(message);
                msg = object.getString("Message");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                notification = new Notification.Builder(context).
                        setSmallIcon(icon)
                        .setContentText(msg)
                        .setContentTitle("A.R.T.S.")

                .build();
            }


            Intent notificationIntent = new Intent(context, HomeFragment.class);
            notificationIntent.putExtra("img", img);
            notificationIntent.putExtra("msg", message);
            notificationIntent.putExtra("Title", Title);
            notificationIntent.putExtra("Link", Link);
            notificationIntent.putExtra("date", date);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent intent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            notification.contentIntent  = intent;
            notification.tickerText = "A.R.T.S.";
            notification.when = when;


            //notification.setLatestEventInfo(context, "Arya Ispat", msg, intent);
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(0, notification);


        }
    }


