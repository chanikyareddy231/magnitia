package tests;

import java.util.Scanner;

import classes.ReusableMethods;

public class Runner2 {

	public static void main(String[] args) throws Exception
	{
		//Test data from Keyboard
		Scanner sc=new Scanner(System.in);
		System.out.println("enter environment local/cloud");
		String env=sc.nextLine();
		System.out.println("Enter browser name");
		String bn=sc.nextLine();
		String v=null;
		String os=null;
		if(env.equalsIgnoreCase("cloud"))
		{
			System.out.println("Enter browser version");
		    v=sc.nextLine();
			System.out.println("Enter os");
		    os=sc.nextLine();
		}
		System.out.println("Enter URL");
		String u=sc.nextLine();
		System.out.println("Enter userid");
		String uid=sc.nextLine();
		System.out.println("Enter userid criteria");
		String uidc=sc.nextLine();
		String pwd=null;
		String pwdc=null;
		if(uidc.equalsIgnoreCase("valid"))
		{
			System.out.println("Enter password");
		    pwd=sc.nextLine();
			System.out.println("Enter password criteria");
			pwdc=sc.nextLine();
		}
		sc.close();
		//create object to ReusableMethods class
		ReusableMethods obj;
		if(env.equalsIgnoreCase("local"))
		{
	     obj=new ReusableMethods(bn);
		}
		else 
		{
		obj=new ReusableMethods(bn,v,os);
		}
		obj.LaunchSite(u);	
		obj.fillandvalidatelogin(uid, uidc, pwd, pwdc);
		obj.closeSite();
	

	}

}
