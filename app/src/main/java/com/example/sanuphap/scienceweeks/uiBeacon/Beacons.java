package com.example.sanuphap.scienceweeks.uiBeacon;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Beacons extends AppCompatActivity {
    private static final int REQUEST_CODE_ENABLE_BLUETOOTH = 1;
    private static final int REQUEST_CODE_CONNECT_TO_DEVICE = 2;
    public static final String EXTRA_FAILURE_MESSAGE = "extra_failure_message";

    int questId;
    String gameId ="";

    Region reg;

    private BeaconManager beaconManager;
    private List<BeaconDevice> beaconDevices;
    Intent myIntent;
    TextView status,title,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacons);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);


        beaconDevices = new ArrayList<BeaconDevice>();
        final DatabaseManager databaseManager = new DatabaseManager(this);



        myIntent = getIntent();

        questId = myIntent.getIntExtra(QuestContents.QUEST_ID,0);

        setTitle(databaseManager.getQuest(questId).getTitle() +" ("+databaseManager.getQuest(questId).getAnswer()+")");

        status =(TextView) findViewById(R.id.status);
        title =(TextView)  findViewById(R.id.title);
        description =(TextView) findViewById(R.id.description);

        final ImageView circle_a = (ImageView) findViewById(R.id.circle_a);
        final ImageView circle_b = (ImageView) findViewById(R.id.circle_b);
        final ImageView circle_c = (ImageView) findViewById(R.id.circle_c);

        title.setText(databaseManager.getQuest(questId).getText());
        description.setText(databaseManager.getQuest(questId).getOther());

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

                if(advertisingPackage.getBeaconUniqueId().equals(databaseManager.getQuest(questId).getAnswer())){
//
                    return true;
                }
                return false;
            }
        });


        beaconManager.registerRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<BeaconDevice> beacons) {
                Beacons.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        replaceWith(beacons);

//f7826da6-4fa2-4e98-8024-bc5b71e0893e

                        BeaconDevice getBeacon = beaconDevices.get(0);


                        status.setText(String.format("Distance: %s m", new DecimalFormat("#.#").format(getBeacon.getAccuracy())));


                        if (getBeacon.getUniqueId().equals(databaseManager.getQuest(questId).getAnswer())) {
                            status.setText(String.format("Distance: %s m", new DecimalFormat("#.#").format(getBeacon.getAccuracy())));
                            if (getBeacon.getAccuracy() < 3.0) {
                                circle_a.setImageResource(R.drawable.circle_green);

                                if (getBeacon.getAccuracy() < 2.0) {
                                    circle_a.setImageResource(R.drawable.circle_green);
                                    circle_b.setImageResource(R.drawable.circle_green);
                                } else {
                                    circle_b.setImageResource(R.drawable.circle_red);
                                }
                                if (getBeacon.getAccuracy() < 1.0) {
                                    circle_a.setImageResource(R.drawable.circle_green);
                                    circle_b.setImageResource(R.drawable.circle_green);
                                    circle_c.setImageResource(R.drawable.circle_green);
                                } else {
                                    circle_c.setImageResource(R.drawable.circle_red);
                                }
                                if (getBeacon.getAccuracy() < 0.2) {
                                    myIntent = new Intent(Beacons.this, FoundBeacon.class);
                                    myIntent.putExtra(QuestContents.QUEST_ID, questId);
                                    startActivity(myIntent);
                                    finish();
                                }
                            } else {
                                circle_a.setImageResource(R.drawable.circle_red);
                                circle_b.setImageResource(R.drawable.circle_red);
                                circle_c.setImageResource(R.drawable.circle_red);
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
                Toast.makeText(Beacons.this, bluetoothNotEnabledInfo, Toast.LENGTH_LONG).show();
            }
            return;
        }  else if(requestCode == REQUEST_CODE_CONNECT_TO_DEVICE) {
            if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,
                        String.format("Beacon authentication failure: %s", data.getExtras().getString(Beacons.EXTRA_FAILURE_MESSAGE, "")),
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
            Toast.makeText(Beacons.this, "start scan Beacon", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(Beacons.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
