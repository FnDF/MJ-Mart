package edu.mj.mart.activities.industry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.mj.mart.R;
import edu.mj.mart.model.CI;
import edu.mj.mart.utils.Constants;

public class CreateCIBottomSheetDialog extends BottomSheetDialogFragment {

    private View layoutLoading;
    private EditText edtName;
    private TextView tvTitle;
    private final OnCIListener listener;

    private CI itemEdit;

    interface OnCIListener {
        void onCreateCISuccessfully();
    }

    public CreateCIBottomSheetDialog(OnCIListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_ci_bottom_sheet_dialog, container, false);

        View btnCreate = v.findViewById(R.id.btnCreate);
        View btnCancel = v.findViewById(R.id.btnCancel);
        layoutLoading = v.findViewById(R.id.layoutLoading);
        edtName = v.findViewById(R.id.edtName);
        tvTitle = v.findViewById(R.id.tvTitle);

        if (getArguments() != null) {
            itemEdit = (CI) getArguments().getSerializable("key_ci");
            if (itemEdit != null) {
                setItemEdit(itemEdit);
            }
        }
        btnCancel.setOnClickListener(view -> {
            layoutLoading.setVisibility(View.GONE);
            dismiss();
        });
        btnCreate.setOnClickListener(view -> {
            String name = edtName.getText().toString();
            if (TextUtils.isEmpty(name)) return;
            layoutLoading.setVisibility(View.VISIBLE);

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            if (itemEdit != null) {
                Map<String, Object> rs = new HashMap<>();
                rs.put("name", name);

                db.collection(Constants.DB_COLLECTION_CI)
                        .document(itemEdit.getId())
                        .set(rs)
                        .addOnCompleteListener(task -> {
                            layoutLoading.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), "Sửa ngành hàng thành công", Toast.LENGTH_SHORT).show();
                            if (listener != null) {
                                listener.onCreateCISuccessfully();
                            }
                            dismiss();
                        })
                        .addOnFailureListener(e -> {
                            layoutLoading.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), "Sửa ngành hàng thất bại! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        });
            } else {
                db.collection(Constants.DB_COLLECTION_CI)
                        .add(new CI(name))
                        .addOnCompleteListener(task -> {
                            layoutLoading.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), "Thêm ngành hàng thành công", Toast.LENGTH_SHORT).show();
                            if (listener != null) {
                                listener.onCreateCISuccessfully();
                            }
                            dismiss();
                        })
                        .addOnFailureListener(e -> {
                            layoutLoading.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), "Thêm ngành hàng thất bại! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        });
            }
        });
        return v;
    }

    private void setItemEdit(CI ci) {
        this.itemEdit = ci;
        if (edtName != null) {
            edtName.setText(ci.getName());
        }
        if (tvTitle != null) {
            tvTitle.setText(getString(R.string.edit_ci));
        }
    }

}
