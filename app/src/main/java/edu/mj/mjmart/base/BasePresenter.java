package edu.mj.mjmart.base;

public interface BasePresenter<V extends BaseView> {

    void onAttach(V mvpView);

    void onDetach();
}
