package edu.mj.mart.activities.employee.edit;

import static edu.mj.mart.utils.SyntheticEnum.StatusEmployee.ACTIVE;
import static edu.mj.mart.utils.SyntheticEnum.StatusEmployee.DEACTIVATE;

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

public class ActiveBottomSheetDialog extends BottomSheetDialogFragment {

    private final Employee account;
    private final OnActiveSelectedListener listener;

    private int activeSelect;

    public ActiveBottomSheetDialog(Employee account, OnActiveSelectedListener listener) {
        this.account = account;
        this.listener = listener;
    }

    public interface OnActiveSelectedListener {
        void onActiveSelected(int active);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.active_bottom_sheet_dialog, container, false);

        TextView activeView = v.findViewById(R.id.tvActive);
        TextView deactivateView = v.findViewById(R.id.tvDeactivate);

        if (account != null) {
            activeSelect = account.getActive();
            if (account.getActive() == ACTIVE.value) {
                activeView.setBackgroundResource(R.drawable.bg_item_selected_bottom_sheet);
                deactivateView.setBackgroundResource(R.drawable.bg_item_default_bottom_sheet);
            } else {
                activeView.setBackgroundResource(R.drawable.bg_item_default_bottom_sheet);
                deactivateView.setBackgroundResource(R.drawable.bg_item_selected_bottom_sheet);
            }
        }
        activeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeView.setBackgroundResource(R.drawable.bg_item_selected_bottom_sheet);
                deactivateView.setBackgroundResource(R.drawable.bg_item_default_bottom_sheet);
                activeSelect = ACTIVE.value;
            }
        });

        deactivateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeView.setBackgroundResource(R.drawable.bg_item_default_bottom_sheet);
                deactivateView.setBackgroundResource(R.drawable.bg_item_selected_bottom_sheet);
                activeSelect = DEACTIVATE.value;
            }
        });

        TextView selectView = v.findViewById(R.id.btnSelect);
        selectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener != null) {
                    listener.onActiveSelected(activeSelect);
                }
                dismiss();
            }
        });

        return v;
    }

}
