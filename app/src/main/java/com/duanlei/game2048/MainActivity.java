package com.duanlei.game2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
                    default:
                        break;
                }
                return true;
            }
        });
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
                        clearScore();
                    }
                }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        ShareActionProvider shareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        shareActionProvider.setShareIntent(intent);

        return super.onCreateOptionsMenu(menu);
    }
}
