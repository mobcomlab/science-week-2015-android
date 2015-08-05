package com.example.sanuphap.scienceweeks.uiQuestion;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sanuphap.scienceweeks.R;
import com.example.sanuphap.scienceweeks.managers.DatabaseManager;
import com.example.sanuphap.scienceweeks.models.QuestContents;

import java.util.Random;


public class Questions extends AppCompatActivity {
    TextView display_questions;
    TextView display_choicesA;
    TextView display_choicesB;
    TextView display_choicesC;
    TextView display_choicesD;
    TextView number;


    int questId = 0;
    String[] questions;
    String[] answers;
    String[] allChoices;
    String[] choices;




    int score;
    int number_order = 1;

    int forEnd=0;

    Random random;
    int num_random=0;
    int order=0;

    DatabaseManager databaseManager;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        databaseManager = new DatabaseManager(this);

        intent = getIntent();
        questId = intent.getIntExtra(QuestContents.QUEST_ID, 0);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.icon_question);

        setTitle(databaseManager.getQuest(questId).getTitle());



        random = new Random();


        questions = databaseManager.getQuest(questId).getText().split(":");
        answers = databaseManager.getQuest(questId).getAnswer().split(":");
        allChoices = databaseManager.getQuest(questId).getOther().split(":");


        forEnd = questions.length+1;


        display_questions = (TextView) findViewById(R.id.question);
        display_choicesA = (TextView) findViewById(R.id.choiceA);
        display_choicesB = (TextView) findViewById(R.id.choiceB);
        display_choicesC = (TextView) findViewById(R.id.choiceC);
        display_choicesD = (TextView) findViewById(R.id.choiceD);
        number =(TextView) findViewById(R.id.nuber_order);

        num_random = random.nextInt(4);



        number.setText(number_order + " / " + questions.length);
        display_questions.setText(questions[order]);
        String Choice = allChoices[order];
        choices = Choice.split(",");

        if (num_random==0){
            display_choicesA.setText(answers[order]);
            display_choicesB.setText(choices[0]);
            display_choicesC.setText(choices[1]);
            display_choicesD.setText(choices[2]);

        }else  if (num_random==1){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(answers[order]);
            display_choicesC.setText(choices[1]);
            display_choicesD.setText(choices[2]);

        }else if (num_random==2){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(choices[1]);
            display_choicesC.setText(answers[order]);
            display_choicesD.setText(choices[2]);

        }else if (num_random==3){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(choices[1]);
            display_choicesC.setText(choices[2]);
            display_choicesD.setText(answers[order]);

        }

        display_choicesA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num_random==0){
                    order++;
                    score++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }else {
                    order++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }
            }
        });

        display_choicesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num_random==1){
                    order++;
                    score++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }else {
                    order++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }
            }
        });

        display_choicesC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num_random==2){
                    order++;
                    score++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }else {
                    order++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }
            }
        });

        display_choicesD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num_random==3){
                    order++;
                    score++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }else {
                    order++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }
            }
        });







    }

    public void readQuestions(){
        num_random = random.nextInt(4);
        number.setText(number_order+" / "+questions.length);
        display_questions.setText(questions[order]);
        String Choice = allChoices[order];
        choices = Choice.split(",");

        if (num_random==0){
            display_choicesA.setText(answers[order]);
            display_choicesB.setText(choices[0]);
            display_choicesC.setText(choices[1]);
            display_choicesD.setText(choices[2]);

        }else  if (num_random==1){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(answers[order]);
            display_choicesC.setText(choices[1]);
            display_choicesD.setText(choices[2]);

        }else if (num_random==2){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(choices[1]);
            display_choicesC.setText(answers[order]);
            display_choicesD.setText(choices[2]);

        }else if (num_random==3){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(choices[1]);
            display_choicesC.setText(choices[2]);
            display_choicesD.setText(answers[order]);

        }

        display_choicesA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num_random==0){
                    order++;
                    score++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }else {
                    order++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }
            }
        });

        display_choicesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num_random==1){
                    order++;
                    score++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }else {
                    order++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }
            }
        });

        display_choicesC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num_random==2){
                    order++;
                    score++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }else {
                    order++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }
            }
        });

        display_choicesD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num_random==3){
                    order++;
                    score++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }else {
                    order++;
                    number_order++;
                    if (number_order<forEnd){
                        readQuestions();
                    }else {toFinish();}
                }
            }
        });

    }

    public void toFinish(){
        intent = new Intent(this,ScoreQuestion.class);
        intent.putExtra("score",score);
        intent.putExtra(QuestContents.QUEST_ID,questId);
        intent.putExtra(QuestContents.FOR_END,forEnd-1);

        startActivity(intent);

        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questions, menu);
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
