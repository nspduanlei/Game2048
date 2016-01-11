package com.duanlei.game2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GameView.OnGameListener {

    private int score = 0;
    private TextView tvScore, tvRecord;
    private GameView gameView;
    private static final String PRE_RECORD = "record";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore = (TextView) findViewById(R.id.tvScore);
        tvRecord = (TextView) findViewById(R.id.tvRecord);
        tvRecord.setText(String.valueOf(getRecord()));
        gameView = (GameView) findViewById(R.id.gameView);
        gameView.setOnScoreListener(this);

        //使用ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("2048");
        setSupportActionBar(toolbar);

        //菜单监听，可以在toolbar里设置
        //也可以通过Activity的onOptionsItemSelected回调方法来处理
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_refresh:
                        gameView.startGame();
                        clearScore();
                        break;

                    case R.id.action_share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/*");
                        MainActivity.this.startActivity(intent);

                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        //插入广告
        addAd();
    }

    private void addAd() {

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

        int record = getRecord();

        if (score > record) {
            tvRecord.setText(String.valueOf(score));
            saveRecord();
            new AlertDialog.Builder(this)
                    .setTitle("您好")
                    .setMessage("恭喜获得打破记录！")
                    .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gameView.startGame();
                            clearScore();
                        }
                    }).setNegativeButton("分享", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO 分享
                }
            }).show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("您好")
                    .setMessage("游戏结束")
                    .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gameView.startGame();
                            clearScore();
                        }
                    }).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void saveRecord() {
        SharedPreferences preferences =
                getSharedPreferences("game2048", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PRE_RECORD, score);
        editor.commit();
    }


    private int getRecord() {
        SharedPreferences preferences =
                getSharedPreferences("game2048", AppCompatActivity.MODE_PRIVATE);

        return preferences.getInt(PRE_RECORD, 0);
    }
}
