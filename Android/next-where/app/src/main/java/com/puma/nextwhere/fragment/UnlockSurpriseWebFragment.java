package com.puma.nextwhere.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.puma.nextwhere.R;
import com.puma.nextwhere.activity.RequestingPermission;
import com.puma.nextwhere.customview.CustomWebChromeClient;
import com.puma.nextwhere.customview.CustomWebView;
import com.puma.nextwhere.customview.CustomWebViewClient;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.CheckPermission;
import com.puma.nextwhere.interfaces.OnWebViewListener;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.preference.Preference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.puma.nextwhere.activity.RequestingPermission.EXTRA_REQUEST_CODE;

/**
 * Created by PingChoi on 2/7/2018.
 */

public class UnlockSurpriseWebFragment extends Fragment implements OnWebViewListener{
    @BindView(R.id.web_view)
    CustomWebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Context context;

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private PermissionRequest mPermissionRequest;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unlock_suprise_web_fragment, container, false);
        ButterKnife.bind(this, view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasCameraPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
            Log.d("Permission Check:", "has camera permission: " + hasCameraPermission);
            List<String> permissions = new ArrayList<>();
            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]),111);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.setWebViewClient(new CustomWebViewClient(this) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( url.startsWith("intent://instagram.com/")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.setPackage("com.instagram.android");

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/")));
                    }
                    return true;

                }

                return  false;
            }
        });
        webView.defaultInit(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setGeolocationDatabasePath(context.getFilesDir().getPath());

        webView.setWebChromeClient(new WebChromeClient(){
            //@TargetApi(Build.VERSION_CODES.LOLLIPOP);
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }
            }

            @Override
            public void onPermissionRequestCanceled(PermissionRequest request) {
                Log.d("Check Permision:", "onPermissionRequestCanceled");
            }

            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        LoginApiResponse response = Preference.getInstance().getUserInfo();
        String mode = "";
        String id = "";
        if ( response != null ){
            String code = response.getCodigo();
            if ( code.charAt(7) == 'M' || code.charAt(7) == 'H' ){
                mode = "hunt";
            }
            else if ( code.charAt(7) == 'S' ){
                mode = "search";
                //mode = "photo_shoot";
            }
            id = response.getIdUsuario();
        }
        String url = "https://www.next-where.com/webapp/index.php";

        if ( mode.length() > 0 && id.length() > 0 ){
            url += "?mode=" + mode + "&id=" + id;
        }
        Log.e(this.getClass().getName(), "url= " + url);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Log.e(this.getClass().getName(), "asdf");
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);

            webView.loadUrl(url);
        }

        //webView.loadUrl(url);



        if (CheckPermission.getCheckPermission().isLocationPermissionGranted(context) == false ) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, AppConstants.PERMISSION_CHECK_CODE.LOCATION);
        }
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onScroll(int l, int t, int oldl, int oldt) {
    }

    @Override
    public void onFinish(String url) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onUrlChange(String url) {
    }

    @Override
    public void onProgressChanged(int newProgress) {
        if ( newProgress == 100 ){
            progressBar.setVisibility(View.GONE);
        }
    }
}
