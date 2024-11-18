package edu.mj.mart.activities.industry;

import static edu.mj.mart.utils.SyntheticEnum.Role.STAFF;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.mj.mart.adapter.CIAdapter;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityCommodityIndustryBinding;
import edu.mj.mart.model.CI;
import edu.mj.mart.utils.Constants;

public class CommodityIndustryActivity extends BaseActivity<ActivityCommodityIndustryBinding> {

    private CIAdapter ciAdapter;
    private final ArrayList<CI> cis = new ArrayList<>();
    private final ArrayList<CI> cisFilter = new ArrayList<>();

    Bundle bundle = new Bundle();

    @NonNull
    @Override
    protected ActivityCommodityIndustryBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityCommodityIndustryBinding.inflate(layoutInflater);
    }

    @Override
    public void setupView() {
        super.setupView();
        ciAdapter = new CIAdapter(cis, item -> {
            bundle.putString("key_action", "confirm");
            bundle.putSerializable("key_ci", item);
            CIBottomSheetDialog dialog = new CIBottomSheetDialog(new CIBottomSheetDialog.OnCIListener() {
                @Override
                public void onOpt1(CI ci) {
                    CreateCIBottomSheetDialog dialogEdit = new CreateCIBottomSheetDialog(CommodityIndustryActivity.this::fetchData);
                    dialogEdit.setArguments(bundle);
                    dialogEdit.show(getSupportFragmentManager(), "CreateCIBottomSheetDialog");
                }

                @Override
                public void onOpt2(CI ci) {
                    bundle.putString("key_action", "delete");
                    CIBottomSheetDialog dialog = new CIBottomSheetDialog(new CIBottomSheetDialog.OnCIListener() {
                        @Override
                        public void onOpt1(CI ci) {
                            // xác nhận xoá
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection(Constants.DB_COLLECTION_CI)
                                    .document(ci.getId())
                                    .delete()
                                    .addOnCompleteListener(task -> {
                                        Toast.makeText(CommodityIndustryActivity.this, "Vô hiệu hoá thành công!", Toast.LENGTH_SHORT).show();
                                        fetchData();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(CommodityIndustryActivity.this, "Đã có lỗi xảy ra! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                                    });
                        }

                        @Override
                        public void onOpt2(CI ci) {

                        }
                    });
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(), "CIBottomSheetDialog");
                }
            });
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "CIBottomSheetDialog");
        });
        binding.ivBack.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.edtSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(binding.edtSearch.getText().toString().trim());
                return true;
            }
            return false;
        });
        binding.cardViewCreateCI.setOnClickListener(view -> {
            if (Constants.currentAccount != null) {
                if (Constants.currentAccount.getRole() == STAFF.value) {
                    Toast.makeText(this, "Bạn không có đủ quyền hạn cần thiết", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            CreateCIBottomSheetDialog dialog = new CreateCIBottomSheetDialog(this::fetchData);
            dialog.show(getSupportFragmentManager(), "CreateCIBottomSheetDialog");
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(ciAdapter);
        fetchData();
    }

    private void performSearch(String keySearch) {
        hideKeyBoard();
        cisFilter.clear();
        if (keySearch.isEmpty()) {
            cisFilter.addAll(cis);
        } else {
            for (CI ci : cis) {
                if (ci.getName().contains(keySearch)) {
                    cisFilter.add(ci);
                }
            }
        }
        ciAdapter.setDataSource(cisFilter);
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.edtSearch.getWindowToken(), 0);
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.DB_COLLECTION_CI)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.getResult() == null || task.getResult().isEmpty()) return;
                    cis.clear();
                    cisFilter.clear();
                    List<CI> cii = task.getResult().toObjects(CI.class);
                    for (int i = 0; i < cii.size(); i++) {
                        CI item = cii.get(i);
                        item.setId(task.getResult().getDocuments().get(i).getId());
                        cis.add(item);
                    }
                    ciAdapter.setDataSource(cis);
                })
                .addOnFailureListener(e -> {
                });
    }
}
