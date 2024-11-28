package edu.mj.mart.model;

import java.util.List;

public class ImportGoodsBill {

    private List<ImportGoods> importGoods;
    private String supplierId;

    public ImportGoodsBill(List<ImportGoods> importGoods, String supplierId) {
        this.importGoods = importGoods;
        this.supplierId = supplierId;
    }

    public List<ImportGoods> getImportGoods() {
        return importGoods;
    }

    public void setImportGoods(List<ImportGoods> importGoods) {
        this.importGoods = importGoods;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
