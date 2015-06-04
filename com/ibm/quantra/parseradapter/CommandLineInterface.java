/*
 * Created on Jun 28, 2004
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.parseradapter;


import java.io.*;
import java.util.*;

import com.ibm.graph.Net;

/**
 * @author Administrator
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CommandLineInterface implements UserInterface{
		private SyntaxAnalyserInterface sAnalyserInterface;
		private Net syntaxNet;
		
		public Net getSyntaxNet(String query,SyntaxAnalyserInterface sAnalyserInterface){
			this.sAnalyserInterface = sAnalyserInterface;
			submitSentence(query);
			return syntaxNet;
		}
		private void submitSentence(String query){
			boolean flag;
			PreProcessCommand preProcessCommand =new PreProcessCommand();
// read the sentences from the query and store it in the vector sentences			
			Vector sentences=(new StringOperation()).readSentences(query);
			int size,limit;
			size = sentences.size();
			ParseCommand parseCmd=new ParseCommand();

			
			for(limit=0;limit<size;limit++){
				String input=(String)sentences.elementAt(limit);
				input=input.trim();
				preProcessCommand.setInputStatement(input);
				preProcessCommand.executeCommand(sAnalyserInterface);
				String modifiedStatement=sAnalyserInterface.getModifiedStatement().getStatement();
				boolean condition=compareStatements(input,modifiedStatement);
				if(condition){
					flag=true;
				}
				else{
					System.out.print("The input has been modified as :\"");
					System.out.println(modifiedStatement+"\"");
					System.out.print("Do you want to continue(y/n)");
					InputStreamReader isr=new InputStreamReader(System.in);				
					BufferedReader br=new BufferedReader(isr);
					char c = ' ';
					try {
						c = (char) br.read();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if((c=='y')||(c=='Y')){
						flag=true;
					}
					else flag=false;
				}
				if(flag){
					
					parseCmd.setInputStatement(modifiedStatement);
					parseCmd.executeCommand(sAnalyserInterface);
				}
			}
			syntaxNet = parseCmd.getNet();
		}		
		private boolean compareStatements(String input,String modifiedStatement){
			
			return input.equals(modifiedStatement);
		}
		
}
