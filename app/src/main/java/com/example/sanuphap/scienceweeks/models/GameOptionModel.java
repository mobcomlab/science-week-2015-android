package com.example.sanuphap.scienceweeks.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sAnuphap on 25/7/2558.
 */
public class GameOptionModel extends RealmObject {

    @PrimaryKey
    private String id;

    private String text;
    private int type;
    private int status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
