package com.example.administrator.applicationtest2.mvp.view.test.userDefine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.util.baseClass.BaseClsActivity;

import butterknife.BindView;

/**
 * Created by hepeng on 2016/12/14.
 */
public class UserDefineActivity extends BaseClsActivity {
    @BindView(R.id.test_userdefine_myTopBar)
    MyTopbar myTopBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_userdefine_main);
        myTopBar.setOnTopbarClickListener(new MyTopbar.topbarClickListener() {
            @Override
            public void leftClick() {
                showToast("左边点击");
            }

            @Override
            public void rightClick() {
                showToast("右边点击");
            }
        });
    }
}
