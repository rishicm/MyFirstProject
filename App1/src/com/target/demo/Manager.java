package com.target.demo;

import java.io.IOException;

class Main extends AbstractClass1 implements Interface1  
{

	public Main(String demo) {
		super(demo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void test1() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
		
	}
	 void test2() throws IOException{
		System.out.println("test2");
	}

}
public class Manager extends Main{
	
	   void test2(){
		  System.out.println("test2-Manager");
	  }
	

	public Manager(String demo) {
		super(demo);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
	}
}
