package lms.model.util;

public abstract class Holding implements SystemOperations
{
	//=====FIELDS=====
	protected String holdingID;
    protected String title;
    protected int loanFee;
    protected int maxLoanPeriod;
    protected boolean availability;
    protected boolean inactive;
    protected DateTime dateBorrowed;
    protected String string;
    
    //=====CONSTRUCTORS=====
    Holding(String holdingID,String title)
    {
    	this.holdingID = holdingID;
    	this.title = title;
    	this.availability = true;
    	this.inactive = false;
    }
    
    //=====ACCESSORS=====
    public String getID()
    {
    	return this.holdingID; 
    }
    
    public String getTitle()
    {
    	return this.title;
    }
    
    public boolean getStatus()
    {
    	return this.inactive;
    }
    
    public int getDefaultLoanFee()
    {
    	return this.loanFee;
    }
    
    public DateTime getBorrowDate()
    {
    	return this.dateBorrowed;
    }
    
    public int getMaxLoanPeriod()
    {
    	return this.maxLoanPeriod;
    }
    
    public boolean isOnLoan()
    {
    	return !this.availability;
    }
    
    //=====MUTATORS=====
    public abstract int calculateLateFee(DateTime dateReturned);
    
    public boolean borrowHolding() throws OnLoanException, ItemInactiveException
    {
    	if(availability == true && inactive == false)
    	{
    		dateBorrowed = new DateTime();
    		availability = false;
        	return true;
    	}
    	else if (inactive == true)
    	{
    		throw new OnLoanException("Holding is inactive");
    	}
    	else
    	{
    		throw new OnLoanException("Holding is unavailable");
    	}
    	
    }
    
    public boolean returnHolding(DateTime dateReturned)
    {
    	if (inactive == false && availability == false)
    	{
    		if (DateTime.diffDays(dateReturned, dateBorrowed) >= 0)
    		{
    			availability = true;
    			return true;
    		}
    		else 
    		{
    			System.out.println("Holding not borrowed");
    			return false;
    		}
    	}
    	else 
    	{
    		System.out.println("Cannot Return: Holding not borrowed");
			return true;
    	}
    }
    
    public void print()
    {
    	System.out.println("Holding: " + title + " (" + holdingID + ")");
    	System.out.println("Loan Fee: " + "$" + loanFee);
    	System.out.print("Availability: " );
    	
    	if (availability == true )
    	{
    		System.out.println("available");
    	}
    	else if (availability == false)
    	{
    		System.out.println("unavailable");
    	}
    	
    	System.out.print("Status: ");
    	if (inactive == false )
    	{
    		System.out.println("active");
    	}
    	else if (inactive == true)
    	{
    		System.out.println("inactive");
    	}
    	System.out.println("");
    	System.out.println("");
    }
    
    public String toString()
    {
    	string = holdingID + ":" + title + ":" + loanFee + ":" + availability + ":" + inactive;
    	return string;
    }
    
    @Override
    public boolean activate()
    {
    	this.inactive = false;
    	return inactive;
    }
    
    @Override
    public boolean deactivate()
    {
    	this.inactive = true;
    	return inactive;
    }
    
    public void setAvailability(boolean availability)
    {
    	this.availability = availability;
    }
    
}
