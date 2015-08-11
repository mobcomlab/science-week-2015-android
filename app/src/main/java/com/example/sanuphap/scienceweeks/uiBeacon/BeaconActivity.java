package com.example.sanuphap.scienceweeks.uiBeacon;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
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
import com.kontakt.sdk.android.configuration.BeaconActivityCheckConfiguration;
import com.kontakt.sdk.android.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.connection.OnServiceBoundListener;
import com.kontakt.sdk.android.data.RssiCalculators;
import com.kontakt.sdk.android.device.BeaconDevice;
import com.kontakt.sdk.android.device.Region;
import com.kontakt.sdk.android.factory.AdvertisingPackage;
import com.kontakt.sdk.android.factory.Filters;
import com.kontakt.sdk.android.manager.BeaconManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BeaconActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ENABLE_BLUETOOTH = 1;
    private static final int REQUEST_CODE_CONNECT_TO_DEVICE = 2;
    public static final String EXTRA_FAILURE_MESSAGE = "extra_failure_message";

    int questId;
    String gameId ="";
    DatabaseManager databaseManager;
    Region reg;
    int num_run=1;

    String[] array_answer;

    String[] array_description;

    private BeaconManager beaconManager;
    private List<BeaconDevice> beaconDevices;
    Intent myIntent;
    TextView status,title,description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacons);
        databaseManager = new DatabaseManager(this);
        myIntent = getIntent();
        questId = myIntent.getIntExtra(QuestContents.QUEST_ID,0);

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



        beaconDevices = new ArrayList<BeaconDevice>();
        setTitle(databaseManager.getQuest(questId).getTitle());
        array_answer=databaseManager.getQuest(questId).getAnswer().split(",");

        status =(TextView) findViewById(R.id.status);
        title =(TextView)  findViewById(R.id.title);
        description =(TextView) findViewById(R.id.description);

        title.setText(databaseManager.getQuest(questId).getText());

        array_description=databaseManager.getQuest(questId).getOther().split(",");
        String getStringDescription = null;
        for (String anArray_description : array_description) {
            if (getStringDescription == null) {
                getStringDescription = anArray_description+"\n";
            } else {
                getStringDescription = getStringDescription + anArray_description+"\n";
            }
        }
        description.setText(getStringDescription);





        final ImageView circle_a = (ImageView) findViewById(R.id.circle_a);
        final ImageView circle_b = (ImageView) findViewById(R.id.circle_b);
        final ImageView circle_c = (ImageView) findViewById(R.id.circle_c);

        final Handler localHandler = new Handler();
        localHandler.postDelayed(new Runnable(){
            public void run() {
                final ImageView dice = (ImageView) findViewById(R.id.imgwifi);
                if (num_run==1){
                    dice.setImageResource(R.drawable.ani1);
                    num_run++;
                }else if (num_run==2){
                    dice.setImageResource(R.drawable.ani2);
                    num_run++;
                }else if (num_run==3){
                    dice.setImageResource(R.drawable.ani3);
                    num_run++;
                }else if (num_run==4){
                    dice.setImageResource(R.drawable.ani4);
                    num_run++;
                }else if (num_run==5){
                    num_run=1;
                }
                localHandler.postDelayed(this, 500);

            }
        },500);

        reg = new Region(UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e"),50138,2445);
        beaconManager = BeaconManager.newInstance(this);
        //beaconManager.setScanMode(BeaconManager.SCAN_MODE_BALANCED); // Works only for Android L OS version
        beaconManager.setScanMode(BeaconManager.SCAN_MODE_LOW_LATENCY);

        beaconManager.setRssiCalculator(RssiCalculators.newLimitedMeanRssiCalculator(5));

        beaconManager.setBeaconActivityCheckConfiguration(BeaconActivityCheckConfiguration.DEFAULT);

        beaconManager.setForceScanConfiguration(ForceScanConfiguration.DEFAULT);


        beaconManager.addFilter(new Filters.CustomFilter() {
            @Override
            public Boolean apply(AdvertisingPackage advertisingPackage) {

                if(advertisingPackage.getBeaconUniqueId().equals(array_answer[3])){
//
                    return true;
                }
                return false;
            }
        });


        beaconManager.registerRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<BeaconDevice> beacons) {
                BeaconActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        replaceWith(beacons);

//f7826da6-4fa2-4e98-8024-bc5b71e0893e

                        BeaconDevice getBeacon = beaconDevices.get(0);


                        status.setText(String.format("Distance: %s m", new DecimalFormat("#.#").format(getBeacon.getAccuracy())));



                        if (getBeacon.getUniqueId().equals(array_answer[3])) {
                            status.setText(String.format("Distance: %s m", new DecimalFormat("#.#").format(getBeacon.getAccuracy())));
                            if (getBeacon.getAccuracy() < 3.0) {
                                circle_a.setImageResource(R.drawable.circle_green);

                                if (getBeacon.getAccuracy() < 2.0) {
                                    circle_a.setImageResource(R.drawable.circle_green);
                                    circle_b.setImageResource(R.drawable.circle_green);
                                } else {
                                    circle_b.setImageResource(R.drawable.circle_red);
                                }
                                if (getBeacon.getAccuracy() < Double.parseDouble(array_answer[4])) {
                                    circle_a.setImageResource(R.drawable.circle_green);
                                    circle_b.setImageResource(R.drawable.circle_green);
                                    circle_c.setImageResource(R.drawable.circle_green);
                                } else {
                                    circle_c.setImageResource(R.drawable.circle_red);
                                }
                                if (getBeacon.getAccuracy() < Double.parseDouble(array_answer[4])) {
                                    toCorrect();
                                    onPause();
                                }
                            } else {
                                circle_a.setImageResource(R.drawable.circle_red);
                                circle_b.setImageResource(R.drawable.circle_red);
                                circle_c.setImageResource(R.drawable.circle_red);

                                status.setText("Searching...");
                            }


                        }



                    }
                });
            }
        });




    }

    public void replaceWith(final List<BeaconDevice> beacons) {
        this.beaconDevices.clear();
        this.beaconDevices.addAll(beacons);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(! beaconManager.isBluetoothEnabled()){
            final Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_CODE_ENABLE_BLUETOOTH);
        } else if(beaconManager.isConnected()) {
            startRanging();
        } else {
            connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(beaconManager.isConnected()) {
            beaconManager.stopRanging();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.disconnect();
        beaconManager = null;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ENABLE_BLUETOOTH) {
            if(resultCode != Activity.RESULT_OK) {
                final String bluetoothNotEnabledInfo = "Bluetooth not enabled";
                Toast.makeText(BeaconActivity.this, bluetoothNotEnabledInfo, Toast.LENGTH_LONG).show();
            }
            return;
        }  else if(requestCode == REQUEST_CODE_CONNECT_TO_DEVICE) {
            if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,
                        String.format("Beacon authentication failure: %s", data.getExtras().getString(BeaconActivity.EXTRA_FAILURE_MESSAGE, "")),
                        Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startRanging() {
        try {

            Set<Region> regions = new HashSet<>(1);
            regions.add(reg);

            beaconManager.startRanging(regions);
        } catch (RemoteException e) {
            Toast.makeText(BeaconActivity.this, "start scan Beacon", Toast.LENGTH_SHORT).show();
        }
    }

    private void connect() {
        try {
            beaconManager.connect(new OnServiceBoundListener() {
                @Override
                public void onServiceBound() throws RemoteException {
                    beaconManager.startRanging();
                }
            });
        } catch (RemoteException e) {
            Toast.makeText(BeaconActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
                Toast.makeText(BeaconActivity.this, "บันทึกเรียบร้อย", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        Button btn_again =(Button) dialog.findViewById(R.id.btn_playagain);
        btn_again.setText("ยกเลิก");
        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                connect();

                Intent intent = new Intent(BeaconActivity.this,BeaconActivity.class);
                intent.putExtra(QuestContents.QUEST_ID,questId);
                Toast.makeText(BeaconActivity.this, "คุณยังไม่ผ่านเควส", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                startActivity(intent);
                finish();



            }
        });
        dialog.show();
    }

}
