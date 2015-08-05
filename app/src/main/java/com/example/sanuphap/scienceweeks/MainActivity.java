package com.example.sanuphap.scienceweeks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sanuphap.scienceweeks.adapter.QuestAdapter;
import com.example.sanuphap.scienceweeks.managers.DatabaseManager;
import com.example.sanuphap.scienceweeks.managers.WebServiceCallbackListener;
import com.example.sanuphap.scienceweeks.managers.WebServiceManager;
import com.example.sanuphap.scienceweeks.models.QuestContents;
import com.example.sanuphap.scienceweeks.models.QuestModel;

import java.util.List;


public class MainActivity extends AppCompatActivity implements WebServiceCallbackListener {
    public final static int rg_update = 1;
    private RecyclerView recyclerView;

    Context context ;
//    Hello eiei test sisissi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        // Get data
        List<QuestModel> data = new DatabaseManager(this).getQuests();

        // Setup RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.list_game);
        recyclerView.setAdapter(new QuestAdapter(this, data));
        //set grid view
        recyclerView.setHasFixedSize(true);
        GridLayoutManager glm=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(glm);

        new WebServiceManager(this,this).requestSoilSamples();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_start_new_game) {
            DatabaseManager databaseManager = new DatabaseManager(this);
            databaseManager.UpdateStatus(QuestContents.OPTION_ONE,0);
            databaseManager.UpdateStatus(QuestContents.OPTION_TWO,0);
            databaseManager.UpdateStatus(QuestContents.OPTION_THREE,0);
            databaseManager.UpdateStatus(QuestContents.OPTION_FOUR,0);
            databaseManager.UpdateStatus(QuestContents.OPTION_FIVE,0);
            databaseManager.UpdateStatus(QuestContents.OPTION_SIX,0);
            databaseManager.UpdateStatus(QuestContents.OPTION_SEVEN,0);
            databaseManager.UpdateStatus(QuestContents.OPTION_EIGHT,0);
            databaseManager.UpdateStatus(QuestContents.OPTION_NINE,0);
            databaseManager.UpdateStatus(QuestContents.OPTION_TEN,0);
            databaseManager.UpdateStatus(QuestContents.OPTION_ELEVEN,0);
            databaseManager.UpdateStatus(QuestContents.OPTION_TWELVE,0);

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            ( MainActivity.this).startActivityForResult(intent, MainActivity.rg_update);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshData() {
        // Get data
        final List<QuestModel> data = new DatabaseManager(this).getQuests();
        final RecyclerView samples = (RecyclerView) findViewById(R.id.list_game);
        samples.setAdapter(new QuestAdapter(this, data));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (requestCode == rg_update) {
            refreshData();

        }

    }

    @Override
    public void onWebServiceCallback() {

        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onWebServiceFailed() {

    }
}
