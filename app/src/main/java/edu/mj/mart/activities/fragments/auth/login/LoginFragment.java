package edu.mj.mart.activities.fragments.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.mj.mart.R;
import edu.mj.mart.activities.fragments.auth.AuthActivity;
import edu.mj.mart.activities.main.MainActivity;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentLoginBinding;
import edu.mj.mart.utils.AESEncryption;
import edu.mj.mart.utils.DeviceUtils;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginPresenter> implements LoginView {
    @NonNull
    @Override
    protected FragmentLoginBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginBinding.inflate(inflater, container, false);
    }

    private boolean isShowPassword;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ivShowPassword.setOnClickListener(view1 -> {
            if (isShowPassword) {
                isShowPassword = false;
                binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.edtPassword.setSelection(binding.edtPassword.getText().toString().trim().length());
                binding.ivShowPassword.setImageResource(R.drawable.ic_hide_pass);
            } else {
                isShowPassword = true;
                binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.edtPassword.setSelection(binding.edtPassword.getText().toString().trim().length());
                binding.ivShowPassword.setImageResource(R.drawable.baseline_remove_red_eye_24);
            }
        });

        binding.tvForgotPassword.setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).onNavigationForgetPass();
        });

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();

            if (email.isEmpty()) {
                binding.tvEmailError.setText(getString(R.string.email_empty));
                binding.tvEmailError.setVisibility(View.VISIBLE);
                return;
            }

            if (!DeviceUtils.validateEmailAddress(email)) {
                binding.tvEmailError.setText(getString(R.string.email_wrong));
                binding.tvEmailError.setVisibility(View.VISIBLE);
                return;
            }

            binding.tvEmailError.setVisibility(View.GONE);

            if (password.length() < 8) {
                binding.tvPassError.setText(getString(R.string.password_empty));
                binding.tvPassError.setVisibility(View.VISIBLE);
                return;
            }

            binding.tvPassError.setVisibility(View.GONE);
            presenter.login(email, AESEncryption.encrypt(password));
        });

        binding.btnRegister.setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).onNavigationRegister();
        });
    }

    @NonNull
    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(requireActivity(), this);
    }

    @Override
    public void loginSuccessfully() {
        startActivity(new Intent(requireActivity(), MainActivity.class));
        requireActivity().finish();
    }

    @Override
    public void loginFailed(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        ((AuthActivity) requireActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((AuthActivity) requireActivity()).hideLoading();
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }
}
