package com.puma.nextwhere.network;

import android.util.Log;

/**
 * Created by rajesh on 17/2/17.
 */

public class NetworkError {
    NetworkError(Throwable e) {
        Log.e("error",e.getMessage());
    }
}
