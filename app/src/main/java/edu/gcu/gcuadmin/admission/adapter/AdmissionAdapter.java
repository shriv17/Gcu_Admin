package edu.gcu.gcuadmin.admission.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.gcu.gcuadmin.constants.AppConstants;

/**
 * Created by Shrivats on 3/28/2017.
 */

public class AdmissionAdapter extends FragmentStatePagerAdapter{

    private int tabCount=0;
    private String[] title = null;
    private Fragment[] pagerFragments = null;

    public AdmissionAdapter(FragmentManager fm) {
        super(fm);
        this.tabCount = AppConstants.getPagerTabCount();
        title = AppConstants.getPagerTitle();
        pagerFragments = AppConstants.getPagerFragments();
    }

    @Override
    public Fragment getItem(int position) {
        return pagerFragments[position];
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
