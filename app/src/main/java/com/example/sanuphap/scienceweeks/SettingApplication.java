package com.example.sanuphap.scienceweeks;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.sanuphap.scienceweeks.managers.DatabaseManager;


/**
 * Created by sAnuphap on 7/7/2558.
 */
public class SettingApplication extends Application {
    private static final String PREF_FIRST_RUN = "pref_first_run";
    @Override
    public void onCreate(){
        super.onCreate();
        // First run only
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean(PREF_FIRST_RUN, true)) {
            final DatabaseManager databaseManager = new DatabaseManager(this);

          /*  databaseManager.addGameOption("option1", "Find a item", 0, 3);
            databaseManager.addGameOption("option2", "Question", 0, 1);
            databaseManager.addGameOption("option3", "Find a item", 0, 3);
            databaseManager.addGameOption("option4", "Question", 0, 1);
            databaseManager.addGameOption("option5", "Find a item", 0,3);
            databaseManager.addGameOption("option6", "QR-code", 0, 2);
            databaseManager.addGameOption("option7", "QR-code", 0, 2);
            databaseManager.addGameOption("option8", "Question", 0, 1);
            databaseManager.addGameOption("option9", "Question", 0, 1);
            databaseManager.addGameOption("option10", "Find a item", 0, 3);
            databaseManager.addGameOption("option11", "QR-code", 0, 2);
            databaseManager.addGameOption("option12", "Find a item", 0, 3);*/





            sp.edit().putBoolean(PREF_FIRST_RUN, false).apply();
        }
    }
}
