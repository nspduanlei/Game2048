package com.duanlei.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Author: duanlei
 * Date: 2015-12-29
 */
public class Card extends FrameLayout {

    private TextView label;

    public TextView getLabel() {
        return label;
    }

    public void setLabel(TextView label) {
        this.label = label;
    }


    public Card(Context context) {
        super(context);

        label = new TextView(getContext());
        label.setTextSize(33);
        label.setGravity(Gravity.CENTER);


        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(GameView.CARD_PADDING, GameView.CARD_PADDING, 0, 0);
        addView(label, lp);

        setNum(0);
    }

    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        if (num <= 4 ) {
            label.setTextColor(getResources().getColor(R.color.text_color));
        } else {
            label.setTextColor(0xffffffff);
        }


        switch (num) {
            case 0:
                label.setBackgroundColor(0x33ffffff);
                break;

            case 2:
                label.setBackgroundColor(getResources().getColor(R.color.c2));
                break;

            case 4:
                label.setBackgroundColor(getResources().getColor(R.color.c4));
                break;

            case 8:
                label.setBackgroundColor(getResources().getColor(R.color.c8));
                break;

            case 16:
                label.setBackgroundColor(getResources().getColor(R.color.c16));
                break;

            case 32:
                label.setBackgroundColor(getResources().getColor(R.color.c32));
                break;

            case 64:
                label.setBackgroundColor(getResources().getColor(R.color.c64));
                break;

            case 128:
                label.setBackgroundColor(getResources().getColor(R.color.c128));
                break;

            case 256:
                label.setBackgroundColor(getResources().getColor(R.color.c256));
                break;

            case 512:
                label.setBackgroundColor(getResources().getColor(R.color.c512));
                break;

            case 2048:
                label.setBackgroundColor(getResources().getColor(R.color.c2048));
                break;

            case 4096:
                label.setBackgroundColor(getResources().getColor(R.color.c4096));
                break;

            case 8192:
                label.setBackgroundColor(getResources().getColor(R.color.c8192));
                break;

            default:
                label.setBackgroundColor(getResources().getColor(R.color.c_other));
                break;

        }

        if (num <= 0) {
            label.setText("");
        } else {
            label.setText(String.valueOf(num));
        }
    }


    public boolean equals(Card o) {
        return getNum() == o.getNum();
    }

}
