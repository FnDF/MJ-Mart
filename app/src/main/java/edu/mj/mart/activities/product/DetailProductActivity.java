package edu.mj.mart.activities.product;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.mj.mart.R;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityDetailProductBinding;
import edu.mj.mart.model.CI;
import edu.mj.mart.model.Product;
import edu.mj.mart.utils.Constants;
import edu.mj.mart.utils.ImageUtil;

public class DetailProductActivity extends BaseActivity<ActivityDetailProductBinding> {

    private Product product;
    protected final ArrayList<CI> cis = new ArrayList<>();

    @NonNull
    @Override
    protected ActivityDetailProductBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityDetailProductBinding.inflate(layoutInflater);
    }

    @Override
    public void setupView() {
        super.setupView();
        fetchData();
        product = (Product) getIntent().getSerializableExtra("product");
        binding.ivBack.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.cardViewEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailProductActivity.this, EditProductActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });
        Constants.getCurrentProductLiveData().observe(this, product -> {
            this.product = product;
            showDetailProduct();
        });
        showDetailProduct();
    }

    private void showDetailProduct() {
        if (product == null) {
            getOnBackPressedDispatcher().onBackPressed();
            return;
        }
        binding.tvId.setText("Mã sp: " + product.getId());
        binding.tvName.setText("Tên sp: " + product.getName());
        binding.tvPurchasePrice.setText("Giá nhập: " + product.getPurchasePrice() + " VNĐ");
        binding.tvSellingPrice.setText("Giá bán: " + product.getSellingPrice() + " VNĐ");
        binding.tvDescription.setText("Mô tả: " + product.getDescription());
        binding.tvNameCI.setText("Ngành hàng: " + product.getCiId());

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

        genCode();
    }

    private void genCode() {
        new Thread(() -> {
            int qrWidth = 480;
            int qrHeight = 480;

            QRCodeWriter writer = new QRCodeWriter();
            try {
                Map<EncodeHintType, Object> hintMap = new HashMap<>();
                hintMap.put(EncodeHintType.MARGIN, 0);
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

                BitMatrix bitMatrix = writer.encode(product.getId(), BarcodeFormat.QR_CODE, qrWidth, qrHeight, hintMap);
                Bitmap bmp = Bitmap.createBitmap(qrWidth, qrHeight, Bitmap.Config.RGB_565);

                int white = getResources().getColor(R.color.white, getTheme());
                int black = getResources().getColor(R.color.black, getTheme());

                for (int i = 0; i < qrWidth; i++) {
                    for (int j = 0; j < qrHeight; j++) {
                        bmp.setPixel(i, j, (bitMatrix.get(i, j) ? white : black));
                    }
                }

                Bitmap bmCombined = Bitmap.createBitmap(qrWidth, qrHeight, Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(bmCombined);
                canvas.drawBitmap(bmp, new Matrix(), null);

                runOnUiThread(() -> {
                    binding.ivBarcode.setImageBitmap(bmCombined);
                });
            } catch (WriterException exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.DB_COLLECTION_CI).get().addOnCompleteListener(task -> {
            if (task.getResult() == null || task.getResult().isEmpty()) return;
            cis.clear();
            List<CI> cii = task.getResult().toObjects(CI.class);
            for (int i = 0; i < cii.size(); i++) {
                CI item = cii.get(i);
                item.setId(task.getResult().getDocuments().get(i).getId());
                cis.add(item);
            }

            if (product != null) {
                cis.forEach(item -> {
                    if (item.getId().equals(product.getCiId())) {
                        binding.tvNameCI.setText("Ngành hàng: " + item.getName());
                    }
                });
            }
        }).addOnFailureListener(e -> {
        });
    }
}
