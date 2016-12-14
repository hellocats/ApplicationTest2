package com.example.administrator.applicationtest2.mvp.view.test.userDefine;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.applicationtest2.R;

/**
 * Created by Administrator on 2016-12-14.
 */
public class MyTopbar extends RelativeLayout {
    private Button leftButton, rightButton;
    private TextView tvTitle;

    private int leftTextColor;
    private Drawable leftBackground;
    private String leftText;

    private int rightTextColor;
    private Drawable rightBackground;
    private String rightText;

    private float titleTextSize;
    private int titleTextColor;
    private String title;

    private LayoutParams leftParams,rightParams,titleParams;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public MyTopbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Toolbar);
        leftTextColor= ta.getColor(R.styleable.MyTopBar_myLeftTextColor,0);
        leftBackground=ta.getDrawable(R.styleable.MyTopBar_myLeftBackground);
        leftText = ta.getString(R.styleable.MyTopBar_myLeftText);

        rightTextColor= ta.getColor(R.styleable.MyTopBar_myRightTextColor,0);
        rightBackground=ta.getDrawable(R.styleable.MyTopBar_myRightBackground);
        rightText = ta.getString(R.styleable.MyTopBar_myRightText);
        
        titleTextSize = ta.getDimension(R.styleable.MyTopBar_myTitleTextSize,0);
        titleTextColor=ta.getColor(R.styleable.MyTopBar_myTitleTextColor,0);
        title=ta.getString(R.styleable.MyTopBar_myTitleText);

        ta.recycle();

        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);
        leftButton.setLines(2);

        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);
        rightButton.setLines(2);

        tvTitle.setTextSize(titleTextSize);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setText(title);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setLines(2);

        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(leftButton,leftParams);

        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(rightButton,rightParams);

        titleParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(tvTitle,titleParams);

        setBackgroundColor(Color.parseColor("#73DC7E"));

    }
}
