package edu.mj.mart.activities.auth.login;

import edu.mj.mart.base.BaseView;
import edu.mj.mart.model.Account;

public interface LoginView extends BaseView {

    void loginSuccessfully(Account account);

    void loginFailed(String msg);
}
