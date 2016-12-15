package com.example.administrator.applicationtest2.mvp.view.test.userDefine;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
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

    private topbarClickListener listener;

    public interface topbarClickListener{
        void leftClick();
        void rightClick();
    }
    public void setOnTopbarClickListener(topbarClickListener listener){
        this.listener = listener;
    }

    public MyTopbar(Context context, AttributeSet attrs) {
        super(context, attrs);

        getAttr(context, attrs);
        setAttr(context);
        addAllView();
        setGlobalAttr();
        setEvent();
    }



    /**
     * 设置全局属性
     */
    private void setGlobalAttr() {
        setBackgroundColor(Color.parseColor("#73DC7E"));
    }
    /**
     * 获取自定义属性
     * @param context
     * @param attrs
     */
    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTopbar);
        leftTextColor= ta.getColor(R.styleable.MyTopbar_myLeftTextColor,0);
        leftBackground=ta.getDrawable(R.styleable.MyTopbar_myLeftBackground);
        leftText = ta.getString(R.styleable.MyTopbar_myLeftText);

        rightTextColor= ta.getColor(R.styleable.MyTopbar_myRightTextColor,0);
        rightBackground=ta.getDrawable(R.styleable.MyTopbar_myRightBackground);
        rightText = ta.getString(R.styleable.MyTopbar_myRightText);

        titleTextSize = ta.getDimension(R.styleable.MyTopbar_myTitleTextSize,16);
        titleTextColor=ta.getColor(R.styleable.MyTopbar_myTitleTextColor,0);
        title=ta.getString(R.styleable.MyTopbar_myTitleText);
        ta.recycle();
    }

    /**
     * 设置自定义属性相应的值
     * @param context
     */
    private void setAttr(Context context) {
        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setPadding(20, 0, 0, 0);
        leftButton.setText(leftText);

        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setPadding(0, 0, 20, 0);
        rightButton.setText(rightText);

        tvTitle.setTextSize(titleTextSize);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setText(title);
        tvTitle.setGravity(Gravity.CENTER);
    }

    /**
     * 添加所有view
     */
    private void addAllView() {
        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftButton,leftParams);

        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightButton,rightParams);

        titleParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(tvTitle,titleParams);
    }

    /**
     * 添加事件
     */
    private void setEvent() {
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });
    }


}
