package Exceptions;

public class referenceToCategoryThatIsntDefinedException extends Exception{
    private final String EXCEPTION_MESSAGE = "There is a reference to an undefined category!";

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}
