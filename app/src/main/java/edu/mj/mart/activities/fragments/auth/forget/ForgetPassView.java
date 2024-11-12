package edu.mj.mart.activities.fragments.auth.forget;

import edu.mj.mart.base.BaseView;

public interface ForgetPassView extends BaseView {

    void sendOTPSuccessfully();

    void sendOTPFailed();
}
