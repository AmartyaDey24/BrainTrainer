package com.example.braintrainerfinal;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;

public class GamePage extends AppCompatActivity {

    TextView timer,score,question,results;
    Button option0,option1,option2,option3,restart;
    ImageButton exit;
    CountDownTimer countDownTimer;
    public BranchUniversalObject branchUniversalObject;

    int IntScore = 0;
    int numOfQuestion = 0;
    public static int finalScore = 0, finalNumOfQuestions = 0;

    int correctAnswer;
    ArrayList<Integer> answers = new ArrayList<Integer>();

    public static int TIME_INTERVAL = 2000;
    private long backPressed;

    public GamePage() {
    }

    @Override
    public void onBackPressed() {
        if (backPressed + TIME_INTERVAL > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        } else
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        backPressed = System.currentTimeMillis();
    }

    public void Share (View view){
//        ShareApp();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String body = "Download this App";
        String sub = "https://50x3h.app.link/4DoCs94DBrb";
        intent.putExtra(Intent.EXTRA_TEXT,body);
        intent.putExtra(Intent.EXTRA_TEXT,sub);
        startActivity(Intent.createChooser(intent,"Share Using"));
    }

    public void ShareApp(){
        LinkProperties lp = new LinkProperties()
                .setChannel("brainTrainer")
                .setFeature("sharing")
                .setCampaign("content 123 launch")
                .setStage("new user")
                .addControlParameter("$desktop_url", "brainTrainer/view/");

        String shareTitle = "Checkout my calculation score" + finalScore + "/" + finalNumOfQuestions;
        String shareMessage = "I have just completed " + finalNumOfQuestions + " questions in 30 sec of time!!";

        ShareSheetStyle shareSheetStyle =new ShareSheetStyle(GamePage.this,shareTitle,shareMessage)
                .setCopyUrlStyle(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
                .setMoreOptionStyle(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_search), "Show more")
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.HANGOUT)
                .setAsFullWidthStyle(true)
                .setSharingTitle("Share With");

        branchUniversalObject.showShareSheet(this, lp, shareSheetStyle, new Branch.BranchLinkShareListener() {
            @Override
            public void onShareLinkDialogLaunched() {

            }

            @Override
            public void onShareLinkDialogDismissed() {

            }

            @Override
            public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {

            }

            @Override
            public void onChannelSelected(String channelName) {

            }
        });

    }

    public void playAgain(View view){
        restart.setVisibility(View.INVISIBLE);
        results.setVisibility(View.INVISIBLE);
        newQuestion();
        IntScore = 0;
        numOfQuestion = 0;
        score.setText("0/0");
        countDownTimer.start();
        option0.setEnabled(true);
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);

    }

    public void chooseOption(View view){
        if (Integer.toString(correctAnswer).equals(view.getTag().toString())){
            results.setVisibility(View.VISIBLE);
            results.setText("CORRECT!");
            IntScore++;
        } else {
            results.setVisibility(View.VISIBLE);
            results.setText("WRONG!!");
        }
        numOfQuestion++;
        score.setText(Integer.toString(IntScore) + "/" + Integer.toString(numOfQuestion));
        finalNumOfQuestions = numOfQuestion;
        finalScore = IntScore;
        newQuestion();
    }

    public void newQuestion() {
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        question.setText(Integer.toString(a) + " + " + Integer.toString(b));

        correctAnswer = rand.nextInt(4);

        answers.clear();

        for (int i=0;i<4;i++){
            if (i == correctAnswer) {
                answers.add(a+b);
            }
            else {
                int wrongAnswer = rand.nextInt(41);

                while (wrongAnswer == a+b) {
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }

        }
        option0.setText(Integer.toString(answers.get(0)));
        option1.setText(Integer.toString(answers.get(1)));
        option2.setText(Integer.toString(answers.get(2)));
        option3.setText(Integer.toString(answers.get(3)));
    }

    public void exit(View view){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Do you want to exit?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("NO",null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        timer = (TextView) findViewById(R.id.timer);
        score = (TextView) findViewById(R.id.score);
        question = (TextView) findViewById(R.id.question);
        results = (TextView) findViewById(R.id.results);

        option0 = (Button) findViewById(R.id.option1);
        option1 = (Button) findViewById(R.id.option2);
        option2 = (Button) findViewById(R.id.option3);
        option3 = (Button) findViewById(R.id.option4);

        restart = (Button) findViewById(R.id.restart);
        exit = (ImageButton) findViewById(R.id.exit);

        newQuestion();

        countDownTimer = new CountDownTimer(31000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000 + "s"));
            }

            @Override
            public void onFinish() {
                results.setVisibility(View.VISIBLE);
                results.setText("DONE!");
                restart.setVisibility(View.VISIBLE);
                restart.setText("Play Again !!");
                option0.setEnabled(false);
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
            }
        }.start();
    }
}