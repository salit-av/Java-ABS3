package DTO.Loan;

import AllParticipants.Customer.Customer;
import AllParticipants.Loan.Payments;
import Status.Status;

import java.util.Map;

public class DTOLoan {
    private String id;
    private String category;
    private String ownersName;
    private int numOfOpenLoans;
    private int totalYazTime;
    private int capitalAtStart; // Money at start
    private int paysEveryYaz; // how long the lone is active
    private int interestPerPayment; // Ribit percent
    private int allPayment; // keren + ribit
    private Status status;
    private Map<String, DTOpayments> lenders;  // name of customer and payments


    // pending
    private int payLeftFromPendingToActive;

    // active
    private int nextYazForPayment;
    private int nextPayIncludesInterestAndCapital; // next keren + next ribit

    // risk
    private int coutUnpaidPayments;
    private int totalUnpaidPayments; // all unpaid money

    // finished
    private int yazAtFirst;
    private int yazAtTheEnd;

    public DTOLoan(String id, String category, String ownersName, int numOfOpenLoans, int totalYazTime, int capitalAtStart, int paysEveryYaz, int interestPerPayment, int allPayment, Status status, Map<String, DTOpayments> lenders, int payLeftFromPendingToActive, int nextYazForPayment, int nextPayIncludesInterestAndCapital, int countUnpaidPayments, int totalUnpaidPayments, int yazAtFirst, int yazAtTheEnd) {
        this.id = id;
        this.category = category;
        this.ownersName = ownersName;
        this.numOfOpenLoans = numOfOpenLoans;
        this.totalYazTime = totalYazTime;
        this.capitalAtStart = capitalAtStart;
        this.paysEveryYaz = paysEveryYaz;
        this.interestPerPayment = interestPerPayment;
        this.allPayment = allPayment;
        this.status = status;
        this.lenders = lenders;

        this.payLeftFromPendingToActive = payLeftFromPendingToActive;

        this.nextYazForPayment = nextYazForPayment;
        this.nextPayIncludesInterestAndCapital = nextPayIncludesInterestAndCapital;

        this.coutUnpaidPayments = countUnpaidPayments;
        this.totalUnpaidPayments = totalUnpaidPayments;

        this.yazAtFirst = yazAtFirst;
        this.yazAtTheEnd = yazAtTheEnd;
    }

    public DTOLoan getThis(){
        return this;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getOwnersName() {
        return ownersName;
    }

    public int getNumOfOpenLoans(){return numOfOpenLoans;}

    public int getTotalYazTime() {
        return totalYazTime;
    }

    public int getCapitalAtStart() {
        return capitalAtStart;
    }

    public int getPaysEveryYaz() {
        return paysEveryYaz;
    }

    public int getInterestPerPayment() {
        return interestPerPayment;
    }

    public int getAllPayment() {
        return allPayment;
    }

    public Status getStatus() {
        return status;
    }

    public int getPayLeftFromPendingToActive() {
        return payLeftFromPendingToActive;
    }

    public int getNextYazForPayment() {
        return nextYazForPayment;
    }

    public int getNextPayIncludesInterestAndCapital() {
        return nextPayIncludesInterestAndCapital;
    }

    public int getCountUnpaidPayments() {
        return coutUnpaidPayments;
    }

    public int getTotalUnpaidPayments() {
        return totalUnpaidPayments;
    }

    public int getYazAtFirst() {
        return yazAtFirst;
    }

    public int getYazAtTheEnd() {
        return yazAtTheEnd;
    }
}
