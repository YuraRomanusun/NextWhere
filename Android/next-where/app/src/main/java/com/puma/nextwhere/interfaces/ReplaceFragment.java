package com.puma.nextwhere.interfaces;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by rajesh on 1/6/17.
 */

public interface ReplaceFragment {

    void replaceFragment(Fragment activeFragment, String tag);

    void finishCurrentActivity();

    void openCode(int code);

    FragmentManager getSupportFragmentManager();
}
