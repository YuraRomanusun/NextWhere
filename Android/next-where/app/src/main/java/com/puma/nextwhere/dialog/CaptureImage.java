package com.puma.nextwhere.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puma.nextwhere.R;
import com.puma.nextwhere.activity.ImagePickerActivity;
import com.puma.nextwhere.database.tables.UnlockSurpriseData;
import com.puma.nextwhere.fragment.UnlockSurpriseFragment;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.imagecompressor.Compressor;
import com.puma.nextwhere.preference.Preference;
import com.puma.nextwhere.share.ShareDialogHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by rtiwari on 10/9/2017.
 */

public class CaptureImage extends AppCompatDialogFragment {

    @BindView(R.id.submissionSuccessFull)
    ConstraintLayout submissionSuccessfull;
    @BindView(R.id.capturedImage)
    ImageView capturedImage;
    @BindView(R.id.imageUploadSuccess)
    ImageView imageUpload;
    @BindView(R.id.uploadingProgress)
    ProgressBar imageLoading;
    @BindView(R.id.messageUploadSuccess)
    TextView messageUploadSuccess;
    @BindView(R.id.tvTakePhotoMessage)
    TextView takeImageText;
    int updatePosition;
    private final int IMAGER_RESULT = 1;
    private File userImage_value;
    Context context;
    private UnlockSurpriseData unlockSupriseData;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_capture_image, container, false);
        unbinder = ButterKnife.bind(this, view);
        takeImageText.setText(unlockSupriseData.getDescripcionPrenda());
        Glide.with(this).load(unlockSupriseData.getImagenPrenda()).into(capturedImage);
        return view;
    }

    @OnClick(R.id.ivCloseDialog)
    public void closeDialog() {
        dismiss();
    }

    @OnClick(R.id.tvUpload)
    public void uploadClick() {
        if (userImage_value == null) {
            Utils.showToast(context, getString(R.string.error_select_image));
            return;
        }
        imageLoading.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            if (context != null) {
                imageLoading.setVisibility(View.GONE);
                imageUpload.setVisibility(View.VISIBLE);
                submissionSuccessfull.setVisibility(View.VISIBLE);
                messageUploadSuccess.setText(getString(R.string.onImageUpload, Preference.getInstance()
                        .getUserInfo().getNombre()));
            }
        }, 2000);
    }

    @OnClick(R.id.tvCamera)
    public void openCamera() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.KEY, AppConstants.CAMERA);
        startActivityForResult(intent, IMAGER_RESULT);
    }

    @OnClick(R.id.tvDone)
    public void doneClick() {
        if (getTargetFragment() != null && getTargetFragment() instanceof UnlockSurpriseFragment) {
            UnlockSurpriseFragment unlockSurpriseFragment = (UnlockSurpriseFragment) getTargetFragment();
            unlockSupriseData.setCategoriaOtraPrenda("x");
            unlockSurpriseFragment.imageUploaded(unlockSupriseData, updatePosition);
        }
        dismiss();
    }

    @OnClick(R.id.instagramShare)
    public void instagramShare() {
        ShareDialogHelper shareDialogHelper = new ShareDialogHelper();
        shareDialogHelper.instagramShare(getActivity(), userImage_value.getAbsolutePath());
    }

    @OnClick(R.id.facebookShare)
    public void facebookShare() {
        ShareDialogHelper shareDialogHelper = new ShareDialogHelper();
        shareDialogHelper.facebookShare(getActivity(),
                Compressor.getDefault().compressToBitmap(userImage_value));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            userImage_value = Compressor.getDefault().compressToFile(new File(data.getStringExtra("filePath")));
            Glide.with(this).load(Uri.fromFile(userImage_value)).into(capturedImage);
        }
    }

    public static CaptureImage getInstance(UnlockSurpriseData UnlockSupriseData, int position) {
        CaptureImage captureImage = new CaptureImage();
        captureImage.unlockSupriseData = UnlockSupriseData;
        captureImage.updatePosition = position;
        return captureImage;
    }
}
