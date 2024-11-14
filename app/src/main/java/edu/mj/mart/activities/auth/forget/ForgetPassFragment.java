package edu.mj.mart.activities.auth.forget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.mj.mart.activities.auth.AuthActivity;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentForgetPassBinding;

public class ForgetPassFragment extends BaseFragment<FragmentForgetPassBinding, ForgetPassPresenter>
        implements ForgetPassView {
    @NonNull
    @Override
    protected FragmentForgetPassBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentForgetPassBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected ForgetPassPresenter createPresenter() {
        return new ForgetPassPresenter(requireActivity(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnForgetPass.setOnClickListener(v -> {
            hideKeyboard();
            validateInputs();

            if (binding.tvEmailError.getVisibility() != View.VISIBLE) {
                String email = binding.edtEmail.getText().toString().trim();
                presenter.checkEmail(email);
            }
        });

        binding.ivBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    @Override
    public void sendOTPSuccessfully() {
        hideLoading();
        ((AuthActivity) requireActivity()).onNavigationOTP();
    }

    @Override
    public void sendOTPFailed(String msg) {
        hideLoading();
        binding.tvEmailError.setText("Gửi code OTP thất bại! Lỗi " + msg);
        binding.tvEmailError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmailIsNotExists(String email) {
        hideLoading();
        binding.tvEmailError.setText("Email " + email + " chưa đăng ký trên hệ thống!");
        binding.tvEmailError.setVisibility(View.VISIBLE);
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
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.edtEmail.getWindowToken(), 0);
    }

    private void validateInputs() {
        String email = binding.edtEmail.getText().toString().trim();

        // Reset thông báo lỗi
        binding.tvEmailError.setVisibility(View.GONE);

        // Kiểm tra email
        if (email.isEmpty()) {
            binding.tvEmailError.setText("Vui lòng nhập email");
            binding.tvEmailError.setVisibility(View.VISIBLE);
        } else if (!isValidEmail(email)) {
            binding.tvEmailError.setText("Email không hợp lệ");
            binding.tvEmailError.setVisibility(View.VISIBLE);
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailPattern);
    }
}
