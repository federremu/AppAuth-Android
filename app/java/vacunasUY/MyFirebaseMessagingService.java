package vacunasUY;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import net.openid.appauthdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vacunasUY.entities.Token;
import vacunasUY.entities.User;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    //private  String channelId = getString(R.string.default_notification_channel_id);
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("TOKEN:", s);

        MainActivity.token = new Token();
        MainActivity.token.setToken(s);


        if (MainActivity.user != null) {
            MainActivity.user.setToken(s);
        } else {
            MainActivity.user = new User(s);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        createNotificationChannel();
        Log.d("TOKEN:", remoteMessage.toString());
        //remoteMessage.getData().get("titulo");
        //remoteMessage.getData().get("cuerpo");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.vacunasuy)
            .setContentTitle(remoteMessage.getData().get("titulo"))
            .setContentText(remoteMessage.getData().get("cuerpo"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID",
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle());

        //add action
        Intent notifyIntent = new Intent(this, MainActivity.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent openApp = PendingIntent.getActivity(
            this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        notificationBuilder.addAction(new NotificationCompat.Action(R.drawable.vacunasuy, "Abrir", openApp));

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
