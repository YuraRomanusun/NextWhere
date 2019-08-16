package com.puma.nextwhere.helper;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.puma.nextwhere.R;

/**
 * Created by rajesh on 26/3/17.
 */

public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;
    private String message, userInfo;

    public MyInfoWindowAdapter(String message, String userInfo, View myContentsView) {
        this.myContentsView = myContentsView;
        this.message = message;
        this.userInfo = userInfo;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // Getting view from the layout file
        TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.title));
        tvTitle.setText(userInfo);
        TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.subTitle));
        tvSnippet.setText(message);
        return myContentsView;
    }

    @Override
    public View getInfoContents(Marker arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}
