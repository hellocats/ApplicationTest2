package com.example.administrator.applicationtest2.presenter.login;

import android.os.Handler;
import android.os.Looper;

import com.example.administrator.applicationtest2.entity.User;
import com.example.administrator.applicationtest2.view.login.ILoginActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by dell on 2016/11/27.
 */
public class LoginPresenter implements ILoginPresenter {

    private ILoginActivity iLoginActivity;
    private Handler handler;
    private User userModel;

    public LoginPresenter(ILoginActivity iLoginActivity){
        this.iLoginActivity= iLoginActivity;
        handler = new Handler(Looper.getMainLooper());
        userModel =new User();
    }
    @Override
    public void doLogin(String account, String password) {
        userModel.setAccount(account);
        userModel.setPassword(password);
        userModel.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    iLoginActivity.onLoginResult(true,s);
                }else{
                    iLoginActivity.onLoginResult(false,e.toString());
                }
            }
        });
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
//        iLoginActivity.onSetProgressBarVisibility(visibility);
    }
}
