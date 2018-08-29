package com.hellapinot.thomassmith.thoughtscape_master.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.hellapinot.thomassmith.thoughtscape_master.Adapters.SectionsPagerAdapter;
import com.hellapinot.thomassmith.thoughtscape_master.CurrentStatus;
import com.hellapinot.thomassmith.thoughtscape_master.FragmentManager;
import com.hellapinot.thomassmith.thoughtscape_master.R;

import java.util.Calendar;

public class ProjectSetupActivity extends BaseActivity {



    private static final String TAG = "ProjectSetupActivity";
    private static DatePickerDialog.OnDateSetListener mOnDateSetListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_setup);

        mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager(), CurrentStatus.PROJECTSETUP);
        mViewPager = findViewById(R.id.project_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.project_tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }


    //On leaving project setup this restores CurrentStatus back to focused activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSectionsPagerAdapter.setCurrentStatus(CurrentStatus.FOCUSED);
        FragmentManager.setmCurrentStatus(CurrentStatus.FOCUSED);
    }

    //Saves data entered into EditText Fields upon leaving Activity
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = (FragmentManager) getVisibleFragment();
        fragmentManager.onLeavePSA();
        getActivity(FocusedActivity.class);
        super.onBackPressed();
    }

    public static void getDatePicker(final Button mDate, Context context){
        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: called");
                month = month + 1;
                String setDate = dayOfMonth + "/" + month + "/" + year;
                Log.d(TAG, "onDateSet: date: " + setDate);
                mDate.setText(setDate);
                Log.d(TAG, "onDateSet: " + mDate.getText().toString());
            }
        };
        Log.d(TAG, "getDatePicker: called");
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mOnDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


}
