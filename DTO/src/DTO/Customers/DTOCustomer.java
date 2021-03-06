package DTO.Customers;


import AllParticipants.Customer.Customer;
import AllParticipants.Customer.Transaction;
import AllParticipants.Loan.Loan;
import AllParticipants.Loan.Loans;
import AllParticipants.Notification;
import DTO.Loan.DTOLoan;
import Engine.Yaz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DTOCustomer {
    private String name;
    private int balance;
    private List<DTOtransaction> DTOtransactions;
    private Map<String, DTOLoan> DTOloansAsLender;
    private Map<String, DTOLoan> DTOloansAsBorrower;
    private List<DTOLoan> DTOloansWithPayments;
    private List<Notification> notificationList;



    public DTOCustomer(Customer customer) {
        this.name = customer.getName();
        this.balance = customer.getBalance();
        addTransactions(customer.getTransactions());
        addLoansAsLender(customer.getLoansAsLender());
        addLoansAsBorrower(customer.getLoansAsBorrower());
        addLoansWithPayments(customer.getLoansWithPayments());
        this.notificationList = customer.getNotificationsList();

    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void addTransactions(List<Transaction> transactions) {
        this.DTOtransactions = new ArrayList<>();
        DTOtransaction dtoTransaction;
        for(Transaction transaction: transactions){
            dtoTransaction = new DTOtransaction(transaction.getYaz(), transaction.getPay(), transaction.getInOrOut(), transaction.getBeforeTran(), transaction.getAfterTran());
            DTOtransactions.add(dtoTransaction);
        }
    }

    public void addLoansAsLender(Loans loansAsLender) {
        this.DTOloansAsLender = new HashMap<>();
        DTOLoan dtoLoan;
        Loan lo;
        for(String id: loansAsLender.getLoans().keySet()){
            lo = loansAsLender.getLoans().get(id);
            dtoLoan = new DTOLoan(lo);
            DTOloansAsLender.put(dtoLoan.getId(), dtoLoan);
        }
    }

    public void addLoansAsBorrower(Loans loansAsBorrower) {
        this.DTOloansAsBorrower = new HashMap<>();
        DTOLoan dtoLoan;
        Loan lo;
        for(String id: loansAsBorrower.getLoans().keySet()){
            lo = loansAsBorrower.getLoans().get(id);
            dtoLoan = new DTOLoan(lo);
            DTOloansAsBorrower.put(dtoLoan.getId(), dtoLoan);
        }
    }

    private void addLoansWithPayments(Loans loansWithPayments) {
        this.DTOloansWithPayments = new ArrayList<>();
        DTOLoan dtoLoan;
        Loan lo;
        for(String id: loansWithPayments.getLoans().keySet()){
            lo = loansWithPayments.getLoans().get(id);
            dtoLoan = new DTOLoan(lo);
            DTOloansWithPayments.add(dtoLoan);
        }
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public List<DTOtransaction> getDTOtransactions() {
        return DTOtransactions;
    }

    public Map<String, DTOLoan> getDTOloansAsBorrower() {
        return DTOloansAsBorrower;
    }

    public Map<String, DTOLoan> getDTOloansAsLender() {
        return DTOloansAsLender;
    }

    public List<DTOLoan> getAllLoansWithUnpaidPayment(Yaz currentYaz){
        List<DTOLoan> res = new ArrayList<>();
        for(String idLoan: DTOloansAsBorrower.keySet()){
            DTOLoan loan = DTOloansAsBorrower.get(idLoan);
            if(loan.getCountAllUnpaidPayments() > 0 || loan.getNextYazToPay() <= currentYaz.getCurrentYaz()){
                res.add(loan);
            }
        }
        return res;
    }
    public List<DTOLoan> getDTOloansWithPayments(){
        return DTOloansWithPayments;
    }
}
