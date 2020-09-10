package com.example.dream.ui.main;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

//
    public com.example.dream.ui.main.Fragment1 mFragment1 = null;
    public com.example.dream.ui.main.Fragment2 mFragment2 = null;
    public com.example.dream.ui.main.Fragment3 mFragment3 = null;
    public com.example.dream.ui.main.Fragment4 mFragment4 = null;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position) {
            case 0:
                return mFragment1 = new com.example.dream.ui.main.Fragment1();
            case 1:
                return mFragment2 = new com.example.dream.ui.main.Fragment2();
            case 2:
                return mFragment3 = new com.example.dream.ui.main.Fragment3();
            case 3:
                return mFragment4 = new com.example.dream.ui.main.Fragment4();
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}