package com.hellapinot.thomassmith.thoughtscape_master.activities;
import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hellapinot.thomassmith.thoughtscape_master.Adapters.SectionsPagerAdapter;
import com.hellapinot.thomassmith.thoughtscape_master.CurrentStatus;
import com.hellapinot.thomassmith.thoughtscape_master.FragmentManager;
import com.hellapinot.thomassmith.thoughtscape_master.DateUtil;
import com.hellapinot.thomassmith.thoughtscape_master.R;

public class DailyDiaryActivity extends BaseActivity {



    private static final String TAG = "DailyDiaryActivity";
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), CurrentStatus.DAILYDIARY);
        mViewPager = findViewById(R.id.daily_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Used to restore diary page that was shown before switching to focused view
        if(getRestorePage() < 0) {
            mViewPager.setCurrentItem(mSectionsPagerAdapter.getCount());
        } else{
            mViewPager.setCurrentItem(getRestorePage());
            Log.d(TAG, "onCreate: page set to: " + getRestorePage());
        }

        // Creates / Restores Diary on Startup
        if(mIdeas.isEmpty()) {
            createDiary(0);
            loadDiaryData();
        }


        BottomNavigationView navigation = findViewById(R.id.navigation_view);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Adds new entry in Diary
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = (FragmentManager) getVisibleFragment();
                addEntry(context, ((FragmentManager) getVisibleFragment()).getAdapter().getSectionNumber());
                fragmentManager.getAdapter().notifyDataSetChanged();
                fragmentManager.getRecyclerView().smoothScrollToPosition(getmIdeas(((FragmentManager) getVisibleFragment()).getAdapter().getSectionNumber()).size());

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createDiary(int num){
        for(int x = num; x < DateUtil.DaysSinceEpoch(); x++){
            addSection(x);
        }
    }

    @Override
    protected void onStop() {
        FragmentManager fragmentManager = (FragmentManager) getVisibleFragment();
        setRestorePage(fragmentManager.getSectionNumber());
        super.onStop();
    }


}
