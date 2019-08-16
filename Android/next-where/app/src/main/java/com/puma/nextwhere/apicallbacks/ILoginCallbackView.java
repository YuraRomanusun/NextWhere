package com.puma.nextwhere.apicallbacks;

import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.presenter.DataView;

/**
 * Created by rajesh on 17/8/17.
 */

public interface ILoginCallbackView extends DataView {
    void onSuccess(LoginApiResponse data);

}
