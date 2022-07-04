package AllParticipants;
import AllParticipants.Customer.*;
import AllParticipants.Loan.*;
import DTO.Customers.DTOBalace;
import DTO.Customers.DTOCustomer;
import DTO.Loan.DTOLoan;
import DTO.Loan.DTOLoans;
import Engine.Yaz;
import Exceptions.*;
import Status.Status;
import XMLReader.XmlReader;
import jaxb.schema.generated.*;

import java.util.List;


public class Descriptor {
    private Categories allCategories;
    private Loans allLoans;
    private Customers allCustomers;
    private Admin admin;


    public Descriptor() {
        allCategories = new Categories();
        allLoans = new Loans();
        allCustomers = new Customers();
        admin = new Admin();
    }

    public Categories getAllCategories() {
        return allCategories;
    }

    public void setAllCategoriesFromAbs(AbsCategories categories) {
        this.allCategories.setCategories(categories.getAbsCategory());
    }

    public Loans getAllLoans() {
        return allLoans;
    }

    public void setAllLoans(Loans value) {
        this.allLoans = value;
    }

    public Customers getAllCustomers() {
        return allCustomers;
    }

    public void setAllLoansFromAbs(AbsLoans absLoans, Customer customer) {
        for (AbsLoan loan:absLoans.getAbsLoan()){
            Loan lo = new Loan(loan.getId(), customer.getName(), customer, loan.getAbsCategory(), loan.getAbsCapital(), loan.getAbsTotalYazTime(), loan.getAbsPaysEveryYaz(), loan.getAbsIntristPerPayment());
            allLoans.addLoanFromAbs(lo);
            customer.getLoansAsBorrower().getLoans().put(lo.getId(), lo);
        }
    }

    public void loadFromXML(String path, String customerName) throws referenceToCategoryThatIsntDefinedException, loansWithTheSameNameException, paymentRateIncorrectException {
        XmlReader reader = new XmlReader();
        AbsDescriptor absDescriptor = reader.openXML(path, allCategories, allLoans);
        loadFromAbsDescriptor(absDescriptor, allCustomers.getCustomers().get(customerName));
    }

    public void loadFromAbsDescriptor(AbsDescriptor absDescriptor,Customer customer) {
        setAllCategoriesFromAbs(absDescriptor.getAbsCategories());
        setAllLoansFromAbs(absDescriptor.getAbsLoans(), customer);
    }

    public void addBalanceToCustomer(DTOBalace dtoAddBalace, Yaz currentYaz) {
        Customer customer = allCustomers.getCustomers().get(dtoAddBalace.getNameOfCustomer());
        int balanceBefore = customer.getBalance();
        int balanceAfter = balanceBefore + dtoAddBalace.getBalanceToChange();
        customer.addToTransactions(new Transaction(currentYaz.getCurrentYaz(), dtoAddBalace.getBalanceToChange(), '+', balanceBefore, balanceAfter));
        customer.setBalance(balanceAfter);
    }

    public void removeBalanceToCustomer(DTOBalace dtoBalace, Yaz currentYaz) {
        Customer customer = allCustomers.getCustomers().get(dtoBalace.getNameOfCustomer());
        int balanceBefore = customer.getBalance();
        int balanceAfter = balanceBefore - dtoBalace.getBalanceToChange();
        customer.addToTransactions(new Transaction(currentYaz.getCurrentYaz(), dtoBalace.getBalanceToChange(), '-', balanceBefore, balanceAfter));
        customer.setBalance(balanceAfter);

    }

    public void updateLoansAndCustomersAfterFundingAlgorithm(List<LoanEditor> allInvestmentLoansAfterAlgorithm, Customer lender, Yaz currentYaz) {
        Loan loan;
        Customer loanOwner;
        for (LoanEditor loanEditor: allInvestmentLoansAfterAlgorithm){
            loan = allLoans.getLoans().get(loanEditor.getId());
            // update loan
            loan.updateLoanAfterInvestment(loanEditor, currentYaz);
            loan.addToLenders(lender, loanEditor);

            // update lender
            lender.addToLoansAsLender(loan);

            //update loanOwner
            loanOwner = allCustomers.getCustomers().get(loan.getOwner());
            loanOwner.updateLoanOwnerAfterInvestment(loanEditor, currentYaz);

            lender.updateCustomerAfterInvestment(loanEditor, currentYaz);
        }
    }

