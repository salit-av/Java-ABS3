package DTO.Loan;

import java.util.List;

public class DTOpayments {
    private List<DTOpayment> allPayments;
    private int investment; // Customer's investment
    private int capital;
    private int interest;
    private int capitalUntilNow;  // Keren until now
    private int interesUntilNowt;  // Ribit until now
    private int capitalToPay;  // Keren needs to pay
    private int interestToPay;  // Ribit needs to pay
    private boolean isInBuyingList;

    public DTOpayments(List<DTOpayment> allPayments, int investment, int capital, int interest, int capitalUntilNow, int interesUntilNowt, int capitalToPay, int interestToPay, boolean isInBuyingList) {
        this.allPayments = allPayments;
        this.investment = investment;
        this.capital = capital;
        this.interest = interest;
        this.capitalUntilNow = capitalUntilNow;
        this.interesUntilNowt = interesUntilNowt;
        this.capitalToPay = capitalToPay;
        this.interestToPay = interestToPay;
        this.isInBuyingList = isInBuyingList;
    }

    public List<DTOpayment> getAllPayments() {
        return allPayments;
    }

    public int getInvestment() {
        return investment;
    }

    public int getCapitalUntilNow() {
        return capitalUntilNow;
    }

    public int getInterestUntilNow() {
        return interesUntilNowt;
    }

    public int getCapitalToPay() {
        return capitalToPay;
    }

    public int getInterestToPay() {
        return interestToPay;
    }

    public int getCapital() {
        return capital;
    }

    public int getInterest() {
        return interest;
    }

    public int getInteresUntilNowt() {
        return interesUntilNowt;
    }

    public boolean isInBuyingList() {
        return isInBuyingList;
    }
}
