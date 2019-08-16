package com.puma.nextwhere.apicallbacks;

import com.puma.nextwhere.model.ExploreCityGuideApiResponse;
import com.puma.nextwhere.presenter.DataView;

import java.util.List;

/**
 * Created by rajesh on 24/12/17.
 */

public interface IEventGuideCallback extends DataView {
    void setEventGuideData(List<ExploreCityGuideApiResponse> cityGuideResponse);

    void showNoDataView();
}
