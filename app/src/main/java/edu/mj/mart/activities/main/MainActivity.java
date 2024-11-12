package edu.mj.mart.activities.main;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @NonNull
    @Override
    protected ActivityMainBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityMainBinding.inflate(layoutInflater);
    }

}
