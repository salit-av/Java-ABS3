package AllParticipants.Loan;

public class Payment implements Cloneable{
    private int yazPayment;
    private int interestPerPayment; // Ribit
    private int capitalPerPayment;  // Keren
    private int totalPayment; // if == 0 not paid, in RISK
    private boolean isPaid;

    public Payment(int yazPayment, int interestPerPayment, int capitalPerPayment, int totalPayment) {
        this.yazPayment = yazPayment;
        this.interestPerPayment = interestPerPayment;
        this.capitalPerPayment = capitalPerPayment;
        this.totalPayment = totalPayment;
        this.isPaid = false;
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

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public Payment clone()  {
       /* try {
            return (Payment) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }*/
        return null;
    }
}
