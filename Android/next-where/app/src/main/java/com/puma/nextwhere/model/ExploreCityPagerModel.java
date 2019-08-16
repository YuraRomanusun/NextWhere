package com.puma.nextwhere.model;

import android.support.v4.app.Fragment;

/**
 * Created by rajesh on 1/6/17.
 */

public class ExploreCityPagerModel {
    private Fragment activeFragment;
    private String title;

    public ExploreCityPagerModel(Fragment activeFragment, String title) {
        this.activeFragment = activeFragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return activeFragment;
    }

    public CharSequence getTitle() {
        return title;
    }
}
