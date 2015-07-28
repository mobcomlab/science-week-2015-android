package com.example.sanuphap.scienceweeks.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanuphap.scienceweeks.MainActivity;
import com.example.sanuphap.scienceweeks.R;
import com.example.sanuphap.scienceweeks.models.GameOptionModel;
import com.example.sanuphap.scienceweeks.models.QuestContents;
import com.example.sanuphap.scienceweeks.models.QuestModel;
import com.example.sanuphap.scienceweeks.uiBeacon.Beacons;
import com.example.sanuphap.scienceweeks.uiQuestion.Questions;
import com.example.sanuphap.scienceweeks.uiScanner.ScannerCode;

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
            image = (ImageView) itemView.findViewById(R.id.circle_a);
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

// Bind data


        holder.sampleNameQuest.setText(objQuest.getTitle());


        if (objQuest.getStatus()==0){
            if(objQuest.getType()==3)
                holder.image.setImageResource(R.drawable.find_fail);
            else if (objQuest.getType()==1)
                holder.image.setImageResource(R.drawable.que_fail);
            else if (objQuest.getType()==2)
                holder.image.setImageResource(R.drawable.qr_fail);

        }else  if (objQuest.getStatus()==1){
            if(objQuest.getType()==3)
                holder.image.setImageResource(R.drawable.find_pass);
            else if (objQuest.getType()==1)
                holder.image.setImageResource(R.drawable.que_pass);
            else if (objQuest.getType()==2)
                holder.image.setImageResource(R.drawable.qr_pass);


        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (objQuest.getType() == 1) {
                    if (objQuest.getId() == 9) {
                        Intent intent = new Intent(context, Questions.class);
                        intent.putExtra(QuestContents.QUEST_ID, objQuest.getId());
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    }else if (objQuest.getId() == 10) {
                        Intent intent = new Intent(context, Questions.class);
                        intent.putExtra(QuestContents.QUEST_ID, objQuest.getId());
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    }else if (objQuest.getId() == 11) {
                        Intent intent = new Intent(context, Questions.class);
                        intent.putExtra(QuestContents.QUEST_ID, objQuest.getId());
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    }else if (objQuest.getId() == 12) {
                        Intent intent = new Intent(context, Questions.class);
                        intent.putExtra(QuestContents.QUEST_ID, objQuest.getId());
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    }

                } else if (objQuest.getType() == 2) {
                    if (objQuest.getId() == 1) {
                        Intent intent = new Intent(context, ScannerCode.class);
                        intent.putExtra(QuestContents.QUEST_ID, 1);
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    } else if (objQuest.getId() == 2) {
                        Intent intent = new Intent(context, ScannerCode.class);
                        intent.putExtra(QuestContents.QUEST_ID, 2);
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    } else if (objQuest.getId() == 3) {
                        Intent intent = new Intent(context, ScannerCode.class);
                        intent.putExtra(QuestContents.QUEST_ID, 3);
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    }
                } else if (objQuest.getType() == 3) {
                    if (objQuest.getId() == 4) {
                        Intent intent = new Intent(context, Beacons.class);
                        intent.putExtra(QuestContents.QUEST_ID, 4);
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    } else if (objQuest.getId() == 5) {
                        Intent intent = new Intent(context, Beacons.class);
                        intent.putExtra(QuestContents.QUEST_ID, 5);
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    } else if (objQuest.getId() == 6) {
                        Intent intent = new Intent(context, Beacons.class);
                        intent.putExtra(QuestContents.QUEST_ID, 6);
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    } else if (objQuest.getId() == 7) {
                        Intent intent = new Intent(context, Beacons.class);
                        intent.putExtra(QuestContents.QUEST_ID, 7);
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    } else if (objQuest.getId()==8) {
                        Intent intent = new Intent(context, Beacons.class);
                        intent.putExtra(QuestContents.QUEST_ID, 8);
                        ((Activity) context).startActivityForResult(intent, MainActivity.rg_update);
                    }
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }
}
