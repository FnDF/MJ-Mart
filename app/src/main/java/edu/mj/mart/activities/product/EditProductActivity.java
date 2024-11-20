package edu.mj.mart.activities.product;

import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import edu.mj.mart.R;
import edu.mj.mart.model.CI;
import edu.mj.mart.model.Product;
import edu.mj.mart.utils.Constants;
import edu.mj.mart.utils.ImageUtil;

public class EditProductActivity extends CreateProductActivity {

    private Product product;

    @Override
    public void setupView() {
        super.setupView();
        binding.tvHeader.setText("Sửa sản phẩm");
        binding.btnCreate.setText("Lưu");

        product = (Product) getIntent().getSerializableExtra("product");

        if (product == null) {
            getOnBackPressedDispatcher().onBackPressed();
            return;
        }

        binding.edtIdProduct.setText(product.getId());
        binding.edtIdProduct.setEnabled(false);
        binding.edtNameProduct.setText(product.getName());
        binding.edtPurchasePrice.setText(String.valueOf(product.getPurchasePrice()));
        binding.edtSellingPrice.setText(String.valueOf(product.getSellingPrice()));
        binding.edtDescription.setText(product.getDescription());

        if (!product.getImages().isEmpty()) {
            StringBuilder ima = new StringBuilder();
            for (String s : product.getImages()) {
                ima.append(s);
            }
            if (ima.toString().isEmpty()) {
                binding.ivProduct.setImageResource(R.drawable.avatar_default);
            } else {
                binding.ivProduct.setImageBitmap(ImageUtil.decode(ima.toString()));
            }
        }
        currentCI = new CI();
        currentCI.setId(product.getCiId());

        binding.btnCreate.setOnClickListener(view -> {
            String id = binding.edtIdProduct.getText().toString().trim();
            String name = binding.edtNameProduct.getText().toString().trim();
            String purchasePrice = binding.edtPurchasePrice.getText().toString().trim();
            String sellingPrice = binding.edtSellingPrice.getText().toString().trim();

            if (id.isEmpty()) {
                binding.edtIdProduct.setError("Không được để trống");
                binding.edtIdProduct.requestFocus();
                return;
            }
            if (name.isEmpty()) {
                binding.edtNameProduct.setError("Không được để trống");
                binding.edtNameProduct.requestFocus();
                return;
            }
            if (currentCI == null) {
                binding.tvCI.setError("Không được để trống");
                return;
            }
            if (purchasePrice.isEmpty()) {
                binding.edtPurchasePrice.setError("Không được để trống");
                binding.edtPurchasePrice.requestFocus();
                return;
            }
            if (sellingPrice.isEmpty()) {
                binding.edtSellingPrice.setError("Không được để trống");
                binding.edtSellingPrice.requestFocus();
                return;
            }
            binding.layoutLoading.setVisibility(View.VISIBLE);

            ArrayList<String> image = null;
            if (imageBitmap != null) {
                String base64 = ImageUtil.convert(imageBitmap);
                int length = base64.length();

                image = new ArrayList<>();
                image.add(base64.substring(0, length / 2));
                image.add(base64.substring(length / 2));
            } else if (product.getImages() != null) {
                image = new ArrayList<>(product.getImages());
            }

            Product product = new Product(
                    id,
                    name,
                    currentCI.getId(),
                    Long.parseLong(purchasePrice),
                    Long.parseLong(sellingPrice),
                    binding.edtDescription.getText().toString().trim(),
                    image
            );
            updateProduct(product);
        });
    }

    private void updateProduct(Product product) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.DB_COLLECTION_USERS)
                .document(product.getFirebaseId())
                .set(product)
                .addOnSuccessListener(aVoid -> {
                    binding.layoutLoading.setVisibility(View.GONE);
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Constants.updateCurrentProduct(product);
                    getOnBackPressedDispatcher().onBackPressed();
                })
                .addOnFailureListener(e -> {
                    binding.layoutLoading.setVisibility(View.GONE);
                    Toast.makeText(this, "Cập nhật thất bại! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                });
    }
}
