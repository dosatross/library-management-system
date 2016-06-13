package lms.model.util;

public class ItemInactiveException extends Exception
{
    public ItemInactiveException() {}

    public ItemInactiveException(String message)
    {
       super(message);
    }
    
    public String getMessage()
    {
    	return super.getMessage();
    }
}
