package com.example.administrator.applicationtest2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.applicationtest2.util.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.mvp.view.test.listviewTest.ListviewActivity;

import butterknife.BindView;

public class MainActivity extends BaseClsActivity {

    @BindView(R.id.main_userName)
    EditText userName;
    @BindView(R.id.main_passWord)
    EditText passWord;
    @BindView(R.id.main_submit)
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickEvent();
        String a = ((String) "");
    }

    private void clickEvent() {
        submit.setOnClickListener(new ClickListener());
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_submit:
//                    User user = new User();
//                    user.setName(userName.getText().toString());
//                    user.setPassword(passWord.getText().toString());
//                    user.save(new SaveListener<String>() {
//                        @Override
//                        public void done(String s, BmobException e) {
//                            if (e == null) {
//                                showToast("添加数据成功，返回值为：" + s);
//                            } else {
//                                showToast("创建数据失败：" + e.getMessage());
//                            }
//                        }
//                    });
                    Intent intent = new Intent(MainActivity.this,ListviewActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}


