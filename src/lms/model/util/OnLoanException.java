package lms.model.util;

public class OnLoanException extends Exception
{
    public OnLoanException() {}

    public OnLoanException(String message)
    {
       super(message);
    }
    
    public String getMessage()
    {
    	return super.getMessage();
    }
}
