package edu.mj.mart.activities.supplier;

import static edu.mj.mart.utils.SyntheticEnum.Role.STAFF;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.mj.mart.activities.industry.CommodityIndustryActivity;
import edu.mj.mart.adapter.OnListenerItem;
import edu.mj.mart.adapter.SupplierAdapter;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivitySupplierBinding;
import edu.mj.mart.model.Supplier;
import edu.mj.mart.utils.Constants;

public class SupplierActivity extends BaseActivity<ActivitySupplierBinding> {

    private final ArrayList<Supplier> suppliers = new ArrayList<>();
    private final ArrayList<Supplier> suppliersFilter = new ArrayList<>();

    private SupplierAdapter supplierAdapter;

    private SupplierBottomSheetDialog createDialog;
    private SupplierBottomSheetDialog confirmDialog;
    private SupplierBottomSheetDialog editDialog;
    private SupplierBottomSheetDialog deleteDialog;

    @NonNull
    @Override
    protected ActivitySupplierBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivitySupplierBinding.inflate(layoutInflater);
    }

    @Override
    public void setupView() {
        super.setupView();
        supplierAdapter = new SupplierAdapter(suppliers, new OnListenerItem<Supplier>() {
            @Override
            public void onClickItem(Supplier item) {
                confirmDialog = new SupplierBottomSheetDialog(new SupplierBottomSheetDialog.OnSupplierListener() {
                    @Override
                    public void onOpt1(Supplier supplier) {
                        editDialog = new SupplierBottomSheetDialog(new SupplierBottomSheetDialog.OnSupplierListener() {
                            @Override
                            public void onOpt1(Supplier supplier) {
                                editSupplier(supplier);
                            }

                            @Override
                            public void onOpt2(Supplier supplier) {

                            }
                        });
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("key_action", SupplierBottomSheetDialog.SupplierAction.EDIT);
                        bundle.putSerializable("key_supplier", item);
                        editDialog.setArguments(bundle);
                        editDialog.show(getSupportFragmentManager(), "EditSupplierBottomSheetDialog");
                    }

                    @Override
                    public void onOpt2(Supplier supplier) {
                        deleteDialog = new SupplierBottomSheetDialog(new SupplierBottomSheetDialog.OnSupplierListener() {
                            @Override
                            public void onOpt1(Supplier supplier) {
                                deleteSupplier(supplier);
                            }

                            @Override
                            public void onOpt2(Supplier supplier) {

                            }
                        });
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("key_action", SupplierBottomSheetDialog.SupplierAction.DELETE);
                        bundle.putSerializable("key_supplier", item);
                        deleteDialog.setArguments(bundle);
                        deleteDialog.show(getSupportFragmentManager(), "DeleteSupplierBottomSheetDialog");
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putSerializable("key_action", SupplierBottomSheetDialog.SupplierAction.CONFIRM);
                confirmDialog.setArguments(bundle);
                confirmDialog.show(getSupportFragmentManager(), "ConfirmSupplierBottomSheetDialog");
            }

            @Override
            public void onCallSupplier(Supplier supplier) {
                if (supplier != null && !TextUtils.isEmpty(supplier.getPhone())) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", supplier.getPhone(), null));
                    startActivity(intent);
                }
            }
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
            createDialog = new SupplierBottomSheetDialog(new SupplierBottomSheetDialog.OnSupplierListener() {
                @Override
                public void onOpt1(Supplier supplier) {
                    createSupplier(supplier);
                }

                @Override
                public void onOpt2(Supplier supplier) {

                }
            });
            Bundle bundle = new Bundle();
            bundle.putSerializable("key_action", SupplierBottomSheetDialog.SupplierAction.CREATE);
            createDialog.setArguments(bundle);
            createDialog.show(getSupportFragmentManager(), "SupplierBottomSheetDialog");
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(supplierAdapter);
        fetchData();
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.DB_COLLECTION_SUPPLIER)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.getResult() == null || task.getResult().isEmpty()) return;
                    suppliers.clear();
                    suppliersFilter.clear();
                    List<Supplier> sups = task.getResult().toObjects(Supplier.class);
                    for (int i = 0; i < sups.size(); i++) {
                        Supplier item = sups.get(i);
                        item.setId(task.getResult().getDocuments().get(i).getId());
                        suppliers.add(item);
                    }
                    supplierAdapter.setDataSource(suppliers);
                })
                .addOnFailureListener(e -> {
                });
    }

    private void performSearch(String keySearch) {
        hideKeyBoard();
        suppliersFilter.clear();
        if (keySearch.isEmpty()) {
            suppliersFilter.addAll(suppliers);
        } else {
            for (Supplier supplier : suppliers) {
                if (supplier.getName().toLowerCase().contains(keySearch.toLowerCase())) {
                    suppliersFilter.add(supplier);
                }
            }
        }
        supplierAdapter.setDataSource(suppliersFilter);
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.edtSearch.getWindowToken(), 0);
    }

    private void createSupplier(Supplier supplier) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.DB_COLLECTION_SUPPLIER)
                .add(supplier)
                .addOnCompleteListener(task -> {
                    binding.layoutLoading.setVisibility(View.GONE);
                    if (createDialog != null) {
                        createDialog.dismiss();
                    }
                    Toast.makeText(this, "Thêm nhà cung cấp thành công", Toast.LENGTH_SHORT).show();
                    fetchData();
                })
                .addOnFailureListener(e -> {
                    binding.layoutLoading.setVisibility(View.GONE);
                    Toast.makeText(this, "Thêm nhà cung cấp thất bại! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                });
    }

    private void editSupplier(Supplier supplier) {
        binding.layoutLoading.setVisibility(View.VISIBLE);
        Map<String, Object> rs = new HashMap<>();
        rs.put("name", supplier.getName());
        rs.put("phone", supplier.getPhone());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.DB_COLLECTION_SUPPLIER).document(supplier.getId()).set(rs).addOnCompleteListener(task -> {
            binding.layoutLoading.setVisibility(View.GONE);
            if (editDialog != null) {
                editDialog.dismiss();
            }
            Toast.makeText(this, "Sửa nhà cung cấp thành công", Toast.LENGTH_SHORT).show();
            fetchData();
        }).addOnFailureListener(e -> {
            binding.layoutLoading.setVisibility(View.GONE);
            Toast.makeText(this, "Sửa nhà cung cấp thất bại! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
        });
    }

    private void deleteSupplier(Supplier supplier) {
        binding.layoutLoading.setVisibility(View.VISIBLE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.DB_COLLECTION_SUPPLIER)
                .document(supplier.getId())
                .delete()
                .addOnCompleteListener(task -> {
                    binding.layoutLoading.setVisibility(View.GONE);
                    Toast.makeText(this, "Vô hiệu hoá thành công!", Toast.LENGTH_SHORT).show();
                    fetchData();
                })
                .addOnFailureListener(e -> {
                    binding.layoutLoading.setVisibility(View.GONE);
                    Toast.makeText(this, "Đã có lỗi xảy ra! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                });
    }
}
