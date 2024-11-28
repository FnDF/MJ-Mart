package edu.mj.mart.activities.customer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.Serializable;

import edu.mj.mart.databinding.CustomerBottomSheetDialogBinding;
import edu.mj.mart.model.Customer;

public class CustomerBottomSheetDialog extends BottomSheetDialogFragment {

    private CustomerBottomSheetDialogBinding binding;
    private OnCustomerListener listener;
    private Customer customer;

    public interface OnCustomerListener extends Serializable {
        void onActionCustomer(String name, String phone);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CustomerBottomSheetDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            listener = (OnCustomerListener) getArguments().getSerializable("listener");
            customer = (Customer) getArguments().getSerializable("customer");
        }
        if (customer != null) {
            binding.edtName.setText(customer.getName());
            binding.edtPhone.setText(customer.getPhone());
            binding.tvTitle.setText("Sửa khách hàng");
        }
        binding.btnCreate.setOnClickListener(v -> {
            String name = binding.edtName.getText().toString().trim();
            String phone = binding.edtPhone.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                binding.edtName.setError("Tên khách hàng không được để trống");
                binding.edtName.requestFocus();
            } else if (TextUtils.isEmpty(phone)) {
                binding.edtPhone.setError("Số điện thoại không được để trống");
                binding.edtPhone.requestFocus();
            } else if (phone.length() < 10) {
                binding.edtPhone.setError("Nhập đúng số điện thoại");
                binding.edtPhone.requestFocus();
            } else {
                if (listener != null) {
                    dismiss();
                    listener.onActionCustomer(name, phone);
                }
            }
        });
        binding.btnCancel.setOnClickListener(v -> dismiss());
    }
}
