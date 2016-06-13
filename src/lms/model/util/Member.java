package lms.model.util;

import java.util.ArrayList;

public abstract class Member implements SystemOperations
{
	//=====FIELDS=====
    protected String memberID;
    protected String name;
    protected int credit;
    protected boolean status; //true if account is active, false == cant borrow
    protected ArrayList<Holding> borrowedHoldings;
    protected String string;
    
    //=====CONSTRUCTORS=====
    public Member(String memberID,String name,int credit)
    {
    	borrowedHoldings = new ArrayList<Holding>();
    	this.memberID = memberID;
    	this.name = name;
    	this.credit = credit;
    	this.status = true;
    }
    
    //=====ACCESSORS=====
    public String getID()
    {
    	return memberID;
    }
    
    public String getFullName()
    {
    	return name;
    }
    
    public boolean getStatus()
    {
    	return status;
    }
    
    public abstract int getMaxCredit();
    
    public Holding[] getCurrentHoldings()
    {
    	Holding[] borrowedHoldings = this.borrowedHoldings.toArray(new Holding[this.borrowedHoldings.size()]); //convert borrowed holdings to string array
    	return borrowedHoldings;
    }
    
    public int calculateRemainingCredit()
    {
    	return credit;
    }
    
    public abstract boolean checkAllowedCreditOverdraw(int lateFee);
    
    //=====MUTATORS=====
    public abstract void resetCredit();
    
    public boolean updateRemainingCredit(int loanFee)
    {
    	credit -= loanFee;
    	return true;
    }
    
    @Override
    public boolean activate()
    {
    	status = true;
    	return status;
    }
    
    @Override
    public boolean deactivate()
    {
    	status = false;
    	return status;
    }
    
    public boolean borrowHolding(Holding holding) throws InsufficientCreditException
    {
    	if (calculateRemainingCredit() > holding.getDefaultLoanFee() && getStatus() == true)
		{
			try
			{
				holding.borrowHolding(); //borrow holding
				borrowedHoldings.add(holding); //add to holdings list
				updateRemainingCredit(holding.getDefaultLoanFee());
				return true;
			}
			catch (ItemInactiveException e)
			{
				System.err.println(e.getMessage());
			}
			catch (OnLoanException e)
			{
				System.err.println(e.getMessage());
			}
			catch (Exception e)
			{
				System.err.println(e.getMessage());
			}
			
    	}
    	else if (getStatus() == true)
    	{
    		throw new InsufficientCreditException("Cannot borrow holding: Insufficient credit.");
    	}
    	else
    	{
    		System.out.println("Cannot borrow holding: Your account is inactive.");
    	}
    	return false;
    }
    
    public void returnHolding(Holding holding,DateTime returnDate)
    {
    	if(checkAllowedCreditOverdraw(holding.calculateLateFee(returnDate)) == true)
		{
			holding.returnHolding(returnDate); //return holding
			borrowedHoldings.remove(holding); //remove from holdings list
		}
		else
		{
			System.out.println("Cannot return holding due to late fee, please reset credit before returning.");
		}
    }
    
    public void print()
    {
    	System.out.println("Member: " + name + " (" + memberID + ")");
    	System.out.println("Credit: " + "$" + credit);
    	System.out.print("Status: ");
    	if (status == true )
    	{
    		System.out.println("active");
    	}
    	else if (status == false)
    	{
    		System.out.println("inactive");
    	}
    	
    	System.out.print("Borrowed items: ");
    	for (int i = 0;i < borrowedHoldings.size();i++)
    	{
    		System.out.print(borrowedHoldings.get(i).getTitle() + " (" + borrowedHoldings.get(i).getID() + ")");
    		if (i != borrowedHoldings.size() - 1)
    		{
    			System.out.print(", ");
    		}
    	}
    	System.out.println("");
    	System.out.println("");
    }
    
    public String toString()
    {
    	string = memberID + ":" + name + ":" + credit + ":" + status + ":";
    	for (int i = 0;i < borrowedHoldings.size();i++)
    	{
    		if (i != borrowedHoldings.size())
    		{
    			string += "#"; //add token if not last borrowedholding
    		}
    		string += borrowedHoldings.get(i).getID(); //add borrowedholding id to string
    	}
    	return string;
    }
    
    public void setCredit(int credit)
    {
    	this.credit = credit;
    }
    
    public void addBorrowedHolding(Holding holding)
    {
    	borrowedHoldings.add(holding);
    }
    
}
