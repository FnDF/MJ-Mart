package edu.mj.mart.activities.auth.forget;

import edu.mj.mart.base.BaseView;

public interface ForgetPassView extends BaseView {

    void sendOTPSuccessfully();

    void sendOTPFailed(String msg);

    void onEmailIsNotExists(String email);
}
