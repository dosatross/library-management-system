package lms.model.util;

public class StandardMember extends Member
{
	//=====FIELDS=====
	private final static int maxBorrowingCredit = 30;
	
	//=====CONSTRUCTORS=====
	public StandardMember(String standardMemberID,String standardMemberName)
	{
		super(standardMemberID,standardMemberName,maxBorrowingCredit);
	}

	//=====ACCESSORS=====
	@Override
	public int getMaxCredit() 
	{
		return maxBorrowingCredit;
	}

	//=====MUTATORS=====

	@Override
	public void resetCredit() 
	{
		credit = maxBorrowingCredit;
	}

	@Override
	public boolean checkAllowedCreditOverdraw(int lateFee) 
	{
		if (calculateRemainingCredit()-lateFee < 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

}
