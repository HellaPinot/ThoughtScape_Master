package com.hellapinot.thomassmith.thoughtscape_master.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.hellapinot.thomassmith.thoughtscape_master.CurrentStatus;
import com.hellapinot.thomassmith.thoughtscape_master.FragmentManager;
import com.hellapinot.thomassmith.thoughtscape_master.DateUtil;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private int numDaysSinceEpoch;
    private CurrentStatus mCurrentStatus;
    private static final String TAG = "SectionsPagerAdapter";


    public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm, CurrentStatus mCurrentStatus) {
        super(fm);
        this.mCurrentStatus = mCurrentStatus;
        numDaysSinceEpoch = DateUtil.DaysSinceEpoch();
        Log.d(TAG, "SectionsPagerAdapter: called" + mCurrentStatus);

    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: called " + mCurrentStatus);
        switch(mCurrentStatus) {
            case DAILYDIARY:
                return FragmentManager.newInstance(position + 1, mCurrentStatus);
            case FOCUSED:
                return FragmentManager.newInstance(1, mCurrentStatus);
            case PROJECTSETUP:
                return FragmentManager.newInstance(position + 1, mCurrentStatus);
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        switch(mCurrentStatus) {
            case DAILYDIARY:
                return numDaysSinceEpoch;
            case FOCUSED:
                return 1;
            case PROJECTSETUP:
                return 2;
            default:
                return 0;
        }
    }

    public void setCurrentStatus(CurrentStatus currentStatus) {
        mCurrentStatus = currentStatus;
    }
}
