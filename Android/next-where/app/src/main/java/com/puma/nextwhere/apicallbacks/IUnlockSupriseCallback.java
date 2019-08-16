package com.puma.nextwhere.apicallbacks;

import com.puma.nextwhere.database.tables.UnlockSurpriseData;
import com.puma.nextwhere.presenter.DataView;

import java.util.List;

/**
 * Created by rajesh on 28/10/17.
 */

public interface IUnlockSupriseCallback extends DataView {
    void unlockSupriseData(List<UnlockSurpriseData> data);
}
