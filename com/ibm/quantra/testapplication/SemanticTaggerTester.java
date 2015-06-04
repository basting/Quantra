/*
 * Created on Feb 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.testapplication;

import com.ibm.graph.Net;
import com.ibm.quantra.parseradapter.SyntaxAnalyser;
import com.ibm.quantra.semantic.LexiconContainerInterface;
import com.ibm.quantra.semantic.SemanticNet;
import com.ibm.quantra.semantic.SemanticProcessor;
import com.ibm.quantra.semantic.tagger.QueryData;
import com.ibm.quantra.semantic.tagger.SemanticTag;
import com.ibm.quantra.semantic.tagger.SemanticTagger;

import java.io.*;

public class SemanticTaggerTester
{
	public static void main(String args[])
	{
		SemanticTaggerTester tester = new SemanticTaggerTester();
		tester.perform();
	}
	public void perform()
	{		
		SemanticProcessor semProcessor = new SemanticProcessor();
		semProcessor.loadSchema("xsdFiles\\railway.xsd");
		SemanticNet semanticNet = semProcessor.getSemanticNet();
		LexiconContainerInterface lexiconContainer = semProcessor.getLexiconContainer();
		
		while(true){
		
			Net syntaxNet = obtainSyntaxNet();
			SemanticTagger tagger = new SemanticTagger();
			SemanticTag tag = tagger.getSemanticTag(syntaxNet, semanticNet, lexiconContainer);
			QueryData qData = tagger.getQueryData(tag,syntaxNet);
		}					
	}
	public Net obtainSyntaxNet()
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		System.out.print("Enter the query: ");
		String input = null;
		try
		{
			input = br.readLine();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		SyntaxAnalyser sAnalyserInterface = new SyntaxAnalyser();
		sAnalyserInterface.initializeSyntaxAnalyser();
		sAnalyserInterface.createSyntaxNet(input);
		Net syntaxNet = sAnalyserInterface.getSyntaxNet();
		return syntaxNet;
	}
}
