package com.puma.nextwhere.provider;

import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.FileProvider;

/**
 * Created by rajesh on 14/4/17.
 */

public class LegacyCompatFileProvider extends FileProvider {
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return(new LegacyCompatCursorWrapper(super.query(uri, projection, selection, selectionArgs, sortOrder)));
    }
}