package edu.mj.mart.activities.employee.edit;

import static edu.mj.mart.utils.SyntheticEnum.Role.MANAGER;
import static edu.mj.mart.utils.SyntheticEnum.Role.STAFF;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import edu.mj.mart.R;
import edu.mj.mart.model.Employee;

public class RoleBottomSheetDialog extends BottomSheetDialogFragment {

    private final Employee account;
    private final OnRoleSelectedListener listener;

    private int roleSelect;

    public RoleBottomSheetDialog(Employee account, OnRoleSelectedListener listener) {
        this.account = account;
        this.listener = listener;
    }

    public interface OnRoleSelectedListener {
        void onRoleSelected(int role);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.role_bottom_sheet_dialog, container, false);

        TextView staffView = v.findViewById(R.id.tvRoleStaff);
        TextView managerView = v.findViewById(R.id.tvRoleManager);

        if (account != null) {
            roleSelect = account.getRole();
            if (account.getRole() == STAFF.value) {
                staffView.setBackgroundResource(R.drawable.bg_item_selected_bottom_sheet);
                managerView.setBackgroundResource(R.drawable.bg_item_default_bottom_sheet);
            } else {
                staffView.setBackgroundResource(R.drawable.bg_item_default_bottom_sheet);
                managerView.setBackgroundResource(R.drawable.bg_item_selected_bottom_sheet);
            }
        }
        staffView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staffView.setBackgroundResource(R.drawable.bg_item_selected_bottom_sheet);
                managerView.setBackgroundResource(R.drawable.bg_item_default_bottom_sheet);
                roleSelect = STAFF.value;
            }
        });

        managerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                staffView.setBackgroundResource(R.drawable.bg_item_default_bottom_sheet);
                managerView.setBackgroundResource(R.drawable.bg_item_selected_bottom_sheet);
                roleSelect = MANAGER.value;
            }
        });

        TextView selectView = v.findViewById(R.id.btnSelect);
        selectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener != null) {
                    listener.onRoleSelected(roleSelect);
                }
                dismiss();
            }
        });

        return v;
    }

}
