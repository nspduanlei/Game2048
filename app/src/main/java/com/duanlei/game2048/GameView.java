package com.duanlei.game2048;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: duanlei
 * Date: 2015-12-29
 */
public class GameView extends GridLayout {

    //记录方阵
    private Card[][] cardsMap = new Card[4][4];
    //记录card为空的位置
    private List<Point> emptyPoints = new ArrayList<>();


    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, widthSpec);
    }

    private void initGameView() {
        setColumnCount(4);
        setBackgroundColor(getResources().getColor(R.color.game_background));


        //判断用户的意图，记录下手指按下，和手指离开的位置
        setOnTouchListener(new OnTouchListener() {

            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                Log.d("test1", "left");
                                swipeLeft();
                            } else if (offsetX > 5) {
                                Log.d("test1", "right");
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                Log.d("test1", "up");
                                swipeUp();
                            } else if (offsetY > 5) {
                                Log.d("test1", "down");
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    interface OnGameListener {
        void addScore(int score);
        void clearScore();
        void gameOver();
    }

    private OnGameListener mOnGameListener;

    public void setOnScoreListener(OnGameListener listener) {
        mOnGameListener = listener;
    }

    private void swipeLeft() {

        boolean isMerge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {


                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {

                            //移动到空白位置
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x--;

                            isMerge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])){


                            //合并数字
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);

                            //记录分数
                            mOnGameListener.addScore(cardsMap[x][y].getNum());

                            isMerge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (isMerge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeRight() {

        boolean isMerge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;

                            isMerge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            //记录分数
                            mOnGameListener.addScore(cardsMap[x][y].getNum());

                            isMerge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (isMerge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeUp() {

        boolean isMerge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;

                            isMerge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            //记录分数
                            mOnGameListener.addScore(cardsMap[x][y].getNum());

                            isMerge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (isMerge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeDown() {

        boolean isMerge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;

                            isMerge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            //记录分数
                            mOnGameListener.addScore(cardsMap[x][y].getNum());

                            isMerge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (isMerge) {
            addRandomNum();
            checkComplete();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //在第一次创建的时候被执行

        //每一张卡片的宽高
        int cardWidth = (Math.min(w, h) - 10) / 4;

        //添加卡片
        addCards(cardWidth, cardWidth);

        startGame();
    }

    private void addCards(int cardWidth, int cardHeight) {

        Card c;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWidth, cardHeight);

                cardsMap[x][y] = c;

            }
        }
    }

    public void startGame() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();

        //mOnGameListener.clearScore();
    }

    //添加随机数
    private void addRandomNum() {
        emptyPoints.clear();
        for (int y = 0 ; y < 4; y++) {
            for (int x = 0 ; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        //随机位置
        Point p = emptyPoints.remove((int)(Math.random() * emptyPoints.size()));
        //在随机的位置上设置随机值 2，4，比例为 9 ： 1
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);

        animCreate(cardsMap[p.x][p.y]);
    }

    /**
     * 生成卡片动画
     * @param card
     */
    private void animCreate(Card card) {
        ScaleAnimation sa = new ScaleAnimation(
                0.1f, 1, 0.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        sa.setDuration(100);
        card.setAnimation(null);
        card.startAnimation(sa);
    }


    //检查游戏是否结束
    private void checkComplete() {

        boolean isComplete = true;

        ALL:
        for (int y = 0 ; y < 4; y++) {
            for (int x = 0 ; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0 ||
                        (x > 0 && cardsMap[x][y].equals(cardsMap[x-1][y])) ||
                        (x < 3 && cardsMap[x][y].equals(cardsMap[x+1][y])) ||
                        (y > 0 && cardsMap[x][y].equals(cardsMap[x][y-1])) ||
                        (y < 3 && cardsMap[x][y].equals(cardsMap[x][y+1])) ) {

                    isComplete = false;

                    break ALL;
                }
            }
        }

        if (isComplete) {
            mOnGameListener.gameOver();
        }
    }

}

