package edu.mj.mart.activities.auth.reset;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.mj.mart.R;
import edu.mj.mart.activities.auth.AuthActivity;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentResetPasswordBinding;
import edu.mj.mart.utils.AESEncryption;
import edu.mj.mart.utils.SharedPrefUtils;

public class ResetPasswordFragment extends BaseFragment<FragmentResetPasswordBinding, ResetPwPresenter> implements ResetPwView {

    private boolean isShowPassword;
    private boolean isShowConfirmPassword;

    @NonNull
    @Override
    protected FragmentResetPasswordBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentResetPasswordBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgTroVeDatLaiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity) requireActivity()).onNavigationLogin();
            }
        });
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

        binding.ivShowConfirmPassword.setOnClickListener(view1 -> {
            if (isShowConfirmPassword) {
                isShowConfirmPassword = false;
                binding.edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.edtConfirmPassword.setSelection(binding.edtConfirmPassword.getText().toString().trim().length());
                binding.ivShowConfirmPassword.setImageResource(R.drawable.ic_hide_pass);
            } else {
                isShowConfirmPassword = true;
                binding.edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.edtConfirmPassword.setSelection(binding.edtConfirmPassword.getText().toString().trim().length());
                binding.ivShowConfirmPassword.setImageResource(R.drawable.baseline_remove_red_eye_24);
            }
        });

        binding.btnXacNhanDatLaiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = binding.edtPassword.getText().toString().trim();
                String passConfirm = binding.edtConfirmPassword.getText().toString().trim();

                if (pass.length() < 8) {
                    binding.tvPassError.setVisibility(View.VISIBLE);
                    return;
                } else {
                    binding.tvPassError.setVisibility(View.GONE);
                }
                if (passConfirm.length() < 8) {
                    binding.tvConfirmError.setVisibility(View.VISIBLE);
                    return;
                } else {
                    binding.tvConfirmError.setVisibility(View.GONE);
                }
                if (!pass.equals(passConfirm)) {
                    binding.tvConfirmError.setText(getString(R.string.password_wrong));
                    binding.tvConfirmError.setVisibility(View.VISIBLE);
                    return;
                } else {
                    binding.tvConfirmError.setVisibility(View.GONE);
                }

                String email = SharedPrefUtils.getStringData(requireContext(), "email");
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(requireContext(), "Đã có lồi hệ thống", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.resetPassword(email, AESEncryption.encrypt(pass));
            }
        });
    }

    @NonNull
    @Override
    protected ResetPwPresenter createPresenter() {
        return new ResetPwPresenter(requireActivity(), this);
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
        imm.hideSoftInputFromWindow(binding.edtPassword.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(binding.edtConfirmPassword.getWindowToken(), 0);
    }

    @Override
    public void getEmailFailed() {
        Toast.makeText(requireContext(), "Lấy thông tin Email thất bại!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resetPWSuccessfully() {
        Toast.makeText(requireContext(), "Đặt lại mật khẩu thành công!", Toast.LENGTH_SHORT).show();
        ((AuthActivity) requireActivity()).onNavigationLogin();
    }
}
