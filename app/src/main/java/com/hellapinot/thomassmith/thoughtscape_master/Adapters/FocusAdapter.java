package com.hellapinot.thomassmith.thoughtscape_master.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hellapinot.thomassmith.thoughtscape_master.Activities.BaseActivity;
import com.hellapinot.thomassmith.thoughtscape_master.DateUtil;
import com.hellapinot.thomassmith.thoughtscape_master.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FocusAdapter extends RecyclerView.Adapter<FocusAdapter.ViewHolder>{

    private static final String TAG = "FocusAdapter";
    private Context mContext;
    private static int position;



    public FocusAdapter( Context context) {
        Log.d(TAG, "FocusAdapter: called");
        this.mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.focused_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.focusedTitle.setText(BaseActivity.getFocusedIdeas().get(position).getTitle());
        holder.fromDate.setText(BaseActivity.getFocusedIdeas().get(position).getProjectStartDate());
        holder.toDate.setText(BaseActivity.getFocusedIdeas().get(position).getProjectEndDate());
        holder.mProgressBar.setProgress(calculateProjectProgress(position));

        for(int x = 0; x < BaseActivity.getFocusedIdeas().get(position).getTaskList().size(); x++){
            if(!BaseActivity.getFocusedIdeas().get(position).getTaskList().get(x).isTaskComplete()){
                holder.focusedBody.setText(BaseActivity.getFocusedIdeas().get(position).getTaskList().get(x).getTaskTitle());
                holder.mTaskProgressBar.setProgress(BaseActivity.getFocusedIdeas().get(position).getTaskList().get(x).getTaskProgress());
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return BaseActivity.getFocusedIdeas().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView focusedTitle;
        TextView focusedBody;
        TextView fromDate;
        TextView toDate;
        ConstraintLayout mLayout;
        ProgressBar mProgressBar;
        ProgressBar mTaskProgressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            focusedTitle = itemView.findViewById(R.id.focused_title);
            focusedBody = itemView.findViewById(R.id.focused_next_task);
            fromDate = itemView.findViewById(R.id.from_date);
            toDate = itemView.findViewById(R.id.to_date);
            mLayout = itemView.findViewById(R.id.focused_item_parent);
            mProgressBar = itemView.findViewById(R.id.focused_progress);
            mTaskProgressBar = itemView.findViewById(R.id.focused_task_progress);
        }
    }

    public static int getPosition() {
        return position;
    }

    public static void setPosition(int position) {
        FocusAdapter.position = position;
    }

    private int calculateProjectProgress(int position){
        try {
            float totalDays = (float) DateUtil.daysBetween(BaseActivity.getFocusedIdeas().get(position).getProjectStartDate(), BaseActivity.getFocusedIdeas().get(position).getProjectEndDate());
            float daysLeft = (float) DateUtil.daysFromToday(BaseActivity.getFocusedIdeas().get(position).getProjectEndDate());
            float calculation = 100 - ((daysLeft / totalDays) * 100);
            Log.d(TAG, "calculateProjectProgress: called, returned days left: " + Float.toString(daysLeft) + " totalDays: " + Float.toString(totalDays) + " calculation: " + Float.toString(calculation));
            return (int) calculation;
        }catch(NullPointerException e){
            return 0;
        }
    }





}
