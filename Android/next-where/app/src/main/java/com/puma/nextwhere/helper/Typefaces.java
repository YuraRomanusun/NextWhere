package com.puma.nextwhere.helper;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by victory on 8/20/2016.
 */
public class Typefaces {
    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(Context c, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(), "fonts/"+assetPath);
                    cache.put(assetPath, t);
                } catch (Exception e) {
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }
}
