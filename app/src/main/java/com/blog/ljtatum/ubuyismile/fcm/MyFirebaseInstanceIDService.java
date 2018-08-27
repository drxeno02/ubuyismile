package com.blog.ljtatum.ubuyismile.fcm;

import android.support.annotation.Nullable;

import com.blog.ljtatum.ubuyismile.logger.Logger;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onTokenRefresh() {
        // get updated InstanceID token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Logger.d(TAG, "Refreshed token: " + refreshedToken);

        // if you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(@Nullable String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
