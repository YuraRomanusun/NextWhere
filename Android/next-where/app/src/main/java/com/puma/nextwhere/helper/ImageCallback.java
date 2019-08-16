package com.puma.nextwhere.helper;

import android.graphics.Bitmap;
import android.util.Base64;

import com.puma.nextwhere.imagecompressor.Compressor;

import java.io.ByteArrayOutputStream;
import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rajesh on 6/4/17.
 */

public class ImageCallback {
    private CompositeDisposable mSubscription;
    private ImageCommunicator imageCallbackHelper;

    public interface ImageCommunicator {
        void imagePathFromFile(String image);

        void imagePathFromString(String image);

        void showErrorMessage(String message);
    }


    public void initializeCommunicator(ImageCommunicator imageCommunicator) {
        this.imageCallbackHelper = imageCommunicator;
    }

    public void unsubscribeImageCallback() {
        if (mSubscription != null && !mSubscription.isDisposed()) {
            mSubscription.clear();
        }
    }

    public void imagePathFromFile(final File imagePath) {
        mSubscription = new CompositeDisposable();
        mSubscription.add(Observable.defer(() -> Observable.just(retriveBitmapImage(imagePath)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(image -> imageCallbackHelper.imagePathFromFile(image), throwable -> imageCallbackHelper.showErrorMessage(throwable.getMessage())));
    }

    private String retriveBitmapImage(File imagePath) {
        return get64BaseImage(Compressor.getDefault().compressToBitmap(imagePath));
    }

    private String get64BaseImage(Bitmap imageLocal) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageLocal.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        byte[] byte_arr = stream.toByteArray();
        return Base64.encodeToString(byte_arr, 0);
    }


    public void imagePathFromString(String imageLocal) {
        mSubscription = new CompositeDisposable();
        mSubscription.add(Observable.defer(() -> Observable.just(retriveImage(imageLocal)))
                .filter(fileName -> fileName != null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(image -> imageCallbackHelper.imagePathFromString(image.getAbsolutePath()), throwable -> imageCallbackHelper.showErrorMessage(throwable.getMessage())));
    }

    private File retriveImage(String image) {
        return Compressor.getDefault().compressToFile(new File(image));
    }
}
