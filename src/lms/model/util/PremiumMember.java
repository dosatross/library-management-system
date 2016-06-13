package lms.model.util;

public class PremiumMember extends Member
{
	//=====FIELDS=====
	private final static int maxBorrowingCredit = 45;
	
	//=====CONSTRUCTORS=====
	public PremiumMember(String premiumMemberID,String premiumMemberName)
	{
		super(premiumMemberID,premiumMemberName,maxBorrowingCredit);
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
		return true;
	}
}
