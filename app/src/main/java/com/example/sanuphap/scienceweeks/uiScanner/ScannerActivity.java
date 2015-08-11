package com.example.sanuphap.scienceweeks.uiScanner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sanuphap.scienceweeks.R;
import com.example.sanuphap.scienceweeks.managers.DatabaseManager;
import com.example.sanuphap.scienceweeks.models.QuestContents;

import eu.livotov.zxscan.ScannerFragment;
import eu.livotov.zxscan.ScannerView;

public class ScannerActivity extends AppCompatActivity implements ScannerView.ScannerViewEventListener {

    ScannerView scannerView;
    ScannerFragment scannerFragment;
    Intent intent;
    int questId;
    String codeId;
    String string_des;

    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_code);

        databaseManager = new DatabaseManager(this);
        intent = getIntent();
        questId = intent.getIntExtra(QuestContents.QUEST_ID,0);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        /*switch (databaseManager.getQuest(questId).getIcon()) {
            case "quest":
                actionBar.setIcon(R.mipmap.icon_question);
                break;
            case "qr":
                actionBar.setIcon(R.mipmap.icon_scanner);
                break;
            case "beacon":
                actionBar.setIcon(R.mipmap.icon_beacon);
                break;
            case "math":
                actionBar.setIcon(R.mipmap.icon_math);
                break;
            case "maze":
                actionBar.setIcon(R.mipmap.icon_maze);
                break;
            case "mobcom":
                actionBar.setIcon(R.mipmap.icon_mcl);
                break;
        }*/



        scannerFragment = new ScannerFragment();
        scannerFragment.setScannerViewEventListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.scanner_fragment, scannerFragment)
                .commit();



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
        TextView text_title = (TextView) findViewById(R.id.tile_scanner);
        text_title.setText(titles);




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

        final ScannerView scanner = scannerFragment.getScanner();
        scanner.stopScanner();
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);

        if(data.equals(codeId)){

            toCorrect();
        }else {
            Toast.makeText(ScannerActivity.this, "รหัสไม่ตรงกับเควสนี้", Toast.LENGTH_LONG).show();
            scanner.startScanner();
        }



        return false;
    }
    public void toCorrect(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        switch (databaseManager.getQuest(questId).getIcon()) {
            case "quest":
                image.setImageResource(R.drawable.qa_clear);
                break;
            case "qr":
                image.setImageResource(R.drawable.qr_clear);
                break;
            case "beacon":
                image.setImageResource(R.drawable.find_clear);
                break;
            case "math":
                image.setImageResource(R.drawable.math_clear);
                break;
            case "maze":
                image.setImageResource(R.drawable.maze_clear);
                break;
            case "mobcom":
                image.setImageResource(R.drawable.mcl_clear);
                break;
        }

        TextView text_status = (TextView) dialog.findViewById(R.id.status_question);
        text_status.setText("คุณได้ผ่านเควสนี้แล้ว");

        Button btn_save = (Button) dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseManager.UpdateStatus(questId, 1);
                Toast.makeText(ScannerActivity.this, "บันทึกเรียบร้อย", Toast.LENGTH_LONG).show();


                finish();

            }
        });

        Button btn_again =(Button) dialog.findViewById(R.id.btn_playagain);
        btn_again.setText("ยกเลิก");
        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScannerView scanner = scannerFragment.getScanner();

                dialog.dismiss();
                Toast.makeText(ScannerActivity.this, "คุณยังไม่ผ่านเควส", Toast.LENGTH_LONG).show();
                scanner.startScanner();

            }
        });
        dialog.show();
    }
}
