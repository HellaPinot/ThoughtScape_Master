package com.hellapinot.thomassmith.thoughtscape_master;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hellapinot.thomassmith.thoughtscape_master.Activities.BaseActivity;
import com.hellapinot.thomassmith.thoughtscape_master.Activities.ProjectSetupActivity;
import com.hellapinot.thomassmith.thoughtscape_master.Adapters.DailyListAdapter;
import com.hellapinot.thomassmith.thoughtscape_master.Adapters.FocusAdapter;
import com.hellapinot.thomassmith.thoughtscape_master.Adapters.ProjectTaskAdapter;


public  class FragmentManager extends Fragment implements RecyclerItemClickListener.OnRecyclerClickListener {
    
    
    private static final String ARG_SECTION_NUMBER = "section_number";
    private DailyListAdapter adapter;
    private FocusAdapter mFocusAdapter;
    private TextView date;
    private RecyclerView recyclerView;
    private static final String TAG = "FragmentManager";
    private static CurrentStatus mCurrentStatus;
    private int sectionNumber;

    private ProjectTaskAdapter mProjectTaskAdapter;
    private EditText projectTitle;
    private EditText projectBody;
    private EditText projectStartDate;
    private EditText projectEndDate;


    public static FragmentManager newInstance(int sectionNumber, CurrentStatus currentStatus) {
        Log.d(TAG, "newInstance: called");
        mCurrentStatus = currentStatus;
        FragmentManager fragment = new FragmentManager();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER)-1;

        View rootView = null;

        switch(mCurrentStatus){
            case DAILYDIARY:
                rootView = inflater.inflate(R.layout.fragment_main, container, false);
                initDiaryRecyclerView(rootView);
                break;
            case FOCUSED:
                rootView = inflater.inflate(R.layout.focused_main, container, false);
                initFocusedRecyclerView(rootView);
                break;
            case PROJECTSETUP:
                switch(sectionNumber){
                    case 0:
                        rootView = inflater.inflate(R.layout.details_pane, container, false);
                        projectTitle = rootView.findViewById(R.id.project_title_main);
                        projectBody = rootView.findViewById(R.id.project_body_main);
                        projectStartDate = rootView.findViewById(R.id.project_start_date);
                        projectEndDate = rootView.findViewById(R.id.project_end_date);
                        projectTitle.setText(BaseActivity.getFocusedIdeas().get(FocusAdapter.getPosition()).getTitle());
                        projectBody.setText(BaseActivity.getFocusedIdeas().get(FocusAdapter.getPosition()).getBody());
                        projectStartDate.setText(BaseActivity.getFocusedIdeas().get(FocusAdapter.getPosition()).getProjectStartDate());
                        projectEndDate.setText(BaseActivity.getFocusedIdeas().get(FocusAdapter.getPosition()).getProjectEndDate());
                        break;
                    case 1:
                        onLeavePSA();
                        rootView = inflater.inflate(R.layout.tasks_pane, container, false);
                        initTasksPane(rootView);
                        break;
                }
                break;
        }
        return rootView;
    }


    private void initDiaryRecyclerView(View view){
        Log.d(TAG, "initDiaryRecyclerView: ");
        date = view.findViewById(R.id.list_date);
        date.setText(DateUtil.getDate(sectionNumber + 1) + "\n");
        recyclerView = view.findViewById(R.id.daily_entry);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DailyListAdapter(getActivity(),sectionNumber, view);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, this));
    }


    private void initFocusedRecyclerView(View view){
        Log.d(TAG, "initFocusedRecyclerView: called");
        recyclerView = view.findViewById(R.id.focused_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mFocusAdapter = new FocusAdapter(getActivity());
        mFocusAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mFocusAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, this));
    }

    private void initTasksPane(View view){


        Log.d(TAG, "initTasksPane: called");
        recyclerView = view.findViewById(R.id.task_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mProjectTaskAdapter = new ProjectTaskAdapter(getActivity());
        recyclerView.setAdapter(mProjectTaskAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, this));
        FloatingActionButton taskButton = view.findViewById(R.id.btn_task);
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: called " + ProjectTaskAdapter.getPosition());
                BaseActivity.getFocusedIdeas().get(ProjectTaskAdapter.getPosition()).addTask();
                mProjectTaskAdapter.notifyDataSetChanged();
            }
        });


    }

    public DailyListAdapter getAdapter() {
        return adapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch(mCurrentStatus){
            case DAILYDIARY:
                getAdapter().ideaInput(sectionNumber, position);
                break;
            case FOCUSED:
                Log.d(TAG, "onItemClick: position " + position);
                getActivity(ProjectSetupActivity.class);
                ProjectTaskAdapter.setPosition(position);
                FocusAdapter.setPosition(position);
                break;
            case PROJECTSETUP:
                mProjectTaskAdapter.taskInput(position);
                break;
        }
    }


    @Override
    public void onItemLongClick(View view, int position) {
        switch(mCurrentStatus){
            case DAILYDIARY:
                adapter.setFocused(view, position, BaseActivity.getmIdeas(sectionNumber), getActivity());
                break;
            case FOCUSED:
                break;
            case PROJECTSETUP:
                break;
        }
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void getActivity(Class<?> context){
        Intent intent = new Intent(getActivity(), context);
        intent.putExtra("SectionNumber", sectionNumber);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 0);
        getActivity().finish();

    }

    public static void setmCurrentStatus(CurrentStatus mCurrentStatus) {
        FragmentManager.mCurrentStatus = mCurrentStatus;
    }

    public void onLeavePSA() {
        if(mCurrentStatus == CurrentStatus.PROJECTSETUP && sectionNumber == 0){
            BaseActivity.getFocusedIdeas().get(FocusAdapter.getPosition()).setTitle(projectTitle.getText().toString());
            BaseActivity.getFocusedIdeas().get(FocusAdapter.getPosition()).setBody(projectBody.getText().toString());
            BaseActivity.getFocusedIdeas().get(FocusAdapter.getPosition()).setProjectStartDate(projectStartDate.getText().toString());
            BaseActivity.getFocusedIdeas().get(FocusAdapter.getPosition()).setProjectEndDate(projectEndDate.getText().toString());
        }
    }
}
