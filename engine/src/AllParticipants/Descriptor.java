package AllParticipants;
import AllParticipants.Customer.*;
import AllParticipants.Loan.*;
import DTO.Customers.DTOBalace;
import DTO.Customers.DTOprintCustomer;
import DTO.Loan.DTOLoan;
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


    public Descriptor() {
        allCategories = new Categories();
        allLoans = new Loans();
        allCustomers = new Customers();
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

    public void setAllCustomersFromAbs(AbsCustomers customers) {
        int i = 0;
        AbsCustomer customer;
        while (i < customers.getAbsCustomer().size()){
            customer = customers.getAbsCustomer().get(i);
            allCustomers.addCustomerFromAbs(customer.getName(), customer.getAbsBalance(), allLoans);
            i++;
        }
    }

    public void setAllLoansFromAbs(AbsLoans absLoans) {
        int i = 0;
        AbsLoan loan = new AbsLoan();
        while (i < absLoans.getAbsLoan().size()){
            loan = absLoans.getAbsLoan().get(i);
            Customer ownerCus = allCustomers.getCustomers().get(loan.getAbsOwner());
            Loan lo = new Loan(loan.getId(), loan.getAbsOwner(), ownerCus, loan.getAbsCategory(), loan.getAbsCapital(), loan.getAbsTotalYazTime(), loan.getAbsPaysEveryYaz(), loan.getAbsIntristPerPayment());
            allLoans.addLoanFromAbs(lo);
            i++;
        }
    }

    public void loadFromXML(String path) throws referenceToCategoryThatIsntDefinedException, loanWhoseCustomerIsNotInSystemException, customersWithTheSameNameException, paymentRateIncorrectException {
        XmlReader reader = new XmlReader();
        AbsDescriptor absDescriptor = reader.openXML(path);
        loadFromAbsDescriptor(absDescriptor);
    }

    public void loadFromAbsDescriptor(AbsDescriptor absDescriptor) {
        setAllCategoriesFromAbs(absDescriptor.getAbsCategories());
        setAllLoansFromAbs(absDescriptor.getAbsLoans());
        setAllCustomersFromAbs(absDescriptor.getAbsCustomers());
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

    public boolean payPaymentToLoan(int currentYaz, DTOLoan dtoloan, DTOprintCustomer dtoBorrower) {
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

    public boolean payAllLoan(int currentYaz, DTOLoan dtoloan, DTOprintCustomer dtoBorrower) {
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
}

