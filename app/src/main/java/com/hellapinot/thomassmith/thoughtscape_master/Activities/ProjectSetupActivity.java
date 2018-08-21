package com.hellapinot.thomassmith.thoughtscape_master.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hellapinot.thomassmith.thoughtscape_master.Adapters.FocusAdapter;
import com.hellapinot.thomassmith.thoughtscape_master.Adapters.ProjectTaskAdapter;
import com.hellapinot.thomassmith.thoughtscape_master.Adapters.SectionsPagerAdapter;
import com.hellapinot.thomassmith.thoughtscape_master.CurrentStatus;
import com.hellapinot.thomassmith.thoughtscape_master.FragmentManager;
import com.hellapinot.thomassmith.thoughtscape_master.R;

public class ProjectSetupActivity extends BaseActivity {



    private static final String TAG = "ProjectSetupActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_setup);

        mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager(), CurrentStatus.PROJECTSETUP);
        mViewPager = findViewById(R.id.project_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.project_tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSectionsPagerAdapter.setCurrentStatus(CurrentStatus.FOCUSED);
        FragmentManager.setmCurrentStatus(CurrentStatus.FOCUSED);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = (FragmentManager) getVisibleFragment();
        fragmentManager.onLeavePSA();
        getActivity(FocusedActivity.class);
        super.onBackPressed();
    }
}
