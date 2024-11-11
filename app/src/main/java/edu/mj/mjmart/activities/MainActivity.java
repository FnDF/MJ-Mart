package edu.mj.mjmart.activities;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import edu.mj.mjmart.core.BaseActivity;
import edu.mj.mjmart.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @NonNull
    @Override
    protected ActivityMainBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityMainBinding.inflate(layoutInflater);
    }

}
