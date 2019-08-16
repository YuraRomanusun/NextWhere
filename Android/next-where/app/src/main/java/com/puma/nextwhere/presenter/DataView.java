package com.puma.nextwhere.presenter;
/**
 * Created by rajesh on 17/2/17.
 */

public interface DataView {
    void showWait();
    void removeWait();
    void onFailure(String appErrorMessage);
}
