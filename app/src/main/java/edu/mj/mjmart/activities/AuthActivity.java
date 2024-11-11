package edu.mj.mjmart.activities;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import edu.mj.mjmart.core.BaseActivity;
import edu.mj.mjmart.databinding.ActivityAuthBinding;

public class AuthActivity extends BaseActivity<ActivityAuthBinding> {

    @NonNull
    @Override
    protected ActivityAuthBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityAuthBinding.inflate(layoutInflater);
    }
}
