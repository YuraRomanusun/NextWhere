package com.puma.nextwhere.apicallbacks;

import com.puma.nextwhere.model.ExploreCityGuideApiResponse;
import com.puma.nextwhere.presenter.DataView;

import java.util.List;

/**
 * Created by rajesh on 8/9/17.
 */

public interface ICityGuideCallback extends DataView {
    void cityGuideResponse(List<ExploreCityGuideApiResponse> cityGuideResponse);

    void setBarData(List<ExploreCityGuideApiResponse> cityGuideResponse);

    void setRestrauntData(List<ExploreCityGuideApiResponse> cityGuideResponse);


    void showNoDataView();

}
