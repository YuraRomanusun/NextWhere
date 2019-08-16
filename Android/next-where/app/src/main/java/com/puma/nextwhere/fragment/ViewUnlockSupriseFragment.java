package com.puma.nextwhere.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puma.nextwhere.R;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.model.SupriseInfo;
import com.puma.nextwhere.preference.Preference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import butterknife.Unbinder;

/**
 * Created by rajesh on 28/10/17.
 */

public class ViewUnlockSupriseFragment extends Fragment {
    @BindView(R.id.tv_supriseNumber)
    TextView supriseNumber;
    @BindView(R.id.iv_offerBackground)
    ImageView offerBackground;
    @BindView(R.id.iv_offerLogo)
    ImageView offerLogo;
    @BindView(R.id.tv_description)
    TextView supriseDescription;
    @BindView(R.id.iv_code)
    ImageView codeImage;
    @BindView(R.id.tv_code)
    TextView codeText;
    @BindView(R.id.tv_address)
    TextView address;
    @BindView(R.id.tv_note)
    TextView terms;
    List<SupriseInfo> offerSize;
    private Unbinder unbinder;
    public static boolean showSecondScreen;
    Context context;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewunlocksuprise, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (Utils.isObjectNotNull(Preference.getInstance().getUserInfo())) {
            offerSize = Preference.getInstance().getUserInfo().getSorpresas();
            if (Utils.validateList(offerSize)) {
                setSupriseInfo("1", offerSize.get(0));
            }
        }
        return view;
    }

    private void setSupriseInfo(String value, SupriseInfo supriseInfo) {
        LoginApiResponse loginApiResponse = Preference.getInstance().getUserInfo();
        Glide.with(this).load(loginApiResponse.getPremio() + "" +
                supriseInfo.getLogo()).into(offerLogo);
        Glide.with(this).load(loginApiResponse.getPremio() + "" +
                supriseInfo.getImagen()).into(offerBackground);
        Glide.with(this).load(loginApiResponse.getPremio() + "" +
                supriseInfo.getImagenBarcode()).into(codeImage);
        codeText.setText(getString(R.string.code, supriseInfo.getCode()));
        supriseDescription.setText(supriseInfo.getDescripcion());
        address.setText(supriseInfo.getStreet());
        terms.setText(supriseInfo.getTerms());
        supriseNumber.setText(getString(R.string.surpriseText, value, String.valueOf(offerSize.size())));
    }

    @OnTouch(R.id.tv_supriseNumber)
    public boolean performBackClick(MotionEvent event) {

        if (event.getRawX() >= (supriseNumber.getCompoundDrawables()[2].getBounds().width())) {
            if (offerSize != null && offerSize.size() > 1 && showSecondScreen) {
                supriseNumber.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close_white, 0,
                        R.drawable.ic_next, 0);
                setSupriseInfo("2", offerSize.get(1));
            } else {
                Utils.showToast(context, getString(R.string.error_task_all_incomplete));
            }
            return true;
        } else {
            if (supriseNumber.getCompoundDrawables()[0] != null) {
                if (event.getRawX() <= (supriseNumber.getCompoundDrawables()[0].getBounds().width())) {
                    if (offerSize != null && offerSize.size() > 1) {
                        supriseNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                R.drawable.ic_next, 0);
                        setSupriseInfo("1", offerSize.get(0));
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
