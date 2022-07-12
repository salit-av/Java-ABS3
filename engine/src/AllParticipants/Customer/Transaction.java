package AllParticipants.Customer;

public class Transaction{
    private int yaz;
    private int pay;
    private char inOrOut;
    private int beforeTran;
    private int afterTran;

    public Transaction(int yaz, int pay, char inOrOut, int beforeTran, int afterTran) {
        this.yaz = yaz;
        this.pay = pay;
        this.inOrOut = inOrOut;
        this.beforeTran = beforeTran;
        this.afterTran = afterTran;
    }

    public int getYaz() {
        return yaz;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public char getInOrOut() {
        return inOrOut;
    }

    public int getBeforeTran() {
        return beforeTran;
    }

    public int getAfterTran() {
        return afterTran;
    }


}
