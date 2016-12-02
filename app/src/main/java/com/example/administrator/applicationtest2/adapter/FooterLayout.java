package com.example.administrator.applicationtest2.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.administrator.applicationtest2.R;

/**
 * Created by Administrator on 2016-12-02.
 */
public class FooterLayout extends FrameLayout {
    private Context context;
    public FooterLayout(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public FooterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public FooterLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        init();
    }

    public void init(){
        LayoutInflater.from(context).inflate(R.layout.com_footer_loaddate, this);
    }
}
