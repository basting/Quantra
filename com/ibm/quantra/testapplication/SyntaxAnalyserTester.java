package com.ibm.quantra.testapplication;


import com.ibm.graph.Net;
import com.ibm.quantra.parseradapter.*;
import com.ibm.research.util.KeyMissingException;
import java.io.*;


public class SyntaxAnalyserTester {
	public static void main(String arg[]) throws IOException,KeyMissingException,CloneNotSupportedException{
		InputStreamReader isr=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(isr);
		System.out.print("Enter the query: ");
		String input=br.readLine();
	
		SyntaxAnalyserInterface sAnalyserInterface = new SyntaxAnalyser();
		sAnalyserInterface.initializeSyntaxAnalyser();
		sAnalyserInterface.createSyntaxNet(input);
		Net syntaxNet = sAnalyserInterface.getSyntaxNet();
		System.out.println(" ");
	 }		
}
