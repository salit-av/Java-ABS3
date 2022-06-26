package DTO.Customers;

public class DTOtransaction {
    private int yaz;
    private int pay;
    private char inOrOut;
    private int beforeTran;
    private int afterTran;


    public DTOtransaction(int yaz, int pay, char inOrOut, int beforeTran, int afterTran) {
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
