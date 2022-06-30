package AllParticipants;

import AllParticipants.Customer.Customers;
import AllParticipants.Loan.Loans;

public class Admin {
    private String name;
    private boolean isLogIn;
    private Loans allLoans;
    private Customers allCustomers;

    public Admin() {
        name = "admin";
        isLogIn = false;
        allLoans = new Loans();
        allCustomers = new Customers();
    }

    public Admin(String name, boolean isLogIn, Loans allLoans, Customers allCustomers) {
        this.name = name;
        this.isLogIn = isLogIn;
        this.allLoans = allLoans;
        this.allCustomers = allCustomers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLogIn() {
        return isLogIn;
    }

    public void setLogIn(boolean logIn) {
        isLogIn = logIn;
    }

    public Loans getAllLoans() {
        return allLoans;
    }

    public void setAllLoans(Loans allLoans) {
        this.allLoans = allLoans;
    }

    public Customers getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(Customers allCustomers) {
        this.allCustomers = allCustomers;
    }
}
