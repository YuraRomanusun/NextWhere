package com.puma.nextwhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.puma.nextwhere.R;
import com.puma.nextwhere.apicallbacks.ILoginCallbackView;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.network.ServiceNetwork;
import com.puma.nextwhere.preference.Preference;
import com.puma.nextwhere.presenter.DataPresontar;
import com.puma.nextwhere.utils.BaseActivity;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginCallbackView {

    @BindView(R.id.etReservationEmail)
    EditText email;
    @BindView(R.id.pbLoading)
    ProgressBar loading;
    @BindView(R.id.edtReservation)
    MaskedEditText edtReservation;
    String reservationValue;
    @BindView(R.id.login_logo)
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Preference.getInstance().getUserInfo() != null) {
            openMenuActivity();
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setFilterForeReservation();
        logo.requestFocus();
//        setDummyData();
    }

    private void setFilterForeReservation() {
        InputFilter[] editFilters = edtReservation.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        edtReservation.setFilters(newFilters);
    }

    private void setDummyData() {
        edtReservation.setText(AppConstants.DUMMY_CODE);
        email.setText(AppConstants.DUMMY_EMAIL);

    }

    @OnClick(R.id.lyt_button)
    public void startMenuActivity() {
        if (TextUtils.isEmpty(edtReservation.getText()) || TextUtils.isEmpty(email.getText())) {
            Utils.showToast(this, getString(R.string.emptyField));
        } else {
            DataPresontar dataPresontar = new DataPresontar(new ServiceNetwork());
            dataPresontar.initializeLoginCallBack(this);
            reservationValue = edtReservation.getText().toString();
            dataPresontar.login(edtReservation.getText().toString(), email.getText().toString());
        }
    }

    @Override
    public void showWait() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        if (loading != null) {
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Utils.showToast(this, appErrorMessage.isEmpty() ? getString(R.string.error_message) : appErrorMessage);
    }

    @Override
    public void onSuccess(LoginApiResponse data) {
        Preference.getInstance().saveReservationCode(reservationValue);
        Preference.getInstance().putLoginDetails(data);
        openMenuActivity();
    }

    private void openMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
