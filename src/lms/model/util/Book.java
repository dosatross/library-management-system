package lms.model.util;

public class Book extends Holding
{
	//=====FIELDS=====
	private int dailyLateFeeRate = 2; //2 dollars per day 
	
    //=====CONSTRUCTORS=====
	Book(String holdingID, String title) 
	{
		super(holdingID, title);
		loanFee = 10; //fixed loan fee in dollars
		maxLoanPeriod = 28; //max loan period in days
	}
	
	//=====ACCESSORS=====
	int getdailyLateFeeRate()
	{
		return this.dailyLateFeeRate;
	}
	
	//=====MUTATORS=====
	@Override
	public int calculateLateFee(DateTime dateReturned)
	{
		int daysLate = DateTime.diffDays(dateReturned, dateBorrowed);
		int dailyRate = getdailyLateFeeRate();
		int lateFee = dailyRate*daysLate;
		return lateFee;
	}

	
}
