package com.hellapinot.thomassmith.thoughtscape_master.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.hellapinot.thomassmith.thoughtscape_master.Adapters.SectionsPagerAdapter;
import com.hellapinot.thomassmith.thoughtscape_master.DataBaseHelper;
import com.hellapinot.thomassmith.thoughtscape_master.DataStructs.IdeaStruct;
import com.hellapinot.thomassmith.thoughtscape_master.DateUtil;
import com.hellapinot.thomassmith.thoughtscape_master.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseActivity handles the core diary data, the switching of intents between Diary
 * and Focused activities and loads data from SQLite class DataBaseHelper into HashMap
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    protected static Map<Integer, ArrayList<IdeaStruct>> mIdeas = new HashMap<>();
    protected static ArrayList<IdeaStruct> mFocusedIdeas;
    protected SectionsPagerAdapter mSectionsPagerAdapter;
    protected ViewPager mViewPager;
    protected static int restorePage = -1;


    //Switches between Diary and Focused activity using Nav Bar
    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.focus_idea:
                    Log.d(TAG, "onNavigationItemSelected: clicked");
                    getActivity(FocusedActivity.class);
                    return true;
                    
                case R.id.idea_diary:
                    Log.d(TAG, "onNavigationItemSelected: idea clicked");
                    getActivity(DailyDiaryActivity.class);
                    return true;
            }
            return false;
        }
    };

    // Takes data from SQLite and adds into mIdeas
    protected void loadDiaryData(){
        Cursor data;
        for(int day = 0; day <= DateUtil.DaysSinceEpoch(); day++){
            data = DataBaseHelper.getInstance(this).getDiaryData(day);
            while(data.moveToNext()){
                loadEntry(this,day-1, data.getInt(0), data.getString(2), data.getString(3), data.getString(4), data.getString(5), data.getString(6));
            }
        }
    }

    //Switches intent from and to any class pairs
    public void getActivity(Class<?> context){
        Intent intent = new Intent(this, context);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 0);
        overridePendingTransition(0,0);
        this.finish();
    }


    //Returns fragment visible on screen
    /*
    BUG NOTE: When swiping between days in Diary, takes half a second to register page switch.
    Can result in adding entry to previous Diary date.
     */
    public Fragment getVisibleFragment(){
        for(Fragment i: getSupportFragmentManager().getFragments()){
            if(i.getUserVisibleHint()){
                return i;
            }
        }
        return null;
    }

    public static void addEntry(Context context, int position){
        mIdeas.get(position).add(new IdeaStruct(context, position, "",""));
    }

    public static void loadEntry(Context context, int position, int dbID, String title, String body, String startDate, String endDate, String focused){
        mIdeas.get(position).add(new IdeaStruct(context, dbID, title, body, startDate, endDate, focused));
    }


    public static void updateEntry(int sectionNumber, int position, String titleUpdate, String bodyUpdate){
        mIdeas.get(sectionNumber).get(position).setTitle(titleUpdate);
        mIdeas.get(sectionNumber).get(position).setBody(bodyUpdate);
    }

    public static void deleteEntry(int sectionNumber, int position, Context context){
        DataBaseHelper.getInstance(context).deleteDiaryEntry(mIdeas.get(sectionNumber).get(position).getDbID());
        mIdeas.get(sectionNumber).remove(position);
    }


    public static void addSection(int position){
        mIdeas.put(position, new ArrayList<IdeaStruct>());
    }

    public static IdeaStruct getEntry(int sectionNumber, int position){
        return mIdeas.get(sectionNumber).get(position);
    }

    public static ArrayList<IdeaStruct> getmIdeas(int position) {
        return mIdeas.get(position);
    }

    public static ArrayList<IdeaStruct> getFocusedIdeas() {
        return mFocusedIdeas;
    }

    public static  int getRestorePage() {
        return restorePage;
    }

    public static void setRestorePage(int restorePages) {
        restorePage = restorePages;
    }

}
