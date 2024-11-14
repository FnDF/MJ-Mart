package edu.mj.mart.activities.main.home;

import androidx.fragment.app.FragmentActivity;

import edu.mj.mart.base.BasePresenter;

public class HomePresenter extends BasePresenter<HomeView> {

    protected HomePresenter(FragmentActivity fragmentActivity, HomeView view) {
        super(fragmentActivity, view);
    }
}
