package com.hellapinot.thomassmith.thoughtscape_master.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.hellapinot.thomassmith.thoughtscape_master.activities.BaseActivity;
import com.hellapinot.thomassmith.thoughtscape_master.DataStructs.IdeaStruct;
import com.hellapinot.thomassmith.thoughtscape_master.R;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;


public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.ViewHolder>{


    private static final String TAG = "DailyListAdapter";

    private Context mContext;
    private int sectionNumber;
    private Dialog entryInput;
    private EditText entryTitle;
    private EditText entryBody;
    private Button updateIdea;
    private Button deleteIdea;


    public DailyListAdapter( Context context, int sectionNumber, View view) {
        this.mContext = context;
        this.sectionNumber = sectionNumber;
        entryInput = new Dialog(context);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(mContext).inflate(R.layout.entry_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.entryHeader.setText(BaseActivity.getmIdeas(sectionNumber).get(position).getTitle());
        if(BaseActivity.getEntry(sectionNumber,position).isFocused()){
            holder.circleImage.setImageResource(R.drawable.entry_lightbulb);
        }else{
            holder.circleImage.setImageResource(R.drawable.entry_brain);
        }
    }


    @Override
    public int getItemCount(){
        return BaseActivity.getmIdeas(sectionNumber).size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImage;
        TextView entryHeader;
        ConstraintLayout mLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImage = itemView.findViewById(R.id.circle_image);
            entryHeader = itemView.findViewById(R.id.entry_header);
            mLayout = itemView.findViewById(R.id.entry_layout);
        }
    }


    public void setFocused(View view, int position, ArrayList<IdeaStruct> focused, Context context){
        ViewHolder holder = new ViewHolder(view);
        if(focused.get(position).isFocused()){
            holder.circleImage.setImageResource(R.drawable.entry_brain);
            focused.get(position).setFocused(false);
            Toast.makeText(context,"Removed from focus List", Toast.LENGTH_SHORT).show();
        }else{
            holder.circleImage.setImageResource(R.drawable.entry_lightbulb);
            focused.get(position).setFocused(true);
            Toast.makeText(context,"Added to focus list", Toast.LENGTH_SHORT).show();
        }
    }


    public int getSectionNumber() {
        return sectionNumber;
    }

    public void ideaInput(final int sectionNumber, final int position){
        entryInput.setContentView(R.layout.entry_input);
        entryTitle = entryInput.findViewById(R.id.entry_title);
        entryBody = entryInput.findViewById(R.id.entry_body);
        updateIdea = entryInput.findViewById(R.id.update_idea);
        deleteIdea = entryInput.findViewById(R.id.delete_idea);
        entryInput.show();

        if(BaseActivity.getEntry(sectionNumber, position).getTitle() != ""){
            entryTitle.setText(BaseActivity.getEntry(sectionNumber, position).getTitle());
            entryBody.setText(BaseActivity.getEntry(sectionNumber, position).getBody());
        }

        updateIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.updateEntry(sectionNumber, position, entryTitle.getText().toString(), entryBody.getText().toString());
                notifyDataSetChanged();
                entryInput.dismiss();
            }
        });

        deleteIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.deleteEntry(sectionNumber, position, mContext);
                notifyDataSetChanged();
                entryInput.dismiss();
            }
        });
    }
}

