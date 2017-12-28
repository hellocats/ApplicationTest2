package com.example.administrator.applicationtest2.presenter.login;

/**
 * Created by dell on 2016/11/27.
 */
public interface ILoginPresenter {
    void doLogin(String account, String password);
    void onSetProgressBarVisibility(int visibility);
}
