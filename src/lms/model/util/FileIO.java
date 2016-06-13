package lms.model.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class FileIO 
{
	//=====FIELDS=====
	private Holding[] holding;
	private Member[] member;
	
	//=====CONSTRUCTORS=====
	FileIO(Member[] member,Holding[] holding)
	{
		this.holding = holding;
		this.member = member;
	}
	
	//=====METHODS=====
	//=====save to file=====
	public void saveToFile()
	{
		String memberFileName = "Member.txt"; //name of member file
		String holdingFileName = "Holding.txt"; //name of holding file
		try
		{
			File memberFile = new File(memberFileName); //create member file
			File holdingFile = new File(holdingFileName); //create holding file
			
			//remove contents of file
			PrintWriter memberPrintWriter = new PrintWriter(memberFile); //create print writer for member file
			PrintWriter holdingPrintWriter = new PrintWriter(holdingFile); //create print writer for holding file
			
			//remove previous content in files
			memberPrintWriter.print("");
			memberPrintWriter.close();
			holdingPrintWriter.print("");
			holdingPrintWriter.close();
			
			//create member and holding file writers
			FileWriter memberFileWriter = new FileWriter(memberFile);
			FileWriter holdingFileWriter = new FileWriter(holdingFile);
			
			//write members to file
			for (int i = 0; i < member.length; i++)
			{
				if (member[i] != null)
				{
					memberFileWriter.write(member[i].toString());
					memberFileWriter.write(System.getProperty( "line.separator" ));
				}
			}
			
			//write holdings to file
			for (int i = 0; i < holding.length; i++)
			{
				if (holding[i] != null)
				{
					holdingFileWriter.write(holding[i].toString());
					holdingFileWriter.write(System.getProperty( "line.separator" ));
					
				}
			}
			memberFileWriter.flush();
			memberFileWriter.close();
			
			holdingFileWriter.flush();
			holdingFileWriter.close();
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	//load from file
	public void loadFromFile()
	{
		//clear holding and member arrays
		for (int i = 0;i < holding.length;i++)
		{
			holding[i] = null;
		}
		
		for (int i = 0;i < member.length;i++)
		{
			member[i] = null;
		}
		
		try 
		{	
			//restore holdings
			BufferedReader br = new BufferedReader(new FileReader("Holding.txt"));
			String line;
			int lineCounter = 0;
			
			while ((line = br.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, ":");


				String holdingID = st.nextToken();
				String title = st.nextToken();
				int loanFee = Integer.parseInt(st.nextToken());
				
				
				if (holdingID.charAt(0) == 'b')
				{
					holding[lineCounter] = new Book(holdingID,title);
				}
				else if (holdingID.charAt(0) == 'v')
				{
					holding[lineCounter] = new Video(holdingID,title,loanFee);
				}
				else 
				{
					System.out.println("Error in creating restoring holdings");
					return;
				}
				
				holding[lineCounter].setAvailability(Boolean.parseBoolean(st.nextToken()));
				
				boolean inactive = Boolean.parseBoolean(st.nextToken());
				
				if (inactive == true)
				{
					holding[lineCounter].deactivate();
				}
				else
				{
					holding[lineCounter].activate();
				}
				
				lineCounter++;
			}
			
			
			//restore members
			br = new BufferedReader(new FileReader("Member.txt"));
			lineCounter = 0;
			
			while ((line = br.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, ":");
				StringTokenizer hst = new StringTokenizer(line, "#");
				
				String memberID = st.nextToken();
				String name = st.nextToken();
				int credit = Integer.parseInt(st.nextToken());
				boolean status = Boolean.parseBoolean(st.nextToken());
				
				//restore member
				if (memberID.charAt(0) == 's')
				{
					member[lineCounter] = new StandardMember(memberID,name);
				}
				else if (memberID.charAt(0) == 'p')
				{
					member[lineCounter] = new PremiumMember(memberID,name);
				}
				else 
				{
					System.out.println("Error in creating restoring holdings");
					return;
				}
				
				//restore status
				if (status == true)
				{
					member[lineCounter].activate();
				}
				else
				{
					member[lineCounter].deactivate();
				}
				
				//restore credit
				member[lineCounter].setCredit(credit);
				
				//restore borrowed holdings 
				hst.nextToken(); //ignore first holding token
				String holdingID = ""; 
				int tokenCount = hst.countTokens();
				
				for (int i = 0;i < tokenCount; i++)
				{
					holdingID = hst.nextToken();
					for (int j = 0; j < holding.length; j++)
					{
						if (holding[j] != null && holding[j].getID().equals(holdingID))
						{
							member[lineCounter].addBorrowedHolding(holding[j]);
						}
					}
				}
				lineCounter++;
			}
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
}