    public void providingPayments(int currentYaz) {
        Customer customer;
        for(String cusName: allCustomers.getCustomers().keySet()){
            customer = allCustomers.getCustomers().get(cusName);
            List<Loan> listAsBorrower = customer.goOverListAsBorrowerAndFilter(currentYaz);
            customer.payToLenders(listAsBorrower, currentYaz);
        }
    }

    public boolean payPaymentToLoan(int currentYaz, DTOLoan dtoloan, DTOCustomer dtoBorrower) {
        Loan loan = allLoans.fromDTOLoanToLoan(dtoloan);
        Customer borrower = allCustomers.fromDTOCustomerToCustomer(dtoBorrower);
        int payment = loan.getNextPayIncludesInterestAndCapital();

        if(payment <= borrower.getBalance()) {
            loan.payLenders(borrower, currentYaz);
            if(loan.getStatus().equals(Status.RISK)){
                loan.setCountAllUnpaidPayments(loan.getCountAllUnpaidPayments() - 1);
                loan.setTotalAllUnpaidPayments(loan.getTotalAllUnpaidPayments() - loan.getNextPayIncludesInterestAndCapital());
            }
            if(loan.getCountAllUnpaidPayments() == 0) {
                borrower.getLoansWithPayments().getLoans().remove(loan.getId());
            }
            return true;
        }
        else {
            loan.addUnpaidPayment(borrower, currentYaz);
            return false;
        }
    }

    public boolean payAllLoan(int currentYaz, DTOLoan dtoloan, DTOCustomer dtoBorrower) {
        Loan loan = allLoans.fromDTOLoanToLoan(dtoloan);
        Customer borrower = allCustomers.fromDTOCustomerToCustomer(dtoBorrower);
        int payment = loan.getCapitalAndInterestAtStart() - loan.getTotalInterestAndCapitalUntilNow();

        if(payment <= borrower.getBalance()) {
            loan.payLendersAllPay(borrower, currentYaz);
            if (loan.getCountAllUnpaidPayments() == 0) {
                borrower.getLoansWithPayments().getLoans().remove(loan.getId());
            }
            return true;
        }
        else {
            return false;
        }
    }

    public void setLoansWithPaymentsInCustomers(int currentYaz) {
        for(String loanID:allLoans.getLoans().keySet()){
            Loan loan = allLoans.getLoans().get(loanID);
            Customer borrower = loan.getOwnerCus();
            if(loan.getNextYazToPay() < currentYaz && loan.getStatus().equals(Status.ACTIVE)){
                loan.setStatus(Status.RISK);
                borrower.getNotificationsList().add(new Notification("RISK!", "loan with id: " + loanID + " is in risk!", "Pay your bill!"));
            }
            if(loan.isRiskOrActive() && loan.doPayThisYaz(currentYaz)) {
                borrower.getLoansWithPayments().getLoans().put(loanID, loan);
                borrower.getNotificationsList().add(new Notification("PayMe!", "It's time to pay off a loan with an ID:" + loanID, "Thank you!"));
                loan.setNotificationsAtLenders();
            }
        }
    }

    public boolean isCustomerExists(String username) {
        return allCustomers.isCustomerExists(username);
    }

    public void addCustomer(String username) {
        allCustomers.addCustomer(username);
    }

    public boolean isAdminExists(String username) {
        return admin.isLogIn();
    }

    public void addAdmin(String username) {
        admin = new Admin(username, true, allLoans,allCustomers);
    }

    public DTOLoans getCustomersLoansAsBorrower(String username) {
        return allCustomers.getCustomersLoansAsBorrower(username);
    }

    public DTOLoans getCustomersLoansAsLender(String username) {
        return allCustomers.getCustomersLoansAsLender(username);
    }

    public DTOLoan getDTOLoan(String id) {
        return new DTOLoan(allLoans.getLoans().get(id));
    }

    public String addLoan(String cusName, String id, String category, int capital, int totalYazTime, int paysEveryYaz, int internistPerPayment) {
        String res = new XmlReader().addLoan(cusName, id, category, capital, totalYazTime, paysEveryYaz, internistPerPayment, allLoans, allCategories);
        if(res.equals("true")){
            Customer customer = allCustomers.getCustomers().get(cusName);
            Loan loan = new Loan(id, cusName, customer, category, capital, totalYazTime, paysEveryYaz, internistPerPayment);
            allLoans.getLoans().put(id, loan);
            customer.getLoansAsBorrower().getLoans().put(id, loan);
            return "Loan added successfully";
        }
        else{
            return res;
        }
    }
}

