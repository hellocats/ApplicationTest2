package com.example.administrator.applicationtest2.view.test.userDefine;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;

import butterknife.BindView;

/**
 * 测试自定义topbar和弹出框样式
 * Created by hepeng on 2016/12/14.
 */
public class UserDefineActivity extends BaseClsActivity implements View.OnClickListener{
    @BindView(R.id.test_userdefine_myTopBar)
    MyTopbar myTopBar;
    @BindView(R.id.test_userdefine_button1)
    Button button1;
    @BindView(R.id.test_userdefine_button2)
    Button button2;
    @BindView(R.id.test_userdefine_button3)
    Button button3;
    @BindView(R.id.test_userdefine_button4)
    Button button4;
    @BindView(R.id.test_userdefine_button5)
    Button button5;

    private String upgradeInfo;
    private static final String title="有新版本啦~";
    private static final String sure = "升级";
    private static final String cancle = "忽略";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_userdefine_main);
        setClickEvent();
        String message ="【新增】消息相关功能|【优化】自动升级界面优化|【优化】其他功能优化";
        StringBuilder builder = new StringBuilder("V");
        builder.append("3.1.1");
        builder.append("\n");
        String upgrade =  message.replace("|","\n").toString();
        builder.append(upgrade);
        upgradeInfo = builder.toString();
    }

    private void setClickEvent() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        myTopBar.setOnTopbarClickListener(new MyTopbar.topbarClickListener() {
            @Override
            public void leftClick() {
//                showToast("左边点击");

            }

            @Override
            public void rightClick() {
//                showToast("右边点击");

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_userdefine_button1:
                new AlertDialog.Builder(UserDefineActivity.this,AlertDialog.THEME_TRADITIONAL)
                        .setTitle(title)
                        .setMessage(upgradeInfo)
                        .setPositiveButton(sure, null).setNegativeButton(cancle,null).show();
                break;
            case R.id.test_userdefine_button2:
                new AlertDialog.Builder(UserDefineActivity.this,AlertDialog.THEME_HOLO_LIGHT)
                        .setTitle(title)
                        .setMessage(upgradeInfo)
                        .setPositiveButton(sure, null).setNegativeButton(cancle,null).show();
                break;
            case R.id.test_userdefine_button3:
                new AlertDialog.Builder(UserDefineActivity.this,AlertDialog.THEME_HOLO_DARK)
                        .setTitle(title)
                        .setMessage(upgradeInfo)
                        .setPositiveButton(sure, null).setNegativeButton(cancle,null).show();
                break;
            case R.id.test_userdefine_button4:
                new AlertDialog.Builder(UserDefineActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setTitle(title)
                        .setMessage(upgradeInfo)
                        .setPositiveButton(sure, null).setNegativeButton(cancle,null).show();
                break;
            case R.id.test_userdefine_button5:
                new AlertDialog.Builder(UserDefineActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                        .setTitle(title)
                        .setMessage(upgradeInfo)
                        .setPositiveButton(sure, null).setNegativeButton(cancle,null).show();
                break;
        }
    }
}
