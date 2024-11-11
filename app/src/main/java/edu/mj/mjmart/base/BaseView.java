package edu.mj.mjmart.base;

public interface BaseView {

    void showLoading();

    void hideLoading();

    boolean isNetworkConnected();

    void hideKeyboard();
}
