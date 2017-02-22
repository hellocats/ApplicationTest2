package com.example.administrator.applicationtest2.mvp.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.mvp.presenter.login.ILoginPresenter;
import com.example.administrator.applicationtest2.mvp.presenter.login.LoginPresenter;

import butterknife.BindView;

/**
 * Created by dell on 2016/11/27.
 */
public class LoginActivity extends BaseClsActivity implements ILoginActivity {
    @BindView(R.id.login_edtUserAccount)
    EditText edtUserAccount;
    @BindView(R.id.login_edtPassword)
    EditText edtPassword;
    @BindView(R.id.login_btnLogin)
    Button btnLogin;

    //    private ProgressBar progressBar;
    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_login);
        init();
        clickEvent();
    }

    private void init() {
        loginPresenter = new LoginPresenter(this);
//        loginPresenter.onSetProgressBarVisibility(View.INVISIBLE);
    }

    private void clickEvent() {
        btnLogin.setOnClickListener(new ClickListener());
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
//        progressBar.setVisibility(visibility);
    }

    @Override
    public void onLoginResult(boolean result, String info) {
        loginPresenter.onSetProgressBarVisibility(View.INVISIBLE);
        btnLogin.setEnabled(true);
        if (result) {
            showToast("登录成功！");
        } else {
            showToast(info);
        }
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_btnLogin:
//                    if ("".equals(edtUserName.getText().toString()) ||
//                            "".equals(edtPassword.getText().toString())) {
//                        showToast("请完善信息后再提交！");
//                        break;
//                    }
//                    UserModel userEntity = new UserModel();
//                    userEntity.setName(edtUserName.getText().toString());
//                    userEntity.setPassword(edtPassword.getText().toString());
//                    userEntity.save(new SaveListener<String>() {
//                        @Override
//                        public void done(String s, BmobException e) {
//                            showToast("添加成功！");
//                        }
//                    });
                    loginPresenter.onSetProgressBarVisibility(View.VISIBLE);
                    btnLogin.setEnabled(false);
                    loginPresenter.doLogin(edtUserAccount.getText().toString(), edtPassword.getText().toString());
                    break;
            }
        }
    }
}
