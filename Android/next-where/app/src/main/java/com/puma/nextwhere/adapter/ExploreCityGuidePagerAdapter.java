package com.puma.nextwhere.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.puma.nextwhere.model.ExploreCityPagerModel;

import java.util.ArrayList;

/**
 * Created by rajesh on 1/6/17.
 */

public class ExploreCityGuidePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<ExploreCityPagerModel> adapterModel;

    public ExploreCityGuidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setItems(ArrayList<ExploreCityPagerModel> adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public Fragment getItem(int position) {
        return adapterModel.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return adapterModel.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return adapterModel == null ? 0 : adapterModel.size();
    }
}
