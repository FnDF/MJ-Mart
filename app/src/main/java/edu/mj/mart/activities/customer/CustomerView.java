package edu.mj.mart.activities.customer;

import java.util.List;

import edu.mj.mart.base.BaseView;
import edu.mj.mart.model.Customer;

public interface CustomerView extends BaseView {

    void onGetAllCustomerSuccess(List<Customer> customers);

    void onCreateCustomerSuccess(Customer customer);

    void onUpdateCustomerSuccess(Customer customer);

    void onFailed(String msg);
}
