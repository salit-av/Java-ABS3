package AllParticipants;

import AllParticipants.Customer.Customer;
import AllParticipants.Customer.Customers;
import AllParticipants.Loan.Loan;
import AllParticipants.Loan.Loans;

import java.util.List;
import java.util.stream.Collectors;

public class State {
    private List<Loan> loans;
    private List<Customer> customers;

    public State(Loans loans, Customers customers) {
        this.loans = loans.getLoans().values().stream().collect(Collectors.toList());
        this.customers = customers.getCustomers().values().stream().collect(Collectors.toList());
    }

/*    public List<Loan> getListLoans(Loans allloans) {
        for(Loan loan:allloans.getLoans().values()){
            loans.add()
        }
    }*/

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Customers getListCustomersAsCustomers() {
        Customers res = new Customers();
        for(Customer customer: customers){
            res.getCustomers().put(customer.getName(), customer);
        }
        return res;
    }

    public Loans getListLoansAsLoans() {
        Loans res = new Loans();
        for(Loan loan: loans){
            res.getLoans().put(loan.getId(), loan);
        }
        return res;
    }
}
