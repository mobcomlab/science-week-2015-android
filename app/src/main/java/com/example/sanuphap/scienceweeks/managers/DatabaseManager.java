package com.example.sanuphap.scienceweeks.managers;

import android.content.Context;


import com.example.sanuphap.scienceweeks.models.QuestModel;

import java.util.List;

import io.realm.Realm;

/**
 * Created by sAnuphap on 25/7/2558.
 */
public class DatabaseManager {

    private final Context context;

    public DatabaseManager(final Context context) {
        this.context = context;
    }





    public List<QuestModel> getQuests() {
        return Realm.getInstance(context)
                .where(QuestModel.class)
                .findAll();
    }

    public QuestModel getQuest(final int questId) {
        return Realm.getInstance(context)
                .where(QuestModel.class)
                .equalTo("id", questId)
                .findFirst();
    }
    public void addQuest(final int id,final int type,final String text,final String answer,final String other) {
        final Realm realm = Realm.getInstance(context);
        realm.beginTransaction();

        final QuestModel qm = realm.createObject(QuestModel.class);

        qm.setId(id);
        qm.setType(type);
        qm.setText(text);
        qm.setAnswer(answer);
        qm.setOther(other);


        realm.commitTransaction();
    }

    public void createOrUpdateQuests(int id, int type, String text, String answer, String other,String title,String icon,String color,int status) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();

        QuestModel quest = getQuest(id);

        if (quest == null) {
            quest = realm.createObject(QuestModel.class);
        }

        quest.setId(id);
        quest.setType(type);
        quest.setText(text);
        quest.setAnswer(answer);
        quest.setOther(other);
        quest.setTitle(title);
        quest.setIcon(icon);
        quest.setColor(color);
        /*quest.setStatus(status);*/

        realm.commitTransaction();

    }

    public void UpdateStatus(int id,int status){
        Realm realm = Realm.getInstance(context);
        QuestModel toEdit = realm.where(QuestModel.class)
                .equalTo("id", id).findFirst();
        realm.beginTransaction();
        toEdit.setStatus(status);
        realm.commitTransaction();


    }


}
