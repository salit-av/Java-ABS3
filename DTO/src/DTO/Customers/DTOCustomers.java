package DTO.Customers;

import AllParticipants.Customer.Customer;
import AllParticipants.Customer.Customers;

import java.util.ArrayList;
import java.util.List;

public class DTOCustomers {
    private List<DTOCustomer> allCustomersToPrint;

    public DTOCustomers(Customers allCustomers) {
        allCustomersToPrint = new ArrayList<>();
        for(String name: allCustomers.getCustomers().keySet()){
            Customer customer = allCustomers.getCustomers().get(name);
            DTOCustomer printCustomer = new DTOCustomer(customer);
            allCustomersToPrint.add(printCustomer);
        }
    }

    public List<DTOCustomer> getAllCustomersToPrint() {
        return allCustomersToPrint;
    }

    public boolean isEmpty(){ return allCustomersToPrint.isEmpty();}

    public DTOCustomer findCustomer(String cusName){
        for (DTOCustomer customer : allCustomersToPrint) {
            if (customer.getName().equals(cusName)) {
                return customer;
            }
        }
        return null;
    }

    public List<String> getAllCustomersName(){
        List<String> res = new ArrayList<>();
        for(DTOCustomer dtOprintCustomer:allCustomersToPrint){
            res.add(dtOprintCustomer.getName());
        }
        return res;
    }
}
