package com.example.sanuphap.scienceweeks.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanuphap.scienceweeks.MainActivity;
import com.example.sanuphap.scienceweeks.R;

import com.example.sanuphap.scienceweeks.models.QuestContents;
import com.example.sanuphap.scienceweeks.models.QuestModel;
import com.example.sanuphap.scienceweeks.uiBeacon.BeaconActivity;
import com.example.sanuphap.scienceweeks.uiQuestion.QuestionActivity;
import com.example.sanuphap.scienceweeks.uiScanner.ScannerActivity;

import java.util.List;

/**
 * Created by sAnuphap on 25/7/2558.
 */
public class QuestAdapter extends RecyclerView.Adapter<QuestAdapter.ViewHolder>{
    private final Context context;
    private final List<QuestModel> data;

    public QuestAdapter(Context context, List<QuestModel> data) {
        this.context = context;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView sampleNameQuest;
        public final ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            sampleNameQuest = (TextView) itemView.findViewById(R.id.sample_name_Quest);
            image = (ImageView) itemView.findViewById(R.id.img_circle);
        }
    }
    @Override
    public QuestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.activity_row_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final QuestModel objQuest = data.get(position);

        holder.sampleNameQuest.setText(objQuest.getTitle());

        // Change background colour
        GradientDrawable drawable = (GradientDrawable)holder.image.getBackground();
        drawable.setColor(Color.parseColor(objQuest.getColor()));

        // Set completed graphic
        boolean completed = objQuest.getStatus() > 0;
        if (objQuest.getIcon().equals("math")) {
            holder.image.setImageResource(completed ? R.drawable.math_clear : R.drawable.math);
        } else if (objQuest.getIcon().equals("quest")) {
            holder.image.setImageResource(completed ? R.drawable.qa_clear : R.drawable.qa);
        } else if (objQuest.getIcon().equals("qr")) {
            holder.image.setImageResource(completed ? R.drawable.qr_clear : R.drawable.qr);
        } else if (objQuest.getIcon().equals("maze")) {
            holder.image.setImageResource(completed ? R.drawable.maze_clear : R.drawable.maze);
        } else if (objQuest.getIcon().equals("mobcom")) {
            holder.image.setImageResource(completed ? R.drawable.mcl_clear : R.drawable.mcl);
        } else if (objQuest.getIcon().equals("beacon")) {
            holder.image.setImageResource(completed ? R.drawable.find_clear : R.drawable.find);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (objQuest.getType() == 1) {
                    Intent intent = new Intent(context, QuestionActivity.class);
                    intent.putExtra(QuestContents.QUEST_ID, objQuest.getId());
                    ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);


                } else if (objQuest.getType() == 2) {

                    Intent intent = new Intent(context, ScannerActivity.class);
                    intent.putExtra(QuestContents.QUEST_ID, objQuest.getId());
                    ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);

                } else if (objQuest.getType() == 3) {

                    Intent intent = new Intent(context, BeaconActivity.class);
                    intent.putExtra(QuestContents.QUEST_ID, objQuest.getId());
                    ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);

                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }
}


/*test test
if(objQuest.getIcon().equals("math")){
        holder.image.setImageResource(R.drawable.math);
        }else if(objQuest.getIcon().equals("quest")){
        holder.image.setImageResource(R.drawable.qa);
        }else if(objQuest.getIcon().equals("qr")){
        holder.image.setImageResource(R.drawable.qr);
        }else if(objQuest.getIcon().equals("maze")){
        holder.image.setImageResource(R.drawable.maze);
        }else if(objQuest.getIcon().equals("mobcom")){
        holder.image.setImageResource(R.drawable.math);
        }else if(objQuest.getIcon().equals("beacon")){
        holder.image.setImageResource(R.drawable.find);
        }*/
