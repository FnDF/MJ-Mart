package edu.mj.mart.activities.industry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.mj.mart.R;
import edu.mj.mart.model.CI;
import edu.mj.mart.utils.Constants;

public class CreateCIBottomSheetDialog extends BottomSheetDialogFragment {

    private View layoutLoading;
    private EditText edtName;
    private final OnCIListener listener;

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

        btnCancel.setOnClickListener(view -> {
            layoutLoading.setVisibility(View.GONE);
            dismiss();
        });
        btnCreate.setOnClickListener(view -> {
            String name = edtName.getText().toString();
            if (TextUtils.isEmpty(name)) return;
            layoutLoading.setVisibility(View.VISIBLE);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                        Toast.makeText(requireContext(), "Thêm ngành hàng thất bại! Vui lòng thử lại", Toast.LENGTH_SHORT).show()
                    });
        });
        return v;
    }

}
