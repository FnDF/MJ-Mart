package edu.mj.mart.activities.industry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import edu.mj.mart.R;
import edu.mj.mart.model.CI;

public class CIBottomSheetDialog extends BottomSheetDialogFragment {

    private final OnCIListener listener;

    private CI itemEdit;
    private String action;

    interface OnCIListener {
        void onOpt1(CI ci);

        void onOpt2(CI ci);
    }

    public CIBottomSheetDialog(OnCIListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ci_bottom_sheet_dialog, container, false);

        TextView btnOpt1 = v.findViewById(R.id.btnOpt1);
        TextView btnOpt2 = v.findViewById(R.id.btnOpt2);
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        TextView tvDescription = v.findViewById(R.id.tvDescription);

        if (getArguments() != null) {
            itemEdit = (CI) getArguments().getSerializable("key_ci");
            if (itemEdit == null) {
                return null;
            }
            action = getArguments().getString("key_action");
            if (!TextUtils.isEmpty(action) && action.equals("confirm")) {
                btnOpt1.setText("Sửa");
                btnOpt2.setText("Vô hiệu hoá");

                tvTitle.setVisibility(View.GONE);
                tvDescription.setVisibility(View.GONE);
            } else if (!TextUtils.isEmpty(action) && action.equals("delete")) {
                tvDescription.setText(getString(R.string.title_delete_ci, itemEdit.getName()));
                btnOpt1.setText("Xác nhận");
                btnOpt2.setText("Huỷ bỏ");

                tvTitle.setVisibility(View.VISIBLE);
                tvDescription.setVisibility(View.VISIBLE);
            }
        }

        btnOpt1.setOnClickListener(view -> {
            dismiss();
            if (listener != null) {
                listener.onOpt1(itemEdit);
            }
        });
        btnOpt2.setOnClickListener(view -> {
            dismiss();
            if (listener != null) {
                listener.onOpt2(itemEdit);
            }
        });
        return v;
    }

}
