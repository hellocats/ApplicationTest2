package com.example.administrator.applicationtest2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.InjectView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_userName)
    EditText userName;
    @InjectView(R.id.main_passWord)
    EditText passWord;
    @InjectView(R.id.main_submit)
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //第一：默认初始化
        Bmob.initialize(this, "fd10491c215bdd79965ecdb249af55b7");
        clickEvent();
    }

    private void clickEvent() {
        submit.setOnClickListener(new ClickListener());
    }

    private class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.main_submit:
                    User user = new User();
                    user.setName(userName.getText().toString());
                    user.setPassword(passWord.getText().toString());
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e== null){
                                showToast("添加数据成功，返回值为："+s);
                            }else{
                                showToast("创建数据失败：" + e.getMessage());
                            }
                        }
                    });
                    break;
            }
        }
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
