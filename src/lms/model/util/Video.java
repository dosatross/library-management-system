package lms.model.util;

public class Video extends Holding
{
	//=====FIELDS=====
	private int[] loanFeeOptions;
    //=====CONSTRUCTORS=====
	Video(String holdingID, String title,int loanFee) 
	{
		super(holdingID, title);
		maxLoanPeriod = 7; //7 days
		this.loanFee = loanFee;
		loanFeeOptions = new int[] {4,6};
	}
	
	//=====ACCESSORS=====
	public int getloanFee()
	{
		return loanFee;
	}
	
	public int[] getLoanFeeOptions()
	{
		return loanFeeOptions;
	}
	
	
	//=====MUTATORS=====
	void setloanFee(int loanFee)
	{
		this.loanFee = loanFee;
	}
	
	@Override
	public int calculateLateFee(DateTime dateReturned)
	{
		int daysLate = DateTime.diffDays(dateReturned, dateBorrowed);
		int lateFee = (int) (daysLate*getloanFee()*0.5);
		return lateFee;
	}

	
	
}
