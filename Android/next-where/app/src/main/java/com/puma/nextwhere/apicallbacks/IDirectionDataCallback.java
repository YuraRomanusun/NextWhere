package com.puma.nextwhere.apicallbacks;

import com.puma.nextwhere.model.DirectionData;
import com.puma.nextwhere.presenter.DataView;

import java.util.List;

/**
 * Created by kAmikAzE on 1/30/18.
 */

public interface IDirectionDataCallback extends DataView {
    void setDirectionData(DirectionData directionData);

    void showNoDataView();
}
