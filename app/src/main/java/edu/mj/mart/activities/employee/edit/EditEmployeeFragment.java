package edu.mj.mart.activities.employee.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.mj.mart.R;
import edu.mj.mart.activities.employee.EmployeeManagerActivity;
import edu.mj.mart.core.BaseFragment;
import edu.mj.mart.databinding.FragmentEditEmployeeBinding;
import edu.mj.mart.model.Employee;
import edu.mj.mart.utils.ImageUtil;

public class EditEmployeeFragment extends BaseFragment<FragmentEditEmployeeBinding, EditEmployPresenter> implements EditEmployeeView {

    private Employee employee;

    @NonNull
    @Override
    protected FragmentEditEmployeeBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEditEmployeeBinding.inflate(inflater, container, false);
    }

    @NonNull
    @Override
    protected EditEmployPresenter createPresenter() {
        return new EditEmployPresenter(requireActivity(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            employee = (Employee) getArguments().get("key_employee");
        }
        if (employee == null) {
            Toast.makeText(requireContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            onBack();
            return;
        }
        if (employee.getAvatar() == null || employee.getAvatar().isEmpty()) {
            binding.ivAvatar.setImageResource(R.drawable.icon_profile_default);
        } else {
            StringBuilder ima = new StringBuilder();
            for (String s : employee.getAvatar()) {
                ima.append(s);
            }
            if (ima.toString().isEmpty()) {
                binding.ivAvatar.setImageResource(R.drawable.icon_profile_default);
            } else {
                binding.ivAvatar.setImageBitmap(ImageUtil.decode(ima.toString()));
            }
        }

    }

    private void onBack() {
        requireActivity().getOnBackPressedDispatcher().onBackPressed();
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


}
