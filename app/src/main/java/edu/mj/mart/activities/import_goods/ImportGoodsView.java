package edu.mj.mart.activities.import_goods;

import java.util.List;

import edu.mj.mart.base.BaseView;
import edu.mj.mart.model.ImportGoods;
import edu.mj.mart.model.Product;
import edu.mj.mart.model.Supplier;

public interface ImportGoodsView extends BaseView {

    void onGetProductByIdFailed(String msg);

    void onGetProductByIdSuccess(Product product);

    void onGetSupplierSuccess(List<Supplier> suppliers);

    void onAddProductIGSuccess(ImportGoods ig);

    void onAddProductIGFailed(String msg);

    void onCreateBillImportSuccess(String billId);

    void onCreateBillImportFailed(String msg);
}
