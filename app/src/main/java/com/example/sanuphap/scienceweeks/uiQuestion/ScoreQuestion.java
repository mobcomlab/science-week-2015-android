package com.example.sanuphap.scienceweeks.uiQuestion;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sanuphap.scienceweeks.R;
import com.example.sanuphap.scienceweeks.managers.DatabaseManager;
import com.example.sanuphap.scienceweeks.models.QuestContents;

import java.text.DecimalFormat;

public class ScoreQuestion extends AppCompatActivity {
    Intent intent;

    int score;

    int questId;
    int forEnd;
    double sum;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_question);

        intent = getIntent();
        score = intent.getIntExtra("score", 0);

        forEnd = intent.getIntExtra(QuestContents.FOR_END, 0);
        questId = intent.getIntExtra(QuestContents.QUEST_ID, 0);

        sum = (Double.parseDouble(String.valueOf(score))/Double.parseDouble(String.valueOf(forEnd))*100);


        TextView text_score = (TextView) findViewById(R.id.score_user);
        text_score.setText(new DecimalFormat("#.#").format(sum)+"%");

        if (sum>=50){
            TextView text_status = (TextView) findViewById(R.id.text_status);
            text_status.setText(">> PASS <<");
            text_status.setTextColor(Color.GREEN);
        }else {
            TextView text_status = (TextView) findViewById(R.id.text_status);
            text_status.setText(">> FAIL <<");
            text_status.setTextColor(Color.RED);
        }

        Button btn_play = (Button) findViewById(R.id.btn_again);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ScoreQuestion.this,Questions.class);
                intent.putExtra(QuestContents.QUEST_ID,questId);
                startActivity(intent);

                finish();
            }
        });

        Button btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseManager = new DatabaseManager(ScoreQuestion.this);
                if (sum>=50) {
                    databaseManager.UpdateStatus(questId, 1);
                }
                else {databaseManager.UpdateStatus(questId,0);}
                finish();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_question, menu);
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
}
