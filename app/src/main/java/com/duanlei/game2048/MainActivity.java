package com.duanlei.game2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GameView.OnGameListener {

    private int score = 0;
    private TextView tvScore;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore = (TextView) findViewById(R.id.tvScore);

        gameView = (GameView) findViewById(R.id.gameView);
        gameView.setOnScoreListener(this);
    }

    public void showScore() {
        tvScore.setText(String.valueOf(score));
    }


    @Override
    public void addScore(int s) {
        score += s;
        showScore();
    }

    @Override
    public void clearScore() {
        score = 0;
        showScore();
    }

    @Override
    public void gameOver() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("游戏结束")
                .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameView.startGame();
                    }
                }).show();
    }
}
