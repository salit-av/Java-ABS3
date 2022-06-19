package AllParticipants.Customer;

import AllParticipants.Loan.Loan;
import AllParticipants.Loan.LoanEditor;
import AllParticipants.Loan.Loans;
import AllParticipants.Notification;
import Engine.Yaz;
import Status.Status;

import java.util.*;
import java.util.stream.Collectors;

public class Customer {
    private String name;
    private int balance;
    private List<Transaction> transactions;
    private Loans loansAsLender; // string - id of Loan
    private Loans loansAsBorrower; // string - id
    private Loans loansWithPayments;
    private List <Notification> notificationsList;

    public Customer(String cusName, int cusBalance, Loans allLoansAsBorrower){
        name = cusName;
        balance = cusBalance;
        transactions = new ArrayList<>();
        loansAsLender = new Loans();
        loansAsBorrower = allLoansAsBorrower;
        loansWithPayments = new Loans();
        notificationsList = new ArrayList<>();
    }

    public List<Notification> getNotificationsList() {
        return notificationsList;
    }


    public Loans getLoansWithPayments() {
        return loansWithPayments;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int value) {
        this.balance = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addToTransactions(Transaction transaction){
        if(transaction == null){
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
    }

    public Loans getLoansAsLender() {
        return loansAsLender;
    }

    public Loans getLoansAsBorrower() {
        return loansAsBorrower;
    }

    public void editLastTransaction(int newPayment){
        transactions.get(transactions.size()).setPay(newPayment);
    }

    public void updateLoanOwnerAfterInvestment(LoanEditor loanEditor, Yaz currentYaz) {
        transactions.add(new Transaction(currentYaz.getCurrentYaz(), loanEditor.getInvestment(), '+', balance, balance + loanEditor.getInvestment()));
        balance += loanEditor.getInvestment();
    }

    public void updateCustomerAfterInvestment(LoanEditor loanEditor, Yaz currentYaz) {
        transactions.add(new Transaction(currentYaz.getCurrentYaz(), loanEditor.getInvestment(), '-', balance, balance - loanEditor.getInvestment()));
        balance -= loanEditor.getInvestment();
    }

    public List<Loan> goOverListAsBorrowerAndFilter(int currentYaz) {
        List <Loan> loansSort1 = loansAsBorrower.getLoans().values().stream().filter(loan -> (loan.getStatus().equals(Status.ACTIVE) || loan.getStatus().equals(Status.ACTIVE))).
                                                                        filter(loan -> loan.getNextYazToPay()== currentYaz).
                                                                          sorted(Comparator.comparingInt(Loan::getYazFromPendingToActive)).collect(Collectors.toList() );
        List<Loan> loansSort2 = new ArrayList<>();
        for(Loan loan1 :loansSort1){
            for(Loan loan2: loansSort1){
                if(loan1.getNextPayIncludesInterestAndCapital() >= loan2.getNextPayIncludesInterestAndCapital() && !loan1.getId().equals(loan2.getId())){
                    loansSort2.add(loan2);
                }
                else {
                    loansSort2.add(loan1);
                }
            }
        }
        return loansSort2;
    }

    public void payToLenders(List<Loan> listAsBorrower, int currentYaz) {
        int payment;
        for(Loan loan: listAsBorrower) {
            payment = loan.getNextPayIncludesInterestAndCapital();
            if(payment <= balance) {
                loan.payLenders(this, currentYaz);
            }
            else {
                loan.addUnpaidPayment(this, currentYaz);
            }
        }
    }

    public void addToLoansAsLender(Loan loan) {
        loansAsLender.getLoans().put(loan.getId(), loan);
    }
}

