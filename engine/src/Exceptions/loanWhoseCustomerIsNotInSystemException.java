package Exceptions;

public class loanWhoseCustomerIsNotInSystemException extends Exception{
    private final String EXCEPTION_MESSAGE = "There is a loan whose customer is not in system!";

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
