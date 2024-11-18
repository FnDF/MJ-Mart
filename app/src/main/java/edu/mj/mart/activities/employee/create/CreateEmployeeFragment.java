package edu.mj.mart.activities.employee.create;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.mj.mart.R;
import edu.mj.mart.activities.employee.EmployeeManagerActivity;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentCreateEmployeeBinding;
import edu.mj.mart.model.Employee;
import edu.mj.mart.utils.AESEncryption;
import edu.mj.mart.utils.Constants;
import edu.mj.mart.utils.DeviceUtils;

public class CreateEmployeeFragment extends BaseFragment<FragmentCreateEmployeeBinding, CreateEmployeePresenter>
        implements CreateEmployeeView {

    private boolean isShowPassword;
    private boolean isShowConfirmPassword;
    private Employee employee;

    @NonNull
    @Override
    protected FragmentCreateEmployeeBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCreateEmployeeBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected CreateEmployeePresenter createPresenter() {
        return new CreateEmployeePresenter(requireActivity(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ivBack.setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

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

        binding.btnCreate.setOnClickListener(v -> {
            String phone = binding.edtPhone.getText().toString().trim();
            if (!DeviceUtils.validPhoneNumber(phone)) {
                Toast.makeText(requireContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            String fullName = binding.edtFullName.getText().toString().trim();
            if (TextUtils.isEmpty(fullName)) {
                Toast.makeText(requireContext(), "Vui lòng nhập tên nhân viên", Toast.LENGTH_SHORT).show();
                return;
            }

            String email = binding.edtEmail.getText().toString().trim();
            if (TextUtils.isEmpty(email) || !DeviceUtils.validateEmailAddress(email)) {
                Toast.makeText(requireContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            String password = binding.edtPassword.getText().toString().trim();
            if (TextUtils.isEmpty(password) || password.length() < 8) {
                Toast.makeText(requireContext(), getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
                return;
            }
            String cfPassword = binding.edtConfirmPassword.getText().toString().trim();
            if (TextUtils.isEmpty(cfPassword) || cfPassword.length() < 8) {
                Toast.makeText(requireContext(), getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(cfPassword)) {
                Toast.makeText(requireContext(), getString(R.string.password_wrong), Toast.LENGTH_SHORT).show();
                return;
            }
            employee = new Employee(email, AESEncryption.encrypt(password), phone, fullName, null);

            presenter.checkEmailIsExists(email);
        });
    }

    @Override
    public void showLoading() {
        ((EmployeeManagerActivity) requireActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((EmployeeManagerActivity) requireActivity()).hideLoading();
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void onError(String msg) {
        String message = msg;
        if (TextUtils.isEmpty(message)) {
            message = getString(R.string.error);
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmailsIsExists(boolean isExists) {
        if (isExists || employee == null) {
            Toast.makeText(requireContext(), "Email đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.createEmployee(employee);
    }

    @Override
    public void onCreateEmployeeSuccessfully() {
        Toast.makeText(requireContext(), "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
        ((EmployeeManagerActivity) requireActivity()).reloadData(Constants.convertFromCurrentAccount());
        requireActivity().getOnBackPressedDispatcher().onBackPressed();
    }

    @Override
    public void onCreateEmployeeFailed() {
        Toast.makeText(requireContext(), "Thêm nhân viên thất bại. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
    }
}
