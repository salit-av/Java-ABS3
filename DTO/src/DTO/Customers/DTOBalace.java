package DTO.Customers;

public class DTOBalace {
    private String nameOfCustomer;
    private int balanceToChange;

    public DTOBalace(String nameOfCustomer, int balanceToChange) {
        this.nameOfCustomer = nameOfCustomer;
        this.balanceToChange = balanceToChange;
    }

    public String getNameOfCustomer() {
        return nameOfCustomer;
    }

    public int getBalanceToChange() {
        return balanceToChange;
    }
}
