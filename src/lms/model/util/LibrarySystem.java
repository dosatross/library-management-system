package lms.model.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class LibrarySystem
{
	
	//=====FIELDS=====
	private static Holding[] holding;
	private static Member[] member;	
	private Scanner keyboard;
	private int maxHoldings;
	private int maxMembers;
	private FileIO fileIO;
	
	//=====CONSTRUCTORS======
	LibrarySystem()
	{
		maxHoldings = 15;
		maxMembers = 15;
		
		//Holding and member arrays
		holding = new Holding[maxHoldings];
		member = new Member[maxMembers];
		
		//enter 8 holdings
		holding[0] = new Book("b000001","Intro to Java");
		holding[1] = new Book("b000002","Learning UML");
		holding[2] = new Book("b000003","Design Patterns");
		holding[3] = new Book("b000004","Advanced Java");
		holding[4] = new Video("v000001","Java 1",4);
		holding[5] = new Video("v000002","Java 2",6);
		holding[6] = new Video("v000003","UML 1",6);
		holding[7] = new Video("v000004","UML 2",4);
		
		//enter 4 members
		member[0] = new StandardMember("s000001","Joe Bloggs");
		member[1] = new StandardMember("s000002","Jane Smith");
		member[2] = new PremiumMember("p000001","Fred Bloggs");
		member[3] = new PremiumMember("p000002","Fred Smith");
		
		//keyboard 
		keyboard = new Scanner(System.in);
		
		//FileIO object for reading and writing state
		fileIO = new FileIO(member,holding);
	}
	
	//=====METHODS=====
	//=====menu option handling=====
	public void optionSelection(int option)
    {
    	switch (option)
		{
			case 1: //Add Holding
			{
				addHolding();
				break;
			}
				
			case 2: //Remove Holding
			{
				removeHolding();
				break;
			}
			case 3: //Add Member
			{
				addMember();
				break;
			}
			case 4: //Remove Member
			{
				removeMember();
				break;
			}
			case 5: //Borrow Holding
			{
				borrowHolding();
				break;
			}
			case 6: //Return Holding
			{
				returnHolding();
				break;
			}
			case 7: //Print all Holdings
			{
				printHoldings();
				break;
			}
			case 8: //Print all Members
			{
				printMembers();
				break;
			}
			case 9: //Print specific Holding
			{
				printSpecificHolding();
				break;
			}
			case 10: //Print specific Member
			{
				printSpecificMember();
				break;
			}
			case 11: //Save to File
			{
				saveToFile();
				break;
			}
			case 12: //Load from File
			{
				loadFromFile();
				break;
			}
			case 13: //Activate member of holding
			{
				activateMemberOrHolding();
				break;
			}
			case 14: //Deactivate member of holding
			{
				deactivateMemberOrHolding();
				break;
			}
			case 15: //Reset credit
			{
				resetCredit();
				break;
			}
			case 16: //Exit
			{
				System.exit(0);
				break;
			}
			default:
			{
				System.out.println("Invalid Option");
				return;
			}
		}
    }
	
	//=====GENERAL PURPOSE METHODS=====
	//=====search holding array=====
	public int searchHolding(String holdingID)
	{
		for (int i = 0; i < holding.length;i++)
		{
			if (holding[i] != null && holding[i].getID().equals(holdingID))
			{
				return i; //returns the first member that matches holdingID
			}
			else if (i == holding.length-1)
			{
				System.out.println("Holding does not exist in the system.");
			}
		}
		return -1; //error code
	}
	
	//=====search member array=====
	public int searchMember(String memberID)
	{
		for (int i=0;i<member.length;i++)
		{
			if (member[i] != null && member[i].getID().equals(memberID) )
			{
				return i; //returns the first member that matches memberID
			}
			else if (i == member.length-1)
			{
				System.out.println("Member does not exist in the system.");
			}
		}
		return -1; //error code
	}
	
	//=====count object array=====
	public int countObjectArray(Object[] object)
	{
		int counter = 0;
		for (int i = 0; i < object.length; i ++)
		{
			if (object[i] != null)
			{
				counter++; 
			}
		}
		return counter;
	}
	
	//=====first null index=====
	public int firstNullIndex(Object[] object)
	{
		for(int i = 0; i < object.length; i++)
		{
			if(object[i] == null)
			{
				return i; //returns first null in an object array
			}
		}
		return -1; //error code
	}
	
	//=====count type in member array=====
	public int countMemberArray(Member[] member,char type)
	{
		int counter = 0;
		for (int i = 0; i < member.length; i ++)
		{
			if (member[i] != null && member[i].getID().charAt(0) == type)
			{
				counter++; 
			}
		}
		return counter; //returns the number of premium or standard members
	}
	
	//=====count type in holding array=====
		public int countHoldingArray(Holding[] holding,char type)
		{
			int counter = 0;
			for (int i = 0; i < holding.length; i ++)
			{
				if (holding[i] != null && holding[i].getID().charAt(0) == type )
				{
					counter++; 
				}
			}
			return counter; //returns the number of books or videos
		}
	
	//=====LIBRARY SYSTEM METHODS=====
	//=====add holding=====
	public void addHolding()
	{
		if (countObjectArray(holding) >= maxHoldings)
		{
			System.out.println("Library is full");
			return;
		}
		else //if library is not full
		{
			System.out.print("Video (type 'v') or book (type 'b'): ");
			
			String holdingType = keyboard.nextLine(); //user input 
			char holdingTypeChar = holdingType.charAt(0);
			switch (holdingTypeChar) //parse holding type
			{
				case 'v':
				{
					break;
				}
				case 'b':
				{
					break;
				}
				default:
				{
					System.out.println("Invalid holding type.");
					return;
				}
			}
			
			System.out.print("Enter Holding Title: ");
			String title = keyboard.nextLine(); //user input title
			
			//get loan fee if video
			int loanFee = 0;
			if (holdingTypeChar == 'v')
			{
				System.out.print("Enter Loan Fee: ");
				loanFee = keyboard.nextInt(); //user input loan fee
				if (loanFee != 4 && loanFee != 6) //if not the allowed loan fee values
				{
					System.out.println("Invalid loan fee. Valid options: $4 or $6");
					return;
				}
			}
			
			int numberOfHoldingType = countHoldingArray(holding,holdingTypeChar);  //count the holding type
			String holdingID = null;

			//set holdingID
			if (numberOfHoldingType < 9)
			{
				holdingID = holdingType + "00000" + (numberOfHoldingType+1); 
			}
			else if (numberOfHoldingType >= 9)
			{
				holdingID = holdingType + "0000" + (numberOfHoldingType+1);
			}
			
			//add holding to system
			if (holdingTypeChar == 'v')
			{
				holding[firstNullIndex(holding)] = new Video(holdingID,title,loanFee);
				System.out.println("Successfully added " + title + ".");
			}
			else if (holdingTypeChar == 'b')
			{
				holding[firstNullIndex(holding)] = new Book(holdingID,title);
				System.out.println("Successfully added " + title + ".");
			}
		}
	}
	
	//=====remove holding=====
	public void removeHolding()
	{
		String holdingID = null;
		System.out.print("Enter Holding ID: ");
		holdingID = keyboard.nextLine(); //user input holdingID
		
		int holdingIndex = searchHolding(holdingID); //find holding index
		
		if (holdingIndex != -1)
		{
			System.out.println("Successfully removed " + holding[holdingIndex].getTitle() + ".");
			holding[holdingIndex] = null; //remove object
		}
	}
	
	//=====add member=====
	public void addMember()
	{
		if (countObjectArray(member) >= maxMembers)
		{
			System.out.println("Member list is full");
			return;
		}
		else //if member array is not full
		{
			System.out.print("Premium member (type 'p') or standard member (type 's'): ");
			String memberType = keyboard.nextLine(); //user input member type
			char memberTypeChar = memberType.charAt(0);
			switch (memberTypeChar) //parse member type
			{
				case 's':
				{
					break;
				}
				case 'p':
				{
					break;
				}
				default:
				{
					System.out.println("Invalid member type.");
					return;
				}
			}
			
			System.out.print("Enter member name: ");
			String name = keyboard.nextLine(); //user input name
			
			int numberOfMemberType = countMemberArray(member,memberTypeChar); //count member type 
			String memberID = null;

			//set memberID
			if (numberOfMemberType < 9)
			{
				memberID = memberType + "00000" + (numberOfMemberType+1);
			}
			else if (numberOfMemberType >= 9)
			{
				memberID = memberType + "0000" + (numberOfMemberType+1);
			}
			
			//add member
			if (memberTypeChar == 's')
			{
				member[firstNullIndex(member)] = new StandardMember(memberID,name);
			}
			else if (memberTypeChar == 'p')
			{
				member[firstNullIndex(member)] = new PremiumMember(memberID,name);
			}
		}
	}
	
	//=====remove member=====
	public void removeMember()
	{
		String memberID = null;
		System.out.print("Enter member ID: ");
		memberID = keyboard.nextLine(); //user input memberID

		int memberIndex = searchMember(memberID); //find member index
		
		if (memberIndex != -1)
		{
			System.out.println("Successfully removed " + member[memberIndex].getFullName() + ".");
			member[memberIndex] = null; //remove object
		}
	}
	
	//=====borrow holding=====
	public void borrowHolding()
	{
		int option;
		
		//identify member
		System.out.print("Enter member ID: ");
		String memberID = keyboard.nextLine(); //user input memberID
		
		//find borrowing member
		Member borrowingMember = null; //initialise borrowing member
		int memberIndex = searchMember(memberID); //find member index
		if (memberIndex != -1)
		{
			borrowingMember = member[memberIndex];
		}
		
		do //do while user wishes to transact
		{
			String holdingID = null;
			System.out.print("Enter Holding ID: ");
			holdingID = keyboard.nextLine(); //user input holdingID
			
			//find borrowing holding
			Holding borrowingHolding = null;
			int holdingIndex = searchHolding(holdingID);
			if (holdingIndex != -1)
			{
				borrowingHolding = holding[holdingIndex];
			}
			
			//borrow holding
			try
			{
				borrowingMember.borrowHolding(borrowingHolding); //borrow holding
			}
			catch (InsufficientCreditException e) //catch if member has insufficient credit
			{
				System.err.println(e.getMessage());
				return;
			}	
			catch (Exception e) //catch all
			{
				System.err.println(e.getMessage());
				return;
			}
			
			//ask user if they wish to continue borrowing
			System.out.println("Any more transactions?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			System.out.print("Enter option: ");
			 
			
			option = keyboard.nextInt(); //user input option
		}
		while (option == 1);
	}
	
	//=====return holding=====
	public void returnHolding()
	{
		int option;
		
		//identify member
		System.out.print("Enter member ID: ");
		String memberID = keyboard.nextLine(); //user input memberID
		
		//find returning member
		Member returningMember = null;
		int memberIndex = searchMember(memberID);
		if (memberIndex != -1)
		{
			returningMember = member[memberIndex];
		}
		
		do //do while user wishes to transact
		{
			String holdingID = null;
		
			System.out.print("Enter Holding ID: ");
			holdingID = keyboard.nextLine(); //user input holdingID
			
			//find returning holding
			Holding returningHolding = null;
			int holdingIndex = searchHolding(holdingID);
			if (holdingIndex != -1)
			{
				returningHolding = holding[holdingIndex];
			}
			
			//return holding
			DateTime todaysDate = new DateTime();
			try
			{
				returningMember.returnHolding(returningHolding,todaysDate); //return holding
			}
			catch (Exception e) //catch all
			{
				System.err.println(e.getMessage());
				return;
			}
			
			//success message
			System.out.println("Successfully returned " + returningHolding.getTitle() + ".");
			
			//late fee message
			if (returningHolding.calculateLateFee(todaysDate) == 0)
			{
				System.out.println("No late fee has been incurred.");
			}
			else if (returningHolding.calculateLateFee(todaysDate) > 0)
			{
				System.out.println("A $" + returningHolding.calculateLateFee(todaysDate) + " late fee has been incurred.");
			}
			
			//ask user if they wish to continue returning
			System.out.println("Any more transactions?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			System.out.print("Enter option: ");
			 
			option = keyboard.nextInt(); //user input option
		}
		while (option == 1);
	}
	
	//=====print all holdings=====
	public void printHoldings()
	{
		for (int i = 0; i < holding.length;i++)
		{
			if(holding[i] == null)
			{
				continue;
			}
			else
			{
				holding[i].print();
			}
		}
	}
	
	//=====print all members=====
	public void printMembers()
	{
		for (int i = 0; i < member.length;i++)
		{
			if (member[i] == null)
			{
				continue;
			}
			else
			{
				member[i].print();
			}
		}
	}
	
	//=====print specific holdings=====
	public void printSpecificHolding()
	{
		String holdingID = null;
		
		System.out.print("Enter holding ID (or 'e'/'exit' to exit): ");
		
		while (true)
		{ 
			holdingID = keyboard.nextLine(); //user input holdingid
			
			for (int i = 0; i < holding.length;i++)
			{
				if (holding[i] != null && (holding[i].getID().equals(holdingID)))
				{
					holding[i].print(); //print holding details to console
				}
				else if (i == holding.length-1)
				{
					System.out.print("Please enter a valid ID (or 'e'/'exit' to exit): "); 
				}
				else if (holdingID.equals("e") || holdingID.equals("exit"))
				{
					return; //exit to main menu
				}
			}
		}
	}
	
	//=====print specific members=====
	public void printSpecificMember()
	{
		String memberID = null;
		
		System.out.print("Enter member ID (or 'e'/'exit' to exit): ");
		
		while (true)
		{
			memberID = keyboard.nextLine(); //user input memberid
			
			for (int i = 0; i < member.length;i++)
			{
				if (member[i] != null && (member[i].getID().equals(memberID)))
				{
					member[i].print(); //print member details to console
				}
				else if (i == member.length-1)
				{
					System.out.print("Please enter a valid ID (or 'e'/'exit' to exit): ");
				}
				else if (memberID.equals("e") || memberID.equals("exit"))
				{
					return; //exit to main menu
				}
			}
		}
	}
	
	//=====save to file=====
	public void saveToFile()
	{
		fileIO.saveToFile();
	}
	
	//=====load from file=====
	public void loadFromFile()
	{
		fileIO.loadFromFile();
	}
	
	//=====activate member or holding=====
	public void activateMemberOrHolding()
	{
		String memberID = null;
		String holdingID = null;
		String line = null;

		System.out.print("Enter ID of holding or member: ");
		line = keyboard.nextLine(); //user input
		
		if (line.charAt(0) == 'v' || line.charAt(0) == 'b')
		{
			holdingID = line;
			
			int holdingIndex = searchHolding(holdingID);
			if (holdingIndex != -1)
			{
				holding[holdingIndex].activate();
				System.out.println("Holding activated.");
				
			}
		}
		else if (line.charAt(0) == 's' || line.charAt(0) == 'p')
		{
			memberID = line;
			
			int memberIndex = searchMember(memberID);
			if (memberIndex != -1)
			{
				member[memberIndex].activate();
				System.out.println("Member activated.");
				
			}
		}
	}
	
	//======deactivate member or holding=====
	public void deactivateMemberOrHolding()
	{
		String memberID = null;
		String holdingID = null;
		String line = null;
		
		System.out.print("Enter ID of holding or member: ");
		line = keyboard.nextLine();
		
		if (line.charAt(0) == 'v' || line.charAt(0) == 'b')
		{
			holdingID = line;
			
			int holdingIndex = searchHolding(holdingID);
			if (holdingIndex != -1)
			{
				holding[holdingIndex].deactivate();
				System.out.println("Holding deactivated.");
				
			}
		}
		else if (line.charAt(0) == 's' || line.charAt(0) == 'p')
		{
			memberID = line;
			
			int memberIndex = searchMember(memberID);
			if (memberIndex != -1)
			{
				member[memberIndex].deactivate();
				System.out.println("Member deactivated.");
				
			}
		}
	}
	
	//=====reset credit=====
	public void resetCredit()
	{
		String memberID = null;
		System.out.print("Enter member ID: ");
		memberID = keyboard.nextLine();
		
		int memberIndex = searchMember(memberID);
		if (memberIndex != -1)
		{
			member[memberIndex].resetCredit();
			System.out.println("Credit reset");
		}
	}
}
