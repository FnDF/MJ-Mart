package edu.mj.mart.activities.import_goods;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.mj.mart.base.BasePresenter;
import edu.mj.mart.model.ImportGoods;
import edu.mj.mart.model.ImportGoodsBill;
import edu.mj.mart.model.Product;
import edu.mj.mart.model.Supplier;
import edu.mj.mart.utils.Constants;

public class ImportGoodsPresenter extends BasePresenter<ImportGoodsView> {

    private Product currentProductIsImported;

    private ArrayList<ImportGoods> igs = new ArrayList<>();

    public ArrayList<ImportGoods> getIgs() {
        return igs;
    }

    private ArrayList<Supplier> suppliers = new ArrayList<>();

    protected ImportGoodsPresenter(FragmentActivity fragmentActivity, ImportGoodsView view) {
        super(fragmentActivity, view);
    }

    public void getSupplierDatabase() {
        db.collection(Constants.DB_COLLECTION_SUPPLIER)
                .get()
                .addOnCompleteListener(task -> {
                    if (mView == null) return;
                    if (!task.isSuccessful() || task.getResult() == null || task.getResult().isEmpty()) {
                        mView.onGetSupplierSuccess(Collections.emptyList());
                        return;
                    }
                    List<Supplier> sups = task.getResult().toObjects(Supplier.class);
                    for (int i = 0; i < sups.size(); i++) {
                        Supplier item = sups.get(i);
                        item.setId(task.getResult().getDocuments().get(i).getId());
                        suppliers.add(item);
                    }
                    mView.onGetSupplierSuccess(suppliers);
                })
                .addOnFailureListener(e -> {
                    mView.onGetSupplierSuccess(Collections.emptyList());
                });
    }

    public void getProductById(String idProduct) {
        db.collection(Constants.DB_COLLECTION_PRODUCTS)
                .whereEqualTo("id", idProduct)
                .get()
                .addOnCompleteListener(task -> {
                    if (mView == null) return;
                    if (task.isSuccessful()) {
                        if (task.getResult() == null || task.getResult().isEmpty()) {
                            currentProductIsImported = null;
                            mView.onGetProductByIdFailed("Không tìm thấy sản phẩm");
                        } else {
                            List<Product> pros = task.getResult().toObjects(Product.class);

                            Product item = pros.get(0);
                            item.setFirebaseId(task.getResult().getDocuments().get(0).getId());
                            currentProductIsImported = item;
                            mView.onGetProductByIdSuccess(item);
                        }
                    } else {
                        currentProductIsImported = null;
                        mView.onGetProductByIdFailed("Không tìm thấy sản phẩm");
                    }
                })
                .addOnFailureListener(e -> {
                    if (mView == null) return;
                    currentProductIsImported = null;
                    mView.onGetProductByIdFailed("Không tìm thấy sản phẩm");
                });
    }

    public Product getCurrentProductIsImported() {
        return currentProductIsImported;
    }

    public void addImportGoods(ImportGoods ig) {
        for (ImportGoods item : igs) {
            if (Objects.equals(item.getProduct().getId(), ig.getProduct().getId())) {
                mView.onAddProductIGFailed("Sản phẩm đã được nhập trước đó. Không thể nhập mới");
                return;
            }
        }
        igs.add(ig);
        mView.onAddProductIGSuccess(ig);
    }

    public void resetData() {
        currentProductIsImported = null;
    }

    public String getTotalAmount() {
        long total = 0;
        for (ImportGoods ig : igs) {
            total += ig.getProduct().getPurchasePrice() * ig.getQuantity();
        }
        return String.valueOf(total);
    }

    private int indexSupplier = -1;

    public void setSupplier(int index) {
        if (index < 0 || index >= suppliers.size()) return;
        indexSupplier = index;
    }

    public Supplier getSupplier() {
        try {
            return suppliers.get(indexSupplier);
        } catch (Exception exception) {
            return null;
        }
    }

    public void createImportGoods() {
        if (mView != null) {
            mView.showLoading();
        }
        db.collection(Constants.DB_COLLECTION_IMPORT_GOODS)
                .add(new ImportGoodsBill(getIgs(), getSupplier().getId()))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        if (mView == null) return;
                        mView.hideLoading();
                        if (TextUtils.isEmpty(documentReference.getId())) {
                            mView.onCreateBillImportFailed("Error");
                        } else {
                            mView.onCreateBillImportSuccess(documentReference.getId());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (mView == null) return;
                        mView.hideLoading();
                        mView.onCreateBillImportFailed(e.getMessage());
                    }
                });
    }
}
