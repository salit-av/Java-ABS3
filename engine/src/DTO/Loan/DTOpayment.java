package DTO.Loan;

public class DTOpayment {
    private int yazPayment;
    private int interestPerPayment; // Ribit
    private int capitalPerPayment;  // Keren
    private int totalPayment; // if == 0 not paid, in RISK

    public DTOpayment(int yazPayment, int interestPerPayment, int capitalPerPayment, int totalPayment) {
        this.yazPayment = yazPayment;
        this.interestPerPayment = interestPerPayment;
        this.capitalPerPayment = capitalPerPayment;
        this.totalPayment = totalPayment;
    }

    public int getYazPayment() {
        return yazPayment;
    }

    public int getInterestPerPayment() {
        return interestPerPayment;
    }

    public int getCapitalPerPayment() {
        return capitalPerPayment;
    }

    public int getTotalPayment() {
        return totalPayment;
    }
}
