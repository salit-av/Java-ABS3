package AllParticipants.Customer;

import AllParticipants.Loan.Loan;
import AllParticipants.Loan.Loans;
import AllParticipants.Notification;
import DTO.Customers.DTOprintCustomer;
import jaxb.schema.generated.AbsCustomers;

import java.util.*;

public class Customers {
    private Map<String, Customer> customers;

    public Customers() {
        this.customers = new HashMap<>();
    }

    public Map<String, Customer> getCustomers() {
        if (customers == null) {
            customers = new HashMap<>();
        }
        return this.customers;
    }

    public boolean addCustomerFromAbs(String cusName, int cusBalance, Loans allLoans) {
        Customer customer = customers.get(cusName);
        if (customer == null) {
            Loans allLoansAsBorrower = foundCustomerLoans(allLoans, cusName);
            Customer cus = new Customer(cusName, cusBalance, allLoansAsBorrower);
            customers.put(cusName, cus);
            allLoansAsBorrower.setOwnerCus(cus);
            cus.getNotificationsList().add(new Notification("WELCOME!", "We are excited to have you in our system!", "Have FUN!"));
            return true;
        }
        return false;
    }

    public Loans foundCustomerLoans(Loans allLoans, String name) {
       Loans allLoansAsBorrower = new Loans();
        for(String id: allLoans.getLoans().keySet()){
            Loan loan = allLoans.getLoans().get(id);
            if(loan.getOwner().equals(name)){
                allLoansAsBorrower.getLoans().put(loan.getId(), loan);
            }
        }
        return allLoansAsBorrower;
    }

    public Customer fromDTOCustomerToCustomer(DTOprintCustomer dtoPrintCustomer){
        return customers.get(dtoPrintCustomer.getName());
    }

}
