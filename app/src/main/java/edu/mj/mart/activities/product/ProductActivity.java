package edu.mj.mart.activities.product;

import static edu.mj.mart.utils.SyntheticEnum.Role.MANAGER;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import edu.mj.mart.adapter.ProductAdapter;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityProductBinding;
import edu.mj.mart.model.CI;
import edu.mj.mart.model.Product;
import edu.mj.mart.utils.Constants;

public class ProductActivity extends BaseActivity<ActivityProductBinding> {

    private final ArrayList<Product> products = new ArrayList<>();
    private final ArrayList<Product> productsFilter = new ArrayList<>();
    private ProductAdapter productAdapter;

    @NonNull
    @Override
    protected ActivityProductBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityProductBinding.inflate(layoutInflater);
    }

    @Override
    public void setupView() {
        super.setupView();
        productAdapter = new ProductAdapter(products, item -> {
            Intent intent = new Intent(ProductActivity.this, DetailProductActivity.class);
            intent.putExtra("product", item);
            startActivity(intent);
        });
        binding.edtSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(binding.edtSearch.getText().toString().trim());
                return true;
            }
            return false;
        });
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(productAdapter);

        binding.ivBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        binding.cardViewCreateProduct.setOnClickListener(view -> {
            if (Constants.currentAccount != null && Constants.currentAccount.getRole() == MANAGER.value) {
                startActivity(new Intent(this, CreateProductActivity.class));
            } else {
                Toast.makeText(this, "Bạn không có đủ quyền hạn cần thiết", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performSearch(String keySearch) {
        hideKeyBoard();
        productsFilter.clear();
        if (keySearch.isEmpty()) {
            productsFilter.addAll(products);
        } else {
            for (Product product : products) {
                if (product.getName().toLowerCase().contains(keySearch.toLowerCase()) || product.getId().contains(keySearch.toLowerCase())) {
                    productsFilter.add(product);
                }
            }
        }
        productAdapter.setDataSource(productsFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData() {
        binding.layoutLoading.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.DB_COLLECTION_PRODUCTS)
                .get()
                .addOnCompleteListener(task -> {
                    binding.layoutLoading.setVisibility(View.GONE);
                    if (task.getResult() == null || task.getResult().isEmpty()) {
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        return;
                    }
                    binding.tvEmpty.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);

                    products.clear();
                    productsFilter.clear();

                    List<Product> pros = task.getResult().toObjects(Product.class);
                    for (int i = 0; i < pros.size(); i++) {
                        Product item = pros.get(i);
                        item.setFirebaseId(task.getResult().getDocuments().get(i).getId());
                        products.add(item);
                    }

                    productAdapter.setDataSource(products);
                })
                .addOnFailureListener(e -> {
                    binding.layoutLoading.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE);
                });
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.edtSearch.getWindowToken(), 0);
    }
}
