package edu.mj.mart.activities.main.product;

import androidx.fragment.app.FragmentActivity;

import edu.mj.mart.base.BasePresenter;

public class ProductPresenter extends BasePresenter<ProductView> {
    protected ProductPresenter(FragmentActivity fragmentActivity, ProductView view) {
        super(fragmentActivity, view);
    }
}
