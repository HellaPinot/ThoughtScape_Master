package com.hellapinot.thomassmith.thoughtscape_master.Activities;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.hellapinot.thomassmith.thoughtscape_master.Adapters.SectionsPagerAdapter;
import com.hellapinot.thomassmith.thoughtscape_master.CurrentStatus;
import com.hellapinot.thomassmith.thoughtscape_master.DataBaseHelper;
import com.hellapinot.thomassmith.thoughtscape_master.FragmentManager;
import com.hellapinot.thomassmith.thoughtscape_master.DateUtil;
import com.hellapinot.thomassmith.thoughtscape_master.R;

public class MainActivity extends BaseActivity {



    private static final String TAG = "MainActivity";
    DataBaseHelper mDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), CurrentStatus.DAILYDIARY);
        mViewPager = findViewById(R.id.daily_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        if(getRestorePage() < 0) {
            mViewPager.setCurrentItem(mSectionsPagerAdapter.getCount());
        } else{
            mViewPager.setCurrentItem(getRestorePage());
            Log.d(TAG, "onCreate: page set to: " + getRestorePage());
        }


        if(!dataLoaded){
            mDataBaseHelper = new DataBaseHelper(this);
            LoadData(mDataBaseHelper);
        } else if(mIdeas.isEmpty()) {
            createDiary(0);
        }


        BottomNavigationView navigation = findViewById(R.id.navigation_view);
        navigation.setSelectedItemId(R.id.idea_diary);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = (FragmentManager) getVisibleFragment();
                addEntry(((FragmentManager) getVisibleFragment()).getAdapter().getSectionNumber());
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
