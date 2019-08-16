package com.puma.nextwhere;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.util.Base64;
import android.util.Log;

import com.puma.nextwhere.database.DatabaseInfo;
import com.zopim.android.sdk.api.ZopimChat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class NextwhereApplication extends Application {
    public static final String TAG = NextwhereApplication.class.getSimpleName();
    private static NextwhereApplication _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        ZopimChat.init(BuildConfig.CHAT_ACCOUNT_KEY);
        _instance = this;
        getUserInfo();

    }

    public static synchronized NextwhereApplication getInstance() {
        return _instance;
    }

    // get sha1 and then print logo
    public void getUserInfo() {
        // Add code to print out the key hash
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {

        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public Context getContext() {
        return _instance.getApplicationContext();
    }
}
