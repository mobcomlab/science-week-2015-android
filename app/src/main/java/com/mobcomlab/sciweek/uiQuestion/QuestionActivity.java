package com.mobcomlab.sciweek.uiQuestion;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobcomlab.sciweek.R;
import com.mobcomlab.sciweek.managers.DatabaseManager;
import com.mobcomlab.sciweek.models.QuestContents;

import java.util.Random;


public class QuestionActivity extends AppCompatActivity {
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
    int random_choice =0;


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

        random_choice= random.nextInt(4);




        number.setText(number_order + " / " + questions.length);
        display_questions.setText(questions[order]);
        String Choice = allChoices[order];
        choices = Choice.split(",");

        if (random_choice ==0){
            display_choicesA.setText(answers[order]);
            display_choicesB.setText(choices[0]);
            display_choicesC.setText(choices[1]);
            display_choicesD.setText(choices[2]);

        }else  if (random_choice ==1){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(answers[order]);
            display_choicesC.setText(choices[1]);
            display_choicesD.setText(choices[2]);

        }else if (random_choice ==2){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(choices[1]);
            display_choicesC.setText(answers[order]);
            display_choicesD.setText(choices[2]);

        }else if (random_choice ==3){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(choices[1]);
            display_choicesC.setText(choices[2]);
            display_choicesD.setText(answers[order]);

        }

        display_choicesA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random_choice ==0){
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
                if (random_choice ==1){
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
                if (random_choice ==2){
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
                if (random_choice ==3){
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
        random_choice = random.nextInt(4);
        number.setText(number_order+" / "+questions.length);
        display_questions.setText(questions[order]);
        String Choice = allChoices[order];
        choices = Choice.split(",");

        if (random_choice ==0){
            display_choicesA.setText(answers[order]);
            display_choicesB.setText(choices[0]);
            display_choicesC.setText(choices[1]);
            display_choicesD.setText(choices[2]);

        }else  if (random_choice ==1){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(answers[order]);
            display_choicesC.setText(choices[1]);
            display_choicesD.setText(choices[2]);

        }else if (random_choice ==2){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(choices[1]);
            display_choicesC.setText(answers[order]);
            display_choicesD.setText(choices[2]);

        }else if (random_choice ==3){
            display_choicesA.setText(choices[0]);
            display_choicesB.setText(choices[1]);
            display_choicesC.setText(choices[2]);
            display_choicesD.setText(answers[order]);

        }

        display_choicesA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random_choice ==0){
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
                if (random_choice ==1){
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
                if (random_choice ==2){
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
                if (random_choice ==3){
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
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        switch (databaseManager.getQuest(questId).getIcon()) {
            case "quest":
                if (score >= (forEnd / 2)) {
                    image.setImageResource(R.drawable.qa_clear);
                }else {
                    image.setImageResource(R.drawable.qa);
                }
                break;
            case "qr":
                if (score >= (forEnd / 2)) {
                    image.setImageResource(R.drawable.qr_clear);
                }else {
                    image.setImageResource(R.drawable.qr);
                }
                break;
            case "beacon":
                if (score >= (forEnd / 2)) {
                    image.setImageResource(R.drawable.find_clear);
                }else {
                    image.setImageResource(R.drawable.find);
                }
                break;
            case "math":
                if (score >= (forEnd / 2)) {
                    image.setImageResource(R.drawable.math_clear);
                }else {
                    image.setImageResource(R.drawable.math);
                }
                break;
            case "maze":
                if (score >= (forEnd / 2)) {
                    image.setImageResource(R.drawable.maze_clear);
                }else {
                    image.setImageResource(R.drawable.maze);
                }
                break;
            case "mobcom":
                if (score >= (forEnd / 2)) {
                    image.setImageResource(R.drawable.mcl_clear);
                }else {
                    image.setImageResource(R.drawable.mcl);
                }
                break;
        }

        TextView text_status = (TextView) dialog.findViewById(R.id.status_question);
            text_status.setText("คะแนนของคุณคือ : "+score+"/"+(forEnd-1));

        Button btn_save = (Button) dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (score >= (forEnd / 2)) {
                    databaseManager.UpdateStatus(questId, 1);
                    Toast.makeText(QuestionActivity.this, "บันทึกเรียบร้อย", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(QuestionActivity.this, "คุณยังไม่ผ่านเควส", Toast.LENGTH_SHORT).show();
                    databaseManager.UpdateStatus(questId, 0);
                }

               finish();

            }
        });

        Button btn_again =(Button) dialog.findViewById(R.id.btn_playagain);
        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(QuestionActivity.this,QuestionActivity.class);
                intent.putExtra(QuestContents.QUEST_ID,questId);
                databaseManager.UpdateStatus(questId, 0);
                startActivity(intent);

                finish();
            }
        });


        dialog.show();

    }



        }



