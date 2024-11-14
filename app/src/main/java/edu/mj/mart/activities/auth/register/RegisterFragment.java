package edu.mj.mart.activities.auth.register;

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
import edu.mj.mart.activities.auth.AuthActivity;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentRegisterBinding;
import edu.mj.mart.utils.AESEncryption;
import edu.mj.mart.utils.DeviceUtils;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterPresenter> implements RegisterView {

    private boolean isShowPassword;

    @NonNull
    @Override
    protected FragmentRegisterBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRegisterBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ivBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        binding.ivShowPassword.setOnClickListener(view1 -> {
            if (isShowPassword) {
                isShowPassword = false;
                binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.edtPassword.setSelection(binding.edtPassword.getText().toString().trim().length());
                binding.ivShowPassword.setImageResource(R.drawable.baseline_remove_red_eye_24);
            } else {
                isShowPassword = true;
                binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.edtPassword.setSelection(binding.edtPassword.getText().toString().trim().length());
                binding.ivShowPassword.setImageResource(R.drawable.ic_hide_pass);
            }
        });

        binding.btnRegister.setOnClickListener(v -> {
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

            if (password.isEmpty()) {
                binding.tvPassError.setText(getString(R.string.password_empty));
                binding.tvPassError.setVisibility(View.VISIBLE);
                return;
            }
            if (password.length() < 6) {
                binding.tvPassError.setText(getString(R.string.password_min));
                binding.tvPassError.setVisibility(View.VISIBLE);
                return;
            }

            binding.tvPassError.setVisibility(View.GONE);
            presenter.register(email, AESEncryption.encrypt(password));
        });
    }

    @NonNull
    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(requireActivity(), this);
    }

    @Override
    public void onRegisterSuccessfully() {
        Toast.makeText(requireActivity(), "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterFailed() {
        Toast.makeText(requireActivity(), "Tài khoản email đã tồn tại!", Toast.LENGTH_SHORT).show();
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
        return true;
    }

    @Override
    public void hideKeyboard() {

    }
}
