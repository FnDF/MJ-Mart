package edu.mj.mart.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import edu.mj.mart.R;
import edu.mj.mart.activities.main.MainActivity;
import edu.mj.mart.activities.auth.forget.ForgetPassFragment;
import edu.mj.mart.activities.auth.login.LoginFragment;
import edu.mj.mart.activities.auth.register.RegisterFragment;
import edu.mj.mart.activities.auth.reset.ResetPasswordFragment;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityAuthBinding;

public class AuthActivity extends BaseActivity<ActivityAuthBinding> {

    @NonNull
    @Override
    protected ActivityAuthBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityAuthBinding.inflate(layoutInflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.layoutLoading.setOnClickListener(v -> {
        });

//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(AuthActivity.this, MainActivity.class));
//            finish();
//        } else {
        binding.fragmentContainer.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new LoginFragment()).commit();
//        }
    }

    public void showLoading() {
        binding.layoutLoading.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        binding.layoutLoading.setVisibility(View.GONE);
    }

    public void onNavigationRegister() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new RegisterFragment())
                .addToBackStack("register")
                .commit();
    }

    public void onNavigationForgetPass() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new ForgetPassFragment())
                .addToBackStack("forget")
                .commit();
    }

    public void onNavigationOTP() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new XacMinhOTPFragment())
                .addToBackStack("otp")
                .commit();
    }

    public void onNavigationResetPW() {
        getSupportFragmentManager().popBackStack();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new ResetPasswordFragment())
                .addToBackStack("resetPW")
                .commit();
    }

    public void onNavigationLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new LoginFragment())
                .commit();
    }
}
