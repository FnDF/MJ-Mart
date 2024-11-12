package edu.mj.mart.base;

public interface BaseView {

    void showLoading();

    void hideLoading();

    boolean isNetworkConnected();

    void hideKeyboard();
}
