package edu.mj.mart.activities.customer;

import static edu.mj.mart.utils.Constants.CUSTOMER_NAME;
import static edu.mj.mart.utils.Constants.CUSTOMER_PHONE;
import static edu.mj.mart.utils.Constants.DB_COLLECTION_CUSTOMER;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;

import edu.mj.mart.base.BasePresenter;
import edu.mj.mart.model.Customer;

public class CustomerPresenter extends BasePresenter<CustomerView> {

    protected CustomerPresenter(FragmentActivity fragmentActivity, CustomerView view) {
        super(fragmentActivity, view);
    }

    public void getAllCustomer() {
        if (mView != null) {
            mView.showLoading();
        }
        db.collection(DB_COLLECTION_CUSTOMER)
                .get()
                .addOnCompleteListener(task -> {
                    if (mView != null) {
                        mView.hideLoading();
                        if (!task.isSuccessful()) {
                            mView.onFailed("Lấy danh sách thất bại");
                            return;
                        }
                        if (task.getResult() == null || task.getResult().isEmpty()) {
                            mView.onGetAllCustomerSuccess(Collections.emptyList());
                        } else {
                            ArrayList<Customer> customers = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String name = document.getString(CUSTOMER_NAME);
                                String phone = document.getString(CUSTOMER_PHONE);

                                Customer customer = new Customer(document.getId(), name, phone);
                                customers.add(customer);
                            }
                            mView.onGetAllCustomerSuccess(customers);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    if (mView != null) {
                        mView.onFailed(e.getMessage());
                    }
                });

    }

    public void createCustomer(String name, String phone) {
        if (mView != null) {
            mView.showLoading();
        }
        Customer customer = new Customer(name, phone);
        db.collection(DB_COLLECTION_CUSTOMER)
                .add(customer)
                .addOnSuccessListener(documentReference -> {
                    if (mView != null) {
                        mView.hideLoading();
                        customer.setId(documentReference.getId());
                        mView.onCreateCustomerSuccess(customer);
                    }
                })
                .addOnFailureListener(e -> {
                    if (mView != null) {
                        mView.hideLoading();
                        mView.onFailed(e.getMessage());
                    }
                });
    }

    public void updateCustomer(Customer customer) {
        if (mView != null) {
            mView.showLoading();
        }
        db.collection(DB_COLLECTION_CUSTOMER)
                .document(customer.getId())
                .set(customer)
                .addOnSuccessListener(documentReference -> {
                    if (mView != null) {
                        mView.hideLoading();
                        mView.onUpdateCustomerSuccess(customer);
                    }
                })
                .addOnFailureListener(e -> {
                    if (mView != null) {
                        mView.hideLoading();
                        mView.onFailed(e.getMessage());
                    }
                });
    }
}
