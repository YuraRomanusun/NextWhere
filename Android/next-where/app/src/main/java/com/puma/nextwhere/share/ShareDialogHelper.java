package com.puma.nextwhere.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.puma.nextwhere.R;
import com.puma.nextwhere.helper.AppConstants;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

/**
 * Created by rajesh on 16/11/17.
 */

public class ShareDialogHelper {

    public void facebookShare(Activity activity, Bitmap image) {
        if (isFacebookInstalled(activity)) {
            com.facebook.share.widget.ShareDialog shareDialog = new ShareDialog(activity);
            CallbackManager callbackManager = CallbackManager.Factory.create();
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException error) {
                }
            });
            if (com.facebook.share.widget.ShareDialog.canShow(ShareLinkContent.class)) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .setShareHashtag(new ShareHashtag.Builder().setHashtag(AppConstants.HASH_TAG).build())
                        .build();
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
            }
        } else {
            CallbackManager callbackManager = CallbackManager.Factory.create();
            List<String> permissionNeeds = Collections.singletonList("publish_actions");
            //this loginManager helps you eliminate adding a LoginButton to your UI
            LoginManager manager = LoginManager.getInstance();
            manager.logInWithPublishPermissions(activity, permissionNeeds);
            manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(image).build();

                    SharePhotoContent sharePhotoContent =
                            new SharePhotoContent.Builder().setPhotos(Collections.singletonList(sharePhoto)).build();

                    ShareApi.share(sharePhotoContent, new FacebookCallback<Sharer.Result>()

                    {
                        @Override
                        public void onSuccess(Sharer.Result result) {

                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {

                        }
                    });
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });

        }

    }

    private boolean isFacebookInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(AppConstants.FACEBOOK_PACKAGE, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    public void instagramShare(Context context, String imagePath) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(AppConstants.INSTAGRAM_PACKAGE);
        if (intent != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(AppConstants.INSTAGRAM_PACKAGE);
            try {
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(context.
                        getContentResolver(), imagePath, context.getString(R.string.app_name), AppConstants.HASH_TAG)));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            shareIntent.setType("image/jpeg");
            context.startActivity(shareIntent);
        } else {
            // bring user to the market to download the app.
            // or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + AppConstants.INSTAGRAM_PACKAGE));
            context.startActivity(intent);
        }
    }
}
