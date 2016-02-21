package com.target.demo;

abstract class AbstractClass1 implements Interface1 {
	private String demo ;
	@Override
	public void test() {
		System.out.println("This is rishi here");
	}
	public AbstractClass1(String demo) {
		//super();
		this.demo = demo;
	}
	
}
