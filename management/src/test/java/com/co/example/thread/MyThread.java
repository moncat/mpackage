package com.co.example.thread;

public class MyThread extends Thread 
{ 
	private String name; 

	public void setwName(String name) 
	{ 
		this.name = name; 
	} 
	
	public void run() 
	{ 
		System.out.println("hello " + name); 
	} 
	
	public static void main(String[] args) 
	{ 
		MyThread myThread = new MyThread(); 
		myThread.setwName("");
		Thread thread = new Thread(myThread); 
		thread.start();  
	}
} 
