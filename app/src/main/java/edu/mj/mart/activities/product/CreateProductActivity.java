package edu.mj.mart.activities.product;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityCreateProductBinding;
import edu.mj.mart.model.CI;
import edu.mj.mart.model.Product;
import edu.mj.mart.utils.Constants;
import edu.mj.mart.utils.ImageUtil;

public class CreateProductActivity extends BaseActivity<ActivityCreateProductBinding> {

    protected final ArrayList<CI> cis = new ArrayList<>();
    protected CI currentCI;
    protected final int PICK_IMAGE_REQUEST = 1001;
    protected Bitmap imageBitmap;

    @NonNull
    @Override
    protected ActivityCreateProductBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityCreateProductBinding.inflate(layoutInflater);
    }

    @Override
    public void setupView() {
        super.setupView();
        fetchData();
        binding.ivBack.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.layoutCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cis.isEmpty()) return;
                if (currentCI != null) {
                    cis.forEach(item -> {
                        item.setSelect(item.getId().equals(currentCI.getId()));
                    });
                }
                ListCIBottomSheetDialog dialog = new ListCIBottomSheetDialog(ci -> {
                    currentCI = ci;
                    binding.tvCI.setError(null);
                    binding.tvCI.setText(currentCI.getName());
                });
                Bundle bundle = new Bundle();
                bundle.putSerializable("cis", cis);
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "ListCI");
            }
        });
        binding.cardViewGalley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                createProduct(product);
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.DB_COLLECTION_CI)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.getResult() == null || task.getResult().isEmpty()) return;
                    cis.clear();
                    List<CI> cii = task.getResult().toObjects(CI.class);
                    for (int i = 0; i < cii.size(); i++) {
                        CI item = cii.get(i);
                        item.setId(task.getResult().getDocuments().get(i).getId());
                        cis.add(item);
                    }
                    if (currentCI != null) {
                        cis.forEach(item -> {
                            if (item.getId().equals(currentCI.getId())) {
                                currentCI.setName(item.getName());
                                binding.tvCI.setText(currentCI.getName());
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> {
                });
    }

    private void createProduct(Product product) {
        // check id product is exists
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.DB_COLLECTION_PRODUCTS)
                .whereEqualTo("id", product.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() == null || task.getResult().isEmpty()) {
                                // chưa tồn tại
                                db.collection(Constants.DB_COLLECTION_PRODUCTS)
                                        .add(product)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    binding.layoutLoading.setVisibility(View.GONE);
                                                    Toast.makeText(CreateProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                                    getOnBackPressedDispatcher().onBackPressed();
                                                } else {
                                                    binding.layoutLoading.setVisibility(View.GONE);
                                                    Toast.makeText(CreateProductActivity.this, "Đã có lỗi xảy ra! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                binding.layoutLoading.setVisibility(View.GONE);
                                                Toast.makeText(CreateProductActivity.this, "Đã có lỗi xảy ra! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                binding.layoutLoading.setVisibility(View.GONE);
                                Toast.makeText(CreateProductActivity.this, "Mã sản phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
                                binding.edtIdProduct.setError("Mã sản phẩm đã tồn tại");
                                binding.edtIdProduct.requestFocus();
                            }
                        } else {
                            binding.layoutLoading.setVisibility(View.GONE);
                            Toast.makeText(CreateProductActivity.this, "Đã có lỗi xảy ra! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.layoutLoading.setVisibility(View.GONE);
                        Toast.makeText(CreateProductActivity.this, "Đã có lỗi xảy ra! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                int height = 500 * imageBitmap.getHeight() / imageBitmap.getWidth();
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 500, height, false);
                binding.ivProduct.setImageBitmap(imageBitmap);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
