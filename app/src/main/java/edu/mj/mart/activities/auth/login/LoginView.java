package edu.mj.mart.activities.auth.login;

import edu.mj.mart.base.BaseView;

public interface LoginView extends BaseView {

    void loginSuccessfully();

    void loginFailed(String msg);
}