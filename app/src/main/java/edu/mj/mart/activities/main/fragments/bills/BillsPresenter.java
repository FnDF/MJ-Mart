package edu.mj.mart.activities.main.fragments.bills;

import androidx.fragment.app.FragmentActivity;

import edu.mj.mart.base.BasePresenter;

public class BillsPresenter extends BasePresenter<BillsView> {
    protected BillsPresenter(FragmentActivity fragmentActivity, BillsView view) {
        super(fragmentActivity, view);
    }
}
