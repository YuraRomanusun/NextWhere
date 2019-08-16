package com.puma.nextwhere.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.puma.nextwhere.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 11/15/2017.
 */

public class SubmissionDialog extends AppCompatDialogFragment {
    @BindView(R.id.text_question)
    TextView taskCompletionMessage;

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
        View view = inflater.inflate(R.layout.dialog_submit_result, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static DialogFragment getInstance(Callback callback) {
        SubmissionDialog submissionDialog = new SubmissionDialog();
        submissionDialog.callback = callback;
        return submissionDialog;
    }

    @OnClick(R.id.button_submit)
    public void submit() {
        callback.finishParent();
        dismiss();
    }

    public interface Callback {
        void finishParent();
    }

    Callback callback;
}
