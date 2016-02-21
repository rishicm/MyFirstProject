package com.target.demo;

import java.util.Arrays;

public class Test {
	public static void main(String[] args) {
		String test = "Identifier \"ecf_archive\"";
		System.out.println(test.split("\"").toString());
		System.out.println(test.split("\"")[1].trim().toString());
	}
}
