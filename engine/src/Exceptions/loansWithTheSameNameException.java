package Exceptions;

public class loansWithTheSameNameException extends Exception{
    private final String EXCEPTION_MESSAGE = "There are loans with the same ID!";

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
