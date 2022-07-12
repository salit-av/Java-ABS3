package AllParticipants.Customer;

import AllParticipants.Loan.Loan;
import AllParticipants.Loan.Loans;
import AllParticipants.Notification;
import DTO.Customers.DTOCustomer;
import DTO.Customers.DTOtransaction;
import DTO.Loan.DTOLoans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            //allLoansAsBorrower.setOwnerCus(cus);
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

    public Customer fromDTOCustomerToCustomer(DTOCustomer dtoPrintCustomer){
        return customers.get(dtoPrintCustomer.getName());
    }

    public boolean isCustomerExists(String username) {
        return customers.containsKey(username);
    }

    public void addCustomer(String username) {
        Customer customer = new Customer(username, 0, new Loans());
        customers.put(username, customer);
        customer.getNotificationsList().add(new Notification("WELCOME!", "We are excited to have you in our system!", "Have FUN!"));

    }

    public DTOLoans getCustomersLoansAsBorrower(String username) {
        if(customers.get(username) != null) {
            return new DTOLoans(customers.get(username).getLoansAsBorrower());
        }
        else{
            return new DTOLoans(new Loans());
        }
    }

    public DTOLoans getCustomersLoansAsLender(String username) {
        if(customers.get(username) != null) {
            return new DTOLoans(customers.get(username).getLoansAsLender());
        }
        else{
            return new DTOLoans(new Loans());
        }
    }

    public List<DTOtransaction> getCustomersTransactions(String username) {
        List<DTOtransaction> res = new ArrayList<>();
        if(customers.get(username) != null) {
            List<Transaction> transactions = customers.get(username).getTransactions();
            for (Transaction transaction : transactions) {
                res.add(new DTOtransaction(transaction.getYaz(), transaction.getPay(), transaction.getInOrOut(), transaction.getBeforeTran(), transaction.getAfterTran()));
            }
        }
        return res;
    }

    public List<Notification> getCustomersNotifications(String username) {
        if (customers.get(username) != null) {
            return customers.get(username).getNotificationsList();
        } else {
            return new ArrayList<>();
        }
    }
}
