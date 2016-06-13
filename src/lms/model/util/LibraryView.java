package lms.model.util;

import java.util.Scanner;

public class LibraryView 
{
	
	private static Scanner keyboard;
	private static int option;
	
	//=====show menu=====
	private static void showMenu()
	{
    	System.out.println("==================================================");
    	System.out.println("1. Add Holding");
    	System.out.println("2. Remove Holding");
    	System.out.println("3. Add Member");
    	System.out.println("4. Remove Member");
    	System.out.println("5. Borrow Holding");
    	System.out.println("6. Return Holding");
    	System.out.println("7. Print all Holdings");
    	System.out.println("8. Print all Members");
    	System.out.println("9. Print specific Holding");
    	System.out.println("10. Print specific Member");
    	System.out.println("11. Save to File");
    	System.out.println("12. Load from File");
    	System.out.println("13. Activate member or holding");
    	System.out.println("14. Deactivate member or holding");
    	System.out.println("15. Reset credit");
    	System.out.println("16. Exit");
    	System.out.println("==================================================");
    	System.out.print("Enter an option: ");
    	keyboard = new Scanner(System.in);
    	option = keyboard.nextInt();
	}
	
	//=====show banner=====
	private static void showBanner()
	{
		System.out.println("==================================================");
    	System.out.println("           LIBRARY MANAGEMENT SYSTEM              ");
	}
	
	//=====MAIN=====
	public static void main(String[] args)
    {
    	LibrarySystem library = new LibrarySystem();
    	showBanner();
    	while (true)
    	{
   			showMenu();
			library.optionSelection(option);
    	}
    }
}
