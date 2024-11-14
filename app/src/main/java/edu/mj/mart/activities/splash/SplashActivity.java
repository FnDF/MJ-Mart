package edu.mj.mart.activities.splash;

import static edu.mj.mart.utils.SharedPrefUtils.KEY_IS_FIRST_TIME;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import edu.mj.mart.activities.auth.AuthActivity;
import edu.mj.mart.activities.tutorial.TutorialActivity;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivitySplashBinding;
import edu.mj.mart.utils.SharedPrefUtils;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @NonNull
    @Override
    protected ActivitySplashBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivitySplashBinding.inflate(layoutInflater);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (SharedPrefUtils.getBooleanData(this, KEY_IS_FIRST_TIME, true)) {
                startActivity(new Intent(this, TutorialActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, AuthActivity.class));
                finish();
            }
        }, 2000);
    }
}
