package Exceptions;

public class customersWithTheSameNameException extends Exception{
    private final String EXCEPTION_MESSAGE = "There are customers with the same name!";

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
