package com.puma.nextwhere.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puma.nextwhere.R;
import com.puma.nextwhere.database.tables.UnlockSurpriseData;
import com.puma.nextwhere.fragment.UnlockSurpriseFragment;
import com.puma.nextwhere.helper.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 11/15/2017.
 */

public class QuestionDialog extends AppCompatDialogFragment implements SubmissionDialog.Callback {
    @BindView(R.id.text_question)
    TextView question;
    @BindView(R.id.image_bg)
    ImageView imageBg;
    @BindView(R.id.edit_answer)
    EditText answer;
    private UnlockSurpriseData unlockSurpriseData;
    private int position;
    Context context;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_question, container, false);
        ButterKnife.bind(this, view);
        question.setText(unlockSurpriseData.getNombrePrenda());
        Glide.with(this).load(unlockSurpriseData.getImagenPrenda()).into(imageBg);

        return view;
    }

    public static DialogFragment getInstance(UnlockSurpriseData UnlockSupriseData, int position) {
        QuestionDialog questionDialog = new QuestionDialog();
        questionDialog.unlockSurpriseData = UnlockSupriseData;
        questionDialog.position = position;
        return questionDialog;
    }

    @OnClick(R.id.button_submit)
    public void submitButtonClick() {
        if (answer.getText().toString().equalsIgnoreCase(unlockSurpriseData.getRespuesta())) {
            // DialogFragment.show() will take care of adding the fragment
            // in a transaction.  We also want to remove any currently showing
            // dialog, so make our own transaction and take care of that here.
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("result");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            DialogFragment screenToOpen = SubmissionDialog.getInstance(this);
            // Create and show the dialog.
            screenToOpen.show(ft, "result");

        } else {
            Utils.showToast(context, getString(R.string.error_incorrect_answer));
        }
    }

    @OnClick(R.id.imageButton_close)
    public void close() {
        dismiss();
    }

    @Override
    public void finishParent() {
        if (getTargetFragment() != null && getTargetFragment() instanceof UnlockSurpriseFragment) {
            dismiss();
            UnlockSurpriseFragment unlockSurpriseFragment = (UnlockSurpriseFragment) getTargetFragment();
            unlockSurpriseData.setCategoriaOtraPrenda("x");
            unlockSurpriseFragment.imageUploaded(unlockSurpriseData, position);
        }
    }
}
