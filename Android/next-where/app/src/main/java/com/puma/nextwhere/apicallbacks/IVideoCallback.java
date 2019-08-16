package com.puma.nextwhere.apicallbacks;

import com.puma.nextwhere.presenter.DataView;

/**
 * Created by rajesh on 8/10/17.
 */

public interface IVideoCallback extends DataView {
    void showVideo(String videoId, String video);
}
