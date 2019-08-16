package com.puma.nextwhere.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.puma.nextwhere.R;
import com.puma.nextwhere.helper.Utils;


public class BaseActivity extends AppCompatActivity implements Handler.Callback {

    public Context _context = null;

    public Handler _handler = null;

    private ProgressDialog _progressDlg;

    private Vibrator _vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _context = this;

        _vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        _handler = new Handler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {

        closeProgress();

        try {
            if (_vibrator != null)
                _vibrator.cancel();
        } catch (Exception e) {
        }
        _vibrator = null;

        super.onDestroy();
    }


    public void showProgress(boolean cancelable) {

        closeProgress();

        _progressDlg = new ProgressDialog(_context, R.style.MyTheme);
        _progressDlg.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
        _progressDlg.setCancelable(cancelable);
        _progressDlg.show();
    }

    public void showProgress() {
        showProgress(false);
    }

    public void closeProgress() {

        if (_progressDlg == null) {
            return;
        }

        _progressDlg.dismiss();
        _progressDlg = null;
    }

    public void showAlertDialog(String msg) {

        AlertDialog alertDialog = new AlertDialog.Builder(_context).create();

        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage(msg);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, _context.getString(R.string.ok),

                (dialogInterface, i) -> {

                });
        //alertDialog.setIcon(R.drawable.banner);
        alertDialog.show();

    }

    /**
     * show toast
     *
     * @param toast_string
     */
    public void showToast(String toast_string) {
        Utils.showToast(_context, toast_string);
    }

    public void vibrate() {

        if (_vibrator != null)
            _vibrator.vibrate(500);
    }

    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what) {

            default:
                break;
        }

        return false;
    }
}
