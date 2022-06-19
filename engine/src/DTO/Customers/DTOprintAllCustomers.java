package DTO.Customers;

import AllParticipants.Customer.Customer;
import AllParticipants.Customer.Customers;
import DTO.Loan.DTOprintLoan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DTOprintAllCustomers {
    private List<DTOprintCustomer> allCustomersToPrint;

    public DTOprintAllCustomers(Customers allCustomers) {
        allCustomersToPrint = new ArrayList<>();
        for(String name: allCustomers.getCustomers().keySet()){
            Customer customer = allCustomers.getCustomers().get(name);
            DTOprintCustomer printCustomer = new DTOprintCustomer(customer);
            allCustomersToPrint.add(printCustomer);
        }
    }

    public List<DTOprintCustomer> getAllCustomersToPrint() {
        return allCustomersToPrint;
    }

    public boolean isEmpty(){ return allCustomersToPrint.isEmpty();}

    public DTOprintCustomer findCustomer(String cusName){
        for (DTOprintCustomer customer : allCustomersToPrint) {
            if (customer.getName().equals(cusName)) {
                return customer;
            }
        }
        return null;
    }

    public List<String> getAllCustomersName(){
        List<String> res = new ArrayList<>();
        for(DTOprintCustomer dtOprintCustomer:allCustomersToPrint){
            res.add(dtOprintCustomer.getName());
        }
        return res;
    }
}
