package edu.mj.mart.activities.main.fragments.profile;

import androidx.fragment.app.FragmentActivity;

import edu.mj.mart.base.BasePresenter;

public class ProfilePresenter extends BasePresenter<ProfileView> {
    protected ProfilePresenter(FragmentActivity fragmentActivity, ProfileView view) {
        super(fragmentActivity, view);
    }
}
