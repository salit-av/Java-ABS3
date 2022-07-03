package Components.CustomerView;

import DTO.Customers.DTOCustomer;
import DTO.DTOCategories;

import java.util.ArrayList;
import java.util.List;

public class CustomerViewData {
    private DTOCustomer customer;
    private List<String> loansIDAsBorrower;
    private List<String> loansIDAsLender;
    private DTOCategories categories;

    public CustomerViewData(DTOCustomer customer, DTOCategories categories) {
        this.customer = customer;
        loansIDAsBorrower = new ArrayList<>();
        loansIDAsLender = new ArrayList<>();
        this.categories = categories;
    }

    public DTOCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(DTOCustomer customer) {
        this.customer = customer;
    }

    public DTOCategories getCategories() {
        return categories;
    }

    public void setCategories(DTOCategories categories) {
        this.categories = categories;
    }

    public List<String> getLoansIDAsBorrower() {
        return loansIDAsBorrower;
    }

    public void setLoansIDAsBorrower(List<String> loansIDAsBorrower) {
        this.loansIDAsBorrower = loansIDAsBorrower;
    }

    public List<String> getLoansIDAsLender() {
        return loansIDAsLender;
    }

    public void setLoansIDAsLender(List<String> loansIDAsLender) {
        this.loansIDAsLender = loansIDAsLender;
    }
}
