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
        return !loan.getStatus().equals(status) || loan.getLenders().size() != lenders.size() ||
                loan.getNextYazToPay() != nextYazToPay || loan.getCapitalNow() != capitalNow;
    }
}
    /*
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
*/