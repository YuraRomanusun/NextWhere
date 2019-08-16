package com.puma.nextwhere.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.puma.nextwhere.R;
import com.puma.nextwhere.activity.RequestingPermission;
import com.puma.nextwhere.adapter.UnlockSurpriseAdapter;
import com.puma.nextwhere.apicallbacks.IUnlockSupriseCallback;
import com.puma.nextwhere.database.databaseoperation.UnlockSurpriseDbOperation;
import com.puma.nextwhere.database.tables.UnlockSurpriseData;
import com.puma.nextwhere.dialog.CaptureImage;
import com.puma.nextwhere.dialog.CheckInDialogFragment;
import com.puma.nextwhere.dialog.QuestionDialog;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.CheckPermission;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.interfaces.ReplaceFragment;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.network.ServiceNetwork;
import com.puma.nextwhere.presenter.DataPresontar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.puma.nextwhere.activity.RequestingPermission.EXTRA_REQUEST_CODE;

/**
 * Created by rtiwari on 10/9/2017.
 */

public class UnlockSurpriseFragment extends Fragment implements UnlockSurpriseAdapter.IClickCallback,
        IUnlockSupriseCallback {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.loading)
    FrameLayout loading;
    private UnlockSurpriseAdapter unlockSurpriseAdapter;
    private ArrayList<UnlockSurpriseData> unlockData;
    private Unbinder unbinder;
    private int selectedPosition;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        context = null;
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unlock_suprise_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        unlockData = new ArrayList<>();
        unlockSurpriseAdapter = new UnlockSurpriseAdapter(unlockData);
        unlockSurpriseAdapter.initializeCallback(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(unlockSurpriseAdapter);
        return view;
    }

    @OnClick(R.id.btGetSurprise)
    public void onSupriseButtonClick() {
        int count = 0;
        for (UnlockSurpriseData UnlockSupriseData : unlockData) {
            if (UnlockSupriseData.getCategoriaOtraPrenda() != null
                    && !UnlockSupriseData.getCategoriaOtraPrenda().isEmpty()) {
                count++;
            }
        }

        // If all the tasks are completed then user must be able to view second
        // screen of surprise
        ViewUnlockSupriseFragment.showSecondScreen = count == unlockData.size();
        if (count > 2) {
            ReplaceFragment replaceFragment = (ReplaceFragment) getActivity();
            replaceFragment.openCode(AppConstants.OPEN_SURPRISE_SCREEN);
        } else {
            onFailure(getString(R.string.error_task_incomplete));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DataPresontar dataPresontar = new DataPresontar(new ServiceNetwork());
        dataPresontar.initializeUnlockSupriseCallback(this);
        LoginApiResponse loginApiResponse = com.puma.nextwhere.preference.Preference.getInstance().getUserInfo();
        dataPresontar.getUnlockSupriseData(loginApiResponse.getIdDestino());
    }

    @Override
    public void onItemClick(int position) {
        if (position != 0) {
            String valueFilled = unlockData.get(position - 1).getCategoriaOtraPrenda();
            if (valueFilled == null || valueFilled.isEmpty()) {
                onFailure(getString(R.string.error_complete_previous_task));
                return;
            }
        }
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment screenToOpen = null;
        UnlockSurpriseData UnlockSupriseData = unlockData.get(position);
        selectedPosition = position;
        switch (UnlockSupriseData.getCategoriaPrenda()) {
            case AppConstants.SupriseSideIcon.PHOTOGRAPH:
                screenToOpen = CaptureImage.getInstance(UnlockSupriseData, position);
                break;
            case AppConstants.SupriseSideIcon.QUESTION:
                screenToOpen = QuestionDialog.getInstance(UnlockSupriseData, position);
                break;
            case AppConstants.SupriseSideIcon.CHECK_IN:
                if (CheckPermission.getCheckPermission().isLocationPermissionGranted(context)) {
                    screenToOpen = CheckInDialogFragment.getInstance(UnlockSupriseData, position);
                } else {
                    Intent intent = new Intent(context, RequestingPermission.class);
                    intent.putExtra(EXTRA_REQUEST_CODE, AppConstants.PERMISSION_CHECK_CODE.LOCATION);
                    startActivityForResult(intent, AppConstants.PERMISSION_CHECK_CODE.LOCATION);
                }
        }

        // Create and show the dialog.
        if (screenToOpen != null) {
            screenToOpen.setTargetFragment(this, 1);
            screenToOpen.show(ft, "dialog");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.PERMISSION_CHECK_CODE.LOCATION && resultCode == Activity.RESULT_OK) {

            onItemClick(selectedPosition);
        }
    }

    @Override
    public void showWait() {
        if (context != null) {
            loading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void removeWait() {
        if (context != null) {
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(String appErrorMessage) {
        if (context != null) {
            Utils.showToast(context, appErrorMessage);
        }
    }

    @Override
    public void unlockSupriseData(List<UnlockSurpriseData> apiData) {
        if (context != null) {
            unlockData.addAll(apiData);
            unlockSurpriseAdapter.notifyDataSetChanged();
        }
    }

    public void imageUploaded(UnlockSurpriseData unlockSupriseData, int updatePosition) {
        UnlockSurpriseDbOperation.getInstance().updateUnlockSurprise(unlockSupriseData, null);
        unlockData.set(updatePosition, unlockSupriseData);
        unlockSurpriseAdapter.notifyItemChanged(updatePosition);
        unlockSurpriseAdapter.notifyItemChanged(updatePosition + 1);
    }
}
