package edu.mj.mjmart.base;

public abstract class BasePresenter<V extends BaseView> {

    protected String TAG = this.getClass().getName();

    protected V view;

    protected BasePresenter(V view) {
        this.view = view;
    }

    public void onDetach() {
        view = null;
    }
}
