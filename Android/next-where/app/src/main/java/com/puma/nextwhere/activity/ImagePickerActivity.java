package com.puma.nextwhere.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.puma.nextwhere.BuildConfig;
import com.puma.nextwhere.R;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.ImageCallback;
import com.puma.nextwhere.helper.Utils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagePickerActivity extends AppCompatActivity {
    @BindView(R.id.layout)
    LinearLayout layout;
    private final String EXTRA_FILENAME = "com.init.payment.bwalle.activities.EXTRA_FILENAME";
    private final String FILENAME = "UserPic.jpeg";
    private final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    private final String PHOTOS = "photos";
    private File output = null;
    private Uri outputUri = null;
    int requestCode;
    public final static int CAMERA_REQUESTCODE = 1, GALLERY_REQUESTCODE = 2;
    public final static String KEY = "requestCode";
    ImageCallback imageCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageselector);
        ButterKnife.bind(this);
        requestCode = getIntent().getIntExtra(KEY, 0);
        if (requestCode == AppConstants.CAMERA)
            openCamera(savedInstanceState);
        else if (requestCode == AppConstants.GALLERY)
            openGallery();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (output != null)
            outState.putSerializable(EXTRA_FILENAME, output);
        super.onSaveInstanceState(outState);
    }

    private void openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUESTCODE);
        else
            startGalleryIntent();
    }

    private void openCamera(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_REQUESTCODE);
        else
            startCameraIntent(savedInstanceState);
    }

    private void startGalleryIntent() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 19) {
            // For Android KitKat, we use a different intent to ensure
            // we can
            // get the file path from the returned intent URI
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            intent.setType("image/*");
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        }
        startActivityForResult(intent, GALLERY_REQUESTCODE);
    }

    private void startCameraIntent(Bundle savedInstanceState) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (savedInstanceState == null) {
            output = new File(new File(getFilesDir(), PHOTOS), FILENAME);
            if (output.exists()) {
                output.delete();
            } else {
                output.getParentFile().mkdirs();
            }
        } else {
            output = (File) savedInstanceState.getSerializable(EXTRA_FILENAME);
        }
        outputUri = FileProvider.getUriForFile(this, AUTHORITY, output);
        if (savedInstanceState == null) {
            i.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ClipData clip = ClipData.newUri(getContentResolver(), "A photo", outputUri);
                i.setClipData(clip);
                i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    grantUriPermission(packageName, outputUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
            try {
                startActivityForResult(i, CAMERA_REQUESTCODE);
            } catch (ActivityNotFoundException e) {
                Utils.showToast(this, getString(R.string.msg_no_camera));
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQUESTCODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraIntent(null);
            } else
                Snackbar.make(layout, "Permission Decline, Ask again", Snackbar.LENGTH_LONG).setAction("Try Again", v -> openCamera(null)).show();
        } else if (requestCode == GALLERY_REQUESTCODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGalleryIntent();
            } else
                Snackbar.make(layout, "Permission Declined, Ask again", Snackbar.LENGTH_LONG).setAction("Try Again", v -> openGallery()).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUESTCODE:
                    if (output == null)
                        userCancel();
                    else
                        setResultForPreviousActivity(output.getAbsolutePath());
                    break;
                case GALLERY_REQUESTCODE:
                    if (data == null) {
                        userCancel();
                        return;
                    }
                    Uri selectedImage = data.getData();
                    imageCallback = new ImageCallback();
                    imageCallback.initializeCommunicator(new ImageCallback.ImageCommunicator() {
                        @Override
                        public void imagePathFromFile(String image) {

                        }

                        @Override
                        public void imagePathFromString(String image) {
                            setResultForPreviousActivity(image);
                        }

                        @Override
                        public void showErrorMessage(String message) {
                            Utils.showToast(ImagePickerActivity.this, message);
                        }
                    });
                    imageCallback.imagePathFromString(getRealPathFromURI(selectedImage));
                    break;
            }
        } else
            userCancel();
    }

    private void userCancel() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void setResultForPreviousActivity(String image) {
        Intent intent = new Intent();
        intent.putExtra("filePath", image);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        if (imageCallback != null)
            imageCallback.unsubscribeImageCallback();
        super.onDestroy();
    }

    public String getRealPathFromURI(Uri uri) {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(this, uri)) {
                if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    uri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                } else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("image".equals(type))
                        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    else if ("video".equals(type))
                        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    else if ("audio".equals(type))
                        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    selection = "_id=?";
                    selectionArgs = new String[]{
                            split[1]
                    };
                }
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            try {
                Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);
                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
                cursor.close();
            } catch (Exception e) {
                //
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return selection;
    }
}

