package Exceptions;

public class paymentRateIncorrectException extends Exception{
    private final String EXCEPTION_MESSAGE = "There is a payment that its rate is incorrect!";

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
