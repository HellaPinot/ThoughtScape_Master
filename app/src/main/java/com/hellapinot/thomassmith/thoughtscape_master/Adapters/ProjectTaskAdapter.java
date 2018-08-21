package com.hellapinot.thomassmith.thoughtscape_master.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hellapinot.thomassmith.thoughtscape_master.Activities.BaseActivity;
import com.hellapinot.thomassmith.thoughtscape_master.DateUtil;
import com.hellapinot.thomassmith.thoughtscape_master.R;
import com.hellapinot.thomassmith.thoughtscape_master.DataStructs.TaskStruct;

import java.util.ArrayList;

public class ProjectTaskAdapter extends RecyclerView.Adapter<ProjectTaskAdapter.ViewHolder>{

    private Context mContext;
    private static int position;
    private static final String TAG = "ProjectTaskAdapter";
    private Dialog entryInput;
    private EditText taskTitle;
    private EditText taskStartDate;
    private EditText taskEndDate;
    private EditText taskBody;
    private Button updateTask;
    private final ArrayList<TaskStruct> aList = BaseActivity.getFocusedIdeas().get(this.position).getTaskList();

    public ProjectTaskAdapter(Context context) {
        mContext = context;
        entryInput = new Dialog(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_entry, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.taskTitle.setText(aList.get(position).getTaskTitle());
        holder.taskEndDate.setText(aList.get(position).getTaskEndDate());
        holder.taskBody.setText(aList.get(position).getTaskBody());
        holder.taskComplete.setChecked(aList.get(position).isTaskComplete());
        holder.taskProgress.setProgress(calculateTaskProgress(position));
        holder.taskComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                aList.get(position).setTaskComplete(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + BaseActivity.getFocusedIdeas().get(position).getTaskList().size());
        return BaseActivity.getFocusedIdeas().get(position).getTaskList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle;
        TextView taskBody;
        TextView taskEndDate;
        ProgressBar taskProgress;
        CheckBox taskComplete;


        public ViewHolder(View itemView) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.task_title);
            taskBody = itemView.findViewById(R.id.task_body);
            taskEndDate = itemView.findViewById(R.id.task_end_date);
            taskProgress = itemView.findViewById(R.id.task_progress);
            taskComplete = itemView.findViewById(R.id.task_complete);
        }
    }


    public static int getPosition() {
        return position;
    }

    public static void setPosition(int position) {
        ProjectTaskAdapter.position = position;
    }

    public void taskInput(final int position){

        entryInput.setContentView(R.layout.task_input);
        taskTitle = entryInput.findViewById(R.id.task_title);
        taskStartDate = entryInput.findViewById(R.id.task_start_date);
        taskEndDate = entryInput.findViewById(R.id.task_end_date);
        taskBody = entryInput.findViewById(R.id.task_body);
        updateTask = entryInput.findViewById(R.id.update_task);
        entryInput.show();

        if(aList.get(position).getTaskTitle() != ""){
            taskTitle.setText(aList.get(position).getTaskTitle());
            taskStartDate.setText(aList.get(position).getTaskStartDate());
            taskEndDate.setText(aList.get(position).getTaskEndDate());
            taskBody.setText(aList.get(position).getTaskBody());
        }

        updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aList.get(position).setTaskTitle(taskTitle.getText().toString());
                aList.get(position).setTaskStartDate(taskStartDate.getText().toString());
                aList.get(position).setTaskEndDate(taskEndDate.getText().toString());
                aList.get(position).setTaskBody(taskBody.getText().toString());
                aList.get(position).setTaskProgress(calculateTaskProgress(position));

                notifyDataSetChanged();
                entryInput.dismiss();
            }
        });

    }

    private int calculateTaskProgress(int position){
        try {
            float totalDays = (float) DateUtil.daysBetween(aList.get(position).getTaskStartDate(), aList.get(position).getTaskEndDate());
            float daysLeft = (float) DateUtil.daysFromToday(aList.get(position).getTaskEndDate());
            float calculation = 100 - ((daysLeft / totalDays) * 100);
            Log.d(TAG, "calculateProjectProgress: called, returned days left: " + Float.toString(daysLeft) + " totalDays: " + Float.toString(totalDays) + " calculation: " + Float.toString(calculation));
            return (int) calculation;
        }catch(NullPointerException e){
            return 0;
        }
    }
}
