/*
 * Created on Dec 28, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.testapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Vector;

import com.ibm.graph.Edge;
import com.ibm.graph.Vertex;
import com.ibm.quantra.semantic.LexiconContainerInterface;
import com.ibm.quantra.semantic.SemanticNet;
import com.ibm.quantra.semantic.SemanticProcessor;
import com.ibm.quantra.utilities.UserdictConstants;
import com.ibm.research.util.KeyMissingException;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SemanticProcessorTester {
	
	public static void main(String arg[]){
		SemanticProcessorTester tester = new SemanticProcessorTester();
		tester.displayNet(tester.getSemanticNet());		
	}
	public SemanticNet getSemanticNet(){
		SemanticProcessor semProcessor = new SemanticProcessor();
		semProcessor.loadSchema("xsdFiles\\railway.xsd");
		SemanticNet semanticNet = semProcessor.getSemanticNet();
		Enumeration enum = semanticNet.enumerateEdges();
		LexiconContainerInterface lexCon = semProcessor.getLexiconContainer();
		//to add the secondary aliases, we can use the function 'addSecondaryAliases(LexiconContainerInterface)
		//addSecondaryAliases(lexCon);	
		return semanticNet;
	}
	private void addSecondaryAliases(LexiconContainerInterface lex){
		Vector v = lex.getAllPrimaryWords();
		String secWord = null; 
		System.out.println("Adding secondary Aliases ");
		for(int i=0;i<v.size();i++){
			String str = (String)v.elementAt(i);
			System.out.print(str+": ");
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			try {
				secWord = br.readLine();
				while(secWord.compareTo("++")!=0){
					lex.setSecondaryWord(str,secWord,"");
					secWord = br.readLine();
				}
			} catch (IOException e) {e.printStackTrace();}			
		}
	} 
	private void displayNet(SemanticNet semanticNet){
		Enumeration edgeEnum = semanticNet.enumerateEdges();
		while(edgeEnum.hasMoreElements()){
			Edge edge = (Edge)edgeEnum.nextElement();
			Vertex v1 = edge.getFromVertex();
			Vertex v2 = edge.getToVertex();
			try {
				System.out.print(v1.userdict.getString(UserdictConstants.NAME)+"<---");
				System.out.print(edge.userdict.getString(UserdictConstants.NAME)+"----");
				System.out.println(v2.userdict.getString((UserdictConstants.NAME)));
 			} catch (KeyMissingException e) {
				e.printStackTrace();
			}
		}
	}

}
