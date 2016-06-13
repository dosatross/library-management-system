package lms.model.util;

public class InsufficientCreditException extends Exception
{
    public InsufficientCreditException() {}

    public InsufficientCreditException(String message)
    {
       super(message);
    }
    
    public String getMessage()
    {
    	return super.getMessage();
    }
}
