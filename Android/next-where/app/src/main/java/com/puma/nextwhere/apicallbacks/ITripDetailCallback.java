package com.puma.nextwhere.apicallbacks;

import com.puma.nextwhere.model.HotelDetails;
import com.puma.nextwhere.presenter.DataView;

/**
 * Created by rajesh on 8/9/17.
 */

public interface ITripDetailCallback extends DataView {

    void setHotelDetails(HotelDetails data);
}
