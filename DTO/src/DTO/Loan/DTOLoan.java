package DTO.Loan;

import AllParticipants.Loan.Loan;
import Status.Status;

import java.util.Map;

public class DTOLoan {
    private String id;
    private String owner;
    private String category;
    private int capitalAtStart; // Money at start
    private int interestAtStart;
    private int capitalNow; // Money now after payments
    private int investmentInLoan; // how much money left to add
    private int interestPerPayment; // Ribit percent
    private int paysEveryYaz; // How often is the loan paid off in yaz
    private  int numberOfPayments;
    private Status status;

    // pending
    private Map<String, DTOpayments> lenders;
    private int payLeftFromPendingToActive;

    // active
    private int yazFromPendingToActive; // how many yaz from pending to active
    private int nextYazToPay; // what yaz is the next payment
    //more at payment

    // finished
    private int yazAtFirst;
    private int yazAtTheEnd;

    // more info
    private int capitalAndInterest; // at the beginning --> capitalAtStart + (capitalAtStart * interestPerPayment) \ 100
    private int interestAndCapitalEveryPay;
    private int totalInterestAndCapitalUntilNow;
    private int capitalAndInterestAtStart;
    private int totalYazTime; // how long the lone is active
    private int nextPayIncludesInterestAndCapital; //  capitalAndInterest \ (totalYazTime \ paysEveryYaz)
    private int countAllUnpaidPayments; // how many unpaid for customer
    private int totalAllUnpaidPayments; // unpaid money for customer
    private int numOfOpenLoans;
    private boolean isInBuyingList;



    public DTOLoan(Loan loan){
        this.id = loan.getId();
        this.owner = loan.getOwner();
        this.category = loan.getCategory();
        this.status = loan.getStatus();
        this.capitalAtStart = loan.getCapitalAtStart();
        this.capitalNow = loan.getCapitalNow();
        this.interestAtStart = loan.getInterestAtStart();
        this.investmentInLoan = getInvestmentInLoan();
        this.capitalAndInterest = loan.getCapitalAndInterest();
        this.totalYazTime = loan.getTotalYazTime();
        this.paysEveryYaz = loan.getPaysEveryYaz();
        this.nextYazToPay = loan.getNextYazToPay();
        this.interestPerPayment = loan.getInterestPerPayment();
        this.yazFromPendingToActive = loan.getYazFromPendingToActive();
        this.nextYazToPay = loan.getNextYazToPay();
        this.nextPayIncludesInterestAndCapital = loan.getNextPayIncludesInterestAndCapital();
        this.yazAtFirst = loan.getYazAtFirst();
        this.yazAtTheEnd = loan.getYazAtTheEnd();
        this.lenders = loan.getLendersToDTO();
        this.payLeftFromPendingToActive = loan.getPayLeftFromPendingToActive();
        this.countAllUnpaidPayments = loan.getCountAllUnpaidPayments();
        this.totalAllUnpaidPayments = loan.getTotalAllUnpaidPayments();
        this.numberOfPayments = loan.getNumberOfPayments();
        this.interestAndCapitalEveryPay = loan.getInterestAndCapitalEveryPay();
        this.totalInterestAndCapitalUntilNow = loan.getTotalInterestAndCapitalUntilNow();
        this.capitalAndInterestAtStart = loan.getCapitalAndInterestAtStart();
        this.isInBuyingList = loan.isLoanInBuyingList();
        //this.numOfOpenLoans = loan.getNumOfOpenLoans();

    }

    public String getId() {
        return id;
    }

    public String getOwnersName() {
        return owner;
    }

    public String getCategory() {
        return category;
    }

    public int getCapitalAtStart() {
        return capitalAtStart;
    }

    public int getCapitalNow() {return capitalNow;}

    public int getInvestmentInLoan() {
        return investmentInLoan;
    }

    public int getInterestPerPayment() {
        return interestPerPayment;
    }

    public int getPaysEveryYaz() {
        return paysEveryYaz;
    }

    public Status getStatus() {
        return status;
    }

    public Map<String, DTOpayments> getLenders() {
        return lenders;
    }

    public int getPayLeftFromPendingToActive() {
        return payLeftFromPendingToActive;
    }

    public int getYazFromPendingToActive() {
        return yazFromPendingToActive;
    }

    public int getNextYazToPay() {
        return nextYazToPay;
    }

    public boolean doPayThisYaz(int currentYaz){
        if((currentYaz - yazFromPendingToActive)/ (paysEveryYaz) == 0){
            return true;
        }
        return false;
    }

    public int getYazAtFirst() {
        return yazAtFirst;
    }

    public int getYazAtTheEnd() {
        return yazAtTheEnd;
    }

    public int getCapitalAndInterest() {
        return capitalAndInterest;
    }

    public int getCapitalAndInterestAtStart() {
        return capitalAndInterestAtStart;
    }

    public int getInterestAndCapitalEveryPay() {
        return interestAndCapitalEveryPay;
    }

    public int getTotalInterestAndCapitalUntilNow() {
        return totalInterestAndCapitalUntilNow;
    }

    public int getNumOfOpenLoans() {
        return numOfOpenLoans;
    }

    public int getTotalYazTime() {
        return totalYazTime;
    }

    public int getNextPayIncludesInterestAndCapital() {
        return nextPayIncludesInterestAndCapital;
    }

    public int getCountAllUnpaidPayments() {
        return countAllUnpaidPayments;
    }

    public int getTotalAllUnpaidPayments() {
        return totalAllUnpaidPayments;
    }


    public boolean isDifferent(DTOLoan loan) {
        return (!loan.getStatus().equals(status)) || loan.getLenders().size() != lenders.size() ||
                loan.getNextYazToPay() != nextYazToPay || loan.getCapitalNow() != capitalNow;
    }

    public boolean isLoanInBuyingList(){
        return isInBuyingList;
    }
}
