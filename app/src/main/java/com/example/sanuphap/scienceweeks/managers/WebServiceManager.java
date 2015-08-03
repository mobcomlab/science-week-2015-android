package com.example.sanuphap.scienceweeks.managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by sAnuphap on 24/7/2558.
 */
public class WebServiceManager {
    private final static String QUEST_URL = "http://192.168.215.1:8888/api/quests";

    final Context context;
    final WebServiceCallbackListener listener;


    public WebServiceManager(Context context, WebServiceCallbackListener listener) {
        super();
        this.context = context;
        this.listener = listener;
    }
    public void requestSoilSamples() {

        String url = QUEST_URL;

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("WebServiceManager", "Response: " + response.toString());

                        DatabaseManager databaseManager = new DatabaseManager(context);

                        try {

                            JSONArray questsRaw = response.getJSONArray("data");
                            for (int i = 0; i < questsRaw.length(); i++) {

                                JSONObject questRaw = questsRaw.getJSONObject(i);
                                int id = questRaw.getInt("id");
                                int type = questRaw.getInt("type");
                                String text = questRaw.getString("text");
                                String answer = questRaw.getString("answer");
                                String other = questRaw.getString("other");
                                String title = questRaw.getString("title");
                                String icon = questRaw.getString("icon");
                                String color = questRaw.getString("color");
                                int status = questRaw.getInt("status");

//                                databaseManager.addQuest(id, type, text, answer, other);
                                databaseManager.createOrUpdateQuests(id, type, text, answer, other,title,icon,color,status);

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                        listener.onWebServiceCallback();

                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }


/*
    private void handleLayerResponse(JSONObject response) {
        DatabaseManager databaseManager = new DatabaseManager(context);

        ArrayList<QuestModel> quests = new ArrayList<>();

        try {
            JSONArray questsRaw = response.getJSONArray("data");
            for (int i = 0; i < questsRaw.length(); i++) {

                JSONObject questRaw = questsRaw.getJSONObject(i);
                int id = questRaw.getInt("id");
                String type = questRaw.getString("type");
                String text = questRaw.getString("text");
                String answer = questRaw.getString("answer");
                String other = questRaw.getString("other");




                databaseManager.addQuest(id,type,text,answer,other);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }*/


}
