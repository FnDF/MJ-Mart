package edu.mj.mart.model;

public class ImportGoods {

    private Product product;
    private int quantity;
    private String endDate;
    private String importDay;

    public ImportGoods(Product product, int quantity, String endDate, String importDay) {
        this.product = product;
        this.quantity = quantity;
        this.endDate = endDate;
        this.importDay = importDay;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImportDay() {
        return importDay;
    }

    public void setImportDay(String importDay) {
        this.importDay = importDay;
    }
}
