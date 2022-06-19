package DTO.Loan;

import AllParticipants.Customer.Customer;
import AllParticipants.Loan.Payments;
import Status.Status;

import java.util.Map;

public class DTOprintLoan {
    private String id;
    private String owner;
    private String category;
    private int capitalAtStart; // Money at start
    private int interestPerPayment; // Ribit percent
    private int paysEveryYaz; // How often is the loan paid off in yaz

    private Status status;
    // pending
    private Map<String, DTOpayments> lenders;  // name of customer and payments
    private int payLeftFromPendingToActive; // capital - payLeftFromPendingToActive = allMoneyFromLenders

    // active
    private int yazFromPendingToActive; // how many yaz from pending to active
    private int nextYazToPay; // what yaz is the next payment
    //more at payment

    // risk
    private int countUnpaidPayments;
    private int totalUnpaidPayments; // all unpaid money

    // finished
    private int yazAtFirst;
    private int yazAtTheEnd;

    // more info
    private int capitalAndInterest; // capitalAtStart + (capitalAtStart * interestPerPayment) \ 100
    private int totalYazTime; // how long the lone is active
    private int nextPayIncludesInterestAndCapital; //  capitalAndInterest \ (totalYazTime \ paysEveryYaz)


    public DTOprintLoan(String id, String owner, String category, int capitalAtStart, int interestPerPayment, int paysEveryYaz, Status status, Map<String, DTOpayments> lenders, int payLeftFromPendingToActive, int yazFromPendingToActive, int nextYazToPay, int countUnpaidPayments, int totalUnpaidPayments, int yazAtFirst, int yazAtTheEnd, int capitalAndInterest, int totalYazTime, int nextPayIncludesInterestAndCapital) {
        this.id = id;
        this.owner = owner;
        this.category = category;
        this.capitalAtStart = capitalAtStart;
        this.interestPerPayment = interestPerPayment;
        this.paysEveryYaz = paysEveryYaz;
        this.status = status;
        this.lenders = lenders;
        this.payLeftFromPendingToActive = payLeftFromPendingToActive;
        this.yazFromPendingToActive = yazFromPendingToActive;
        this.nextYazToPay = nextYazToPay;
        this.countUnpaidPayments = countUnpaidPayments;
        this.totalUnpaidPayments = totalUnpaidPayments;
        this.yazAtFirst = yazAtFirst;
        this.yazAtTheEnd = yazAtTheEnd;
        this.capitalAndInterest = capitalAndInterest;
        this.totalYazTime = totalYazTime;
        this.nextPayIncludesInterestAndCapital = nextPayIncludesInterestAndCapital;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getCategory() {
        return category;
    }

    public int getCapitalAtStart() {
        return capitalAtStart;
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

    public int getCountUnpaidPayments() {
        return countUnpaidPayments;
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

    public int getCapitalAndInterest() {
        return capitalAndInterest;
    }

    public int getTotalYazTime() {
        return totalYazTime;
    }

    public int getNextPayIncludesInterestAndCapital() {
        return nextPayIncludesInterestAndCapital;
    }
}
