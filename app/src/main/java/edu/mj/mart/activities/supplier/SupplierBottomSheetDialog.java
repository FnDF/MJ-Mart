package edu.mj.mart.activities.supplier;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.Serializable;

import edu.mj.mart.databinding.SupplierBottomSheetDialogBinding;
import edu.mj.mart.model.Supplier;

public class SupplierBottomSheetDialog extends BottomSheetDialogFragment {

    private SupplierBottomSheetDialogBinding binding;

    private Supplier supplier;
    private SupplierAction action;
    private OnSupplierListener listener;

    interface OnSupplierListener {
        void onOpt1(Supplier ci);

        void onOpt2(Supplier ci);
    }

    public SupplierBottomSheetDialog(OnSupplierListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SupplierBottomSheetDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            action = (SupplierAction) getArguments().getSerializable("key_action");
            supplier = (Supplier) getArguments().getSerializable("key_supplier");
        }

        switch (action) {
            case CREATE:
                binding.tvTitle.setText("Thêm nhà cung cấp");
                binding.tvDescription.setVisibility(View.GONE);

                binding.btnOpt1.setText("Xác nhận");
                binding.btnOpt2.setText("Huỷ bỏ");
                break;
            case CONFIRM:
                binding.tvTitle.setVisibility(View.GONE);
                binding.tvDescription.setVisibility(View.GONE);
                binding.edtPhone.setVisibility(View.GONE);
                binding.edtSupplierName.setVisibility(View.GONE);

                binding.btnOpt1.setText("Sửa");
                binding.btnOpt2.setText("Vô hiệu hoá");
                break;
            case EDIT:
                binding.tvTitle.setText("Sửa nhà cung cấp");
                binding.tvDescription.setVisibility(View.GONE);

                if (supplier != null) {
                    binding.edtSupplierName.setText(supplier.getName());
                    binding.edtPhone.setText(supplier.getPhone());
                }

                binding.btnOpt1.setText("Xác nhận");
                binding.btnOpt2.setText("Huỷ bỏ");
                break;
            case DELETE:
                binding.tvTitle.setText("Nhà cung cấp");
                if (supplier != null) {
                    binding.tvDescription.setText("Bạn có chắc chắn muốn vô hiệu hoá Nhà cung cấp " + supplier.getName() + "?");
                }
                binding.edtPhone.setVisibility(View.GONE);
                binding.edtSupplierName.setVisibility(View.GONE);

                binding.btnOpt1.setText("Xác nhận");
                binding.btnOpt2.setText("Huỷ bỏ");
                break;
            default:
                break;
        }

        binding.btnOpt1.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                if (supplier == null) {
                    supplier = new Supplier();
                }
                supplier.setName(binding.edtSupplierName.getText().toString());
                supplier.setPhone(binding.edtPhone.getText().toString());
                listener.onOpt1(supplier);
            }
        });
        binding.btnOpt2.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                listener.onOpt2(supplier);
            }
        });
    }

    enum SupplierAction implements Serializable {
        CREATE, CONFIRM, EDIT, DELETE
    }
}
