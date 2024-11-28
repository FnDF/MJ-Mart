package edu.mj.mart.activities.import_goods;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import java.util.Calendar;
import java.util.List;

import edu.mj.mart.R;
import edu.mj.mart.adapter.IGAdapter;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityImportGoodsBinding;
import edu.mj.mart.model.ImportGoods;
import edu.mj.mart.model.Product;
import edu.mj.mart.model.Supplier;
import edu.mj.mart.utils.Constants;

public class ImportGoodsActivity extends BaseActivity<ActivityImportGoodsBinding> implements ImportGoodsView {

    private ImportGoodsPresenter presenter;
    Calendar myCalendarEndDate = Calendar.getInstance();
    Calendar myCalendarDayImport = Calendar.getInstance();
    private IGAdapter igAdapter;

    @NonNull
    @Override
    protected ActivityImportGoodsBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityImportGoodsBinding.inflate(layoutInflater);
    }

    @Override
    public void setupView() {
        super.setupView();
        presenter = new ImportGoodsPresenter(ImportGoodsActivity.this, this);
        binding.ivBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        binding.ivBackReport.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        presenter.getSupplierDatabase();
        binding.spinnerSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int index = arg2 - 1;
                presenter.setSupplier(index);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        igAdapter = new IGAdapter(presenter.getIgs());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(igAdapter);

        binding.edtIdProduct.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                getProductById();
            }
        });
        binding.edtIdProduct.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard();
                getProductById();
            }
            return false;
        });
        binding.tvEndDateProduct.setOnClickListener(v -> {
            showDatePickerEndDate();
        });
        binding.tvDay.setOnClickListener(v -> {
            showDatePickerImport();
        });
        binding.edtQuantityProduct.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard();
            }
            return false;
        });
        binding.ivScanBarcode.setOnClickListener(v -> {
            GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE, Barcode.FORMAT_AZTEC)
                    .enableAutoZoom()
                    .build();
            GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this, options);
            scanner.startScan().addOnSuccessListener(barcode -> {
                binding.edtIdProduct.setText(barcode.getRawValue());
                getProductById();
            }).addOnCanceledListener(() -> {
                // Task canceled
            }).addOnFailureListener(e -> {
                // Task failed with an exception
            });
        });
        binding.btnAddProduct.setOnClickListener(v -> {
            Product product = presenter.getCurrentProductIsImported();
            if (product == null) {
                binding.edtIdProduct.setError("Vui lòng nhập đúng mã sản phẩm");
                binding.edtIdProduct.requestFocus();
                return;
            }
            String mQuantity = binding.edtQuantityProduct.getText().toString().trim();
            int quantity = -1;
            try {
                quantity = Integer.parseInt(mQuantity);
            } catch (Exception exception) {
                binding.edtQuantityProduct.setError("Vui lòng nhập số lượng");
                binding.edtQuantityProduct.requestFocus();
                return;
            }
            if (quantity <= 0) {
                binding.edtQuantityProduct.setError("Vui lòng nhập số lượng lớn hơn 0");
                binding.edtQuantityProduct.requestFocus();
                return;
            }
            String mEndDate = binding.tvEndDateProduct.getText().toString().trim();
            if (TextUtils.isEmpty(mEndDate)) {
                binding.tvEndDateProduct.setError("Vui lòng nhập hạn sử dụng");
                binding.tvEndDateProduct.requestFocus();
                return;
            }
            String mDay = binding.tvDay.getText().toString().trim();
            if (TextUtils.isEmpty(mDay)) {
                binding.tvDay.setError("Vui lòng nhập ngày nhập");
                binding.tvDay.requestFocus();
                return;
            }
            hideKeyboard();
            ImportGoods ig = new ImportGoods(product, quantity, mEndDate, mDay);
            presenter.addImportGoods(ig);
        });
        binding.btnDone.setOnClickListener(v -> {
            if (presenter.getIgs().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập sản phẩm", Toast.LENGTH_SHORT).show();
                return;
            }
            if (presenter.getSupplier() == null) {
                Toast.makeText(this, "Vui lòng chọn nhà cung cấp", Toast.LENGTH_SHORT).show();
                return;
            }

            presenter.createImportGoods();
        });
        setDayImport();
    }

    private void showDatePickerEndDate() {
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendarEndDate.set(Calendar.YEAR, year);
            myCalendarEndDate.set(Calendar.MONTH, month);
            myCalendarEndDate.set(Calendar.DAY_OF_MONTH, day);
            setEndDate();
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, date, myCalendarEndDate.get(Calendar.YEAR), myCalendarEndDate.get(Calendar.MONTH), myCalendarEndDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void showDatePickerImport() {
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendarDayImport.set(Calendar.YEAR, year);
            myCalendarDayImport.set(Calendar.MONTH, month);
            myCalendarDayImport.set(Calendar.DAY_OF_MONTH, day);
            setDayImport();
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, date, myCalendarDayImport.get(Calendar.YEAR), myCalendarDayImport.get(Calendar.MONTH), myCalendarDayImport.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void setEndDate() {
        int year = myCalendarEndDate.get(Calendar.YEAR);
        int month = myCalendarEndDate.get(Calendar.MONTH);
        int day = myCalendarEndDate.get(Calendar.DAY_OF_MONTH);

        String mDay = String.valueOf(day);
        if (mDay.length() == 1) {
            mDay = "0" + mDay;
        }
        String mMonth = String.valueOf(month + 1);
        if (mMonth.length() == 1) {
            mMonth = "0" + mMonth;
        }
        String dayFinal = mDay + "/" + mMonth + "/" + year;
        binding.tvEndDateProduct.setTextColor(ContextCompat.getColor(this, R.color.black));
        binding.tvEndDateProduct.setText(dayFinal);
        binding.tvEndDateProduct.setError(null);
    }

    private void setDayImport() {
        int year = myCalendarDayImport.get(Calendar.YEAR);
        int month = myCalendarDayImport.get(Calendar.MONTH);
        int day = myCalendarDayImport.get(Calendar.DAY_OF_MONTH);

        String mDay = String.valueOf(day);
        if (mDay.length() == 1) {
            mDay = "0" + mDay;
        }
        String mMonth = String.valueOf(month + 1);
        if (mMonth.length() == 1) {
            mMonth = "0" + mMonth;
        }
        String dayFinal = mDay + "/" + mMonth + "/" + year;
        binding.tvDay.setTextColor(ContextCompat.getColor(this, R.color.black));
        binding.tvDay.setText(dayFinal);
        binding.tvDay.setError(null);
    }

    private void getProductById() {
        String id = binding.edtIdProduct.getText().toString().trim();
        if (TextUtils.isEmpty(id)) return;
        presenter.getProductById(id);
    }

    @Override
    public void onGetProductByIdFailed(String msg) {
        binding.edtIdProduct.setError(msg);
        binding.edtNameProduct.setText("");
        binding.edtPriceProduct.setText("");
    }

    @Override
    public void onGetProductByIdSuccess(Product product) {
        binding.edtNameProduct.setText(product.getName());
        binding.edtPriceProduct.setText(String.valueOf(product.getPurchasePrice()));
    }

    @Override
    public void onGetSupplierSuccess(List<Supplier> suppliers) {
        String[] items = new String[suppliers.size() + 1];
        items[0] = "Chọn nhà cung cấp";
        for (int i = 0; i < suppliers.size(); i++) {
            items[i + 1] = suppliers.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.spinnerSupplier.setAdapter(adapter);
    }

    @Override
    public void showLoading() {
        binding.layoutLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.layoutLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.edtPriceProduct.getWindowToken(), 0);
    }

    @Override
    public void onAddProductIGFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddProductIGSuccess(ImportGoods ig) {
        resetData();
        Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
        igAdapter.setDataSource(presenter.getIgs());
        binding.tvTotal.setText("Tổng tiền: " + presenter.getTotalAmount() + " VNĐ");
    }

    private void resetData() {
        presenter.resetData();

        binding.edtIdProduct.setText("");
        binding.edtIdProduct.setError(null);
        binding.edtIdProduct.requestFocus();

        binding.edtNameProduct.setText("");
        binding.edtNameProduct.setError(null);

        binding.edtPriceProduct.setText("");
        binding.edtPriceProduct.setError(null);

        binding.edtQuantityProduct.setText("");
        binding.edtQuantityProduct.setError(null);

        binding.tvEndDateProduct.setText("");
        binding.tvEndDateProduct.setError(null);

        myCalendarEndDate = Calendar.getInstance();
        myCalendarDayImport = Calendar.getInstance();
        setDayImport();
    }

    @Override
    public void onCreateBillImportFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateBillImportSuccess(String billId) {
        Toast.makeText(this, "Nhập hàng thành công", Toast.LENGTH_SHORT).show();
        // show màn hình detail
        binding.layoutInput.setVisibility(View.GONE);
        binding.layoutReport.setVisibility(View.VISIBLE);

        binding.tvBillId.setText("ID: " + billId);
        binding.tvUser.setText("Nhân viên: " + Constants.currentAccount.getFullName());

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (day.length() == 1) {
            day = "0" + day;
        }
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (month.length() == 1) {
            month = "0" + month;
        }
        int year = calendar.get(Calendar.YEAR);
        binding.tvCreateBill.setText("Ngày tạo: " + hour + ":" + minute + " " + day + "/" + month + "/" + year);

        binding.tvSupplierName.setText("Nhà cung cấp: " + presenter.getSupplier().getName());
        binding.recyclerViewReport.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewReport.setAdapter(igAdapter);
        binding.tvTotalReport.setText("Tổng tiền: " + presenter.getTotalAmount() + " VNĐ");
    }
}