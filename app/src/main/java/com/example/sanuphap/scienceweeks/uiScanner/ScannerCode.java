package com.example.sanuphap.scienceweeks.uiScanner;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.sanuphap.scienceweeks.R;
import com.example.sanuphap.scienceweeks.managers.DatabaseManager;
import com.example.sanuphap.scienceweeks.models.QuestContents;

import eu.livotov.zxscan.ScannerFragment;
import eu.livotov.zxscan.ScannerView;

public class ScannerCode extends AppCompatActivity implements ScannerView.ScannerViewEventListener {

    ScannerView scannerView;
    ScannerFragment scannerFragment;
    Intent intent;
    int questId;
    String codeId;
    String string_des;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_code);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.icon_scanner);

        final DatabaseManager databaseManager = new DatabaseManager(this);

        scannerFragment = new ScannerFragment();
        scannerFragment.setScannerViewEventListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.scanner_fragment, scannerFragment)
                .commit();

        intent = getIntent();

        questId =intent.getIntExtra(QuestContents.QUEST_ID, 0);

        setTitle(databaseManager.getQuest(questId).getTitle());

        String descriptions = databaseManager.getQuest(questId).getOther();
        String[] descriptionArray = descriptions.split(",");

        String titles = databaseManager.getQuest(questId).getText();

        codeId = databaseManager.getQuest(questId).getAnswer();

        TextView text_description = (TextView) findViewById(R.id.description_content);
        for (String aDescriptionArray : descriptionArray) {
            if (string_des==null) {
                string_des =  aDescriptionArray + "\n";
            }else{
                string_des = string_des+aDescriptionArray + "\n";

            }
        }

        text_description.setText(string_des);
        TextView text_title = (TextView) findViewById(R.id.title_content);
        text_title.setText(titles);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner_code, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScannerReady() {
        scannerView = scannerFragment.getScanner();
    }

    @Override
    public void onScannerFailure(int i) {

    }

    @Override
    public boolean onCodeScanned(String data) {

        ScannerView scanner = scannerFragment.getScanner();
        scanner.stopScanner();
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);

        if(data.equals(codeId)){
            intent = new Intent(this,CorrectScannerCode.class);
            intent.putExtra(QuestContents.QUEST_ID,questId);
            startActivity(intent);
            finish();
        }else {
            scanner.startScanner();
        }



        return false;
    }
}
