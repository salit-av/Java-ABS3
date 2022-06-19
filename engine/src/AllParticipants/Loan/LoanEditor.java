package AllParticipants.Loan;

public class LoanEditor {
    private String id;
    private String owner;
    private int capitalNow; // Money now
    private int investment; // customer's investment in loan
    private int capitalLeft; // capitalNow - investment

    public LoanEditor(String id, String owner, int capitalNow) {
        this.id = id;
        this.owner = owner;
        this.capitalNow = capitalNow;
        this.investment = 0;
        this.capitalLeft = capitalNow;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public int getInvestment() {
        return investment;
    }

    public void setInvestment(int investment) {
        this.investment = investment;
    }

    public int getCapitalNow() {
        return capitalNow;
    }

    public void setCapitalNow(int capitalNow) {
        this.capitalNow = capitalNow;
    }

    public int getCapitalLeft() {
        return capitalLeft;
    }

    public void setCapitalLeft(int capitalLeft) {
        this.capitalLeft = capitalLeft;
    }
}
