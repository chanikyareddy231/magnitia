package tests;

import java.util.Scanner;

import classes.ReusableMethods;

public class Runner1 {


	public static void main(String[] args) throws Exception
	{
		//Run in local host not cloud
		//Take test data from keyboard
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter browser name");
		String bn=sc.nextLine();
		System.out.println("Enter url");
		String u=sc.nextLine();
		System.out.println("Enter userid");
		String uid=sc.nextLine();
		System.out.println("Enter userid criteria");
		String uidc=sc.nextLine();
		String pwd=null;
		String pwdc=null;
		if (uidc.equalsIgnoreCase("valid"))
		{
			System.out.println("Enter password");
			pwd=sc.nextLine();
			System.out.println("Enter password criteria");
			pwdc=sc.nextLine();	
		}
		sc.close();
		//create object to ReausableMethods class
		ReusableMethods obj=new ReusableMethods(bn);
		obj.LaunchSite(u);
		obj.fillandvalidatelogin(uid, uidc, pwd, pwdc);
		obj.closeSite();

	}

}
