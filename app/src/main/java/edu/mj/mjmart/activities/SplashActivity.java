package edu.mj.mjmart.activities;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import edu.mj.mjmart.core.BaseActivity;
import edu.mj.mjmart.databinding.ActivitySplashBinding;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @NonNull
    @Override
    protected ActivitySplashBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivitySplashBinding.inflate(layoutInflater);
    }
}
