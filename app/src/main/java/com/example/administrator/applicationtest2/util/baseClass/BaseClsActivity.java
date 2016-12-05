package com.example.administrator.applicationtest2.util.baseClass;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016-12-02.
 */
public class BaseClsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一：默认初始化
        Bmob.initialize(this, "fd10491c215bdd79965ecdb249af55b7");
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    /**
     * 显示一个Toast信息
     *
     * @param content
     */
    protected Toast mToast = null;
    protected void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }
}
