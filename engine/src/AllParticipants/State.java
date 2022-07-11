package AllParticipants;

import AllParticipants.Customer.Customer;
import AllParticipants.Customer.Customers;
import AllParticipants.Loan.Loan;
import AllParticipants.Loan.Loans;
import com.google.gson.Gson;

public class State {
    private String jsonLoans;
    private String jsonCustomers;

    public State(Loans loans, Customers customers) {
        Gson gson = new Gson();
        this.jsonLoans = gson.toJson(loans.getLoans().values());
        this.jsonCustomers = gson.toJson(customers.getCustomers().values());
    }

    public Customers getListCustomersAsCustomers(){
        Gson gson = new Gson();
        Customer[] customers =  gson.fromJson(jsonCustomers, Customer[].class);
        Customers res = new Customers();
        for(Customer customer:customers){
            res.getCustomers().put(customer.getName(), customer);
        }
        return res;
    }

    public Loans getListLoansAsLoans(){
        Gson gson = new Gson();
        Loan[] loans = gson.fromJson(jsonLoans, Loan[].class);
        Loans res = new Loans();
        for(Loan loan: loans){
            res.getLoans().put(loan.getId(), loan);
        }
        return res;
    }

    /*private List<Loan> loans;
    private List<Customer> customers;

    public State(Loans loans, Customers customers) {
        this.loans = new ArrayList<>();
        this.customers = new ArrayList<>();
        setLoans(loans);
        setCustomers(customers);
    }

    public void setCustomers(Customers customers) {
        for(Customer customer: customers.getCustomers().values()){
            this.customers.add(customer.clone());
        }
    }

    public void setLoans(Loans loans) {
        for(Loan loan:loans.getLoans().values()){
            this.loans.add(loan.clone());
        }
    }

    public Customers getListCustomersAsCustomers() {
        Customers newCustomers = new Customers();
        for(Customer customer: customers){
            newCustomers.getCustomers().put(customer.getName(), customer);
        }
        return newCustomers;
    }

    public Loans getListLoansAsLoans() {
        Loans newLoans = new Loans();
        for(Loan loan:loans){
            newLoans.getLoans().put(loan.getId(), loan);
        }
        return newLoans;
    }*/
}
