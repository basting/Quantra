/*
 * Created on Mar 3, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.testapplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Vector;

import com.ibm.graph.Net;
import com.ibm.quantra.parseradapter.SyntaxAnalyser;
import com.ibm.quantra.queryconstructor.QueryConstructor;
import com.ibm.quantra.queryconstructor.QueryExecutor;
import com.ibm.quantra.semantic.LexiconContainerInterface;
import com.ibm.quantra.semantic.SemanticNet;
import com.ibm.quantra.semantic.SemanticProcessor;
import com.ibm.quantra.semantic.SemanticProcessorConstants;
import com.ibm.quantra.semantic.tagger.QueryData;
import com.ibm.quantra.semantic.tagger.SemanticTag;
import com.ibm.quantra.semantic.tagger.SemanticTagger;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryConstructorTester {
	
	public static void main(String args[])
		{
			QueryConstructorTester tester = new QueryConstructorTester();
			tester.performTesting();
		}
		public void performTesting()
		{	
			Vector fileNames = new Vector();	
			//SemanticProcessor is constructed
			SemanticProcessor semProcessor = new SemanticProcessor();
			//the XML Schema file is loaded
			getFileNames(fileNames);
			semProcessor.loadSchema((String)fileNames.elementAt(0));
			//the semanticNet is obtained.
			SemanticNet semanticNet = semProcessor.getSemanticNet();
			//the lexicon is constructed with the help of 
			//semanticNet and the WordNet.
			LexiconContainerInterface lexiconContainer = semProcessor.getLexiconContainer();
			
			//while(true)
				//obtain the Syntax Net.
				Net syntaxNet = obtainSyntaxNet();
				// Get the semanticTagger and obtain the SemanticTag
				SemanticTagger tagger = new SemanticTagger();
				SemanticTag tag = tagger.getSemanticTag(syntaxNet, semanticNet, lexiconContainer);
				//also obtain the queryData from the tagger.
				QueryData qData = tagger.getQueryData(tag,syntaxNet);
				//pass on the QueryData and the semanticNet for 
				//obtaining the query.
				String query = new QueryConstructor().getQuery(qData,semanticNet);
				if(query == null) System.exit(0);
				QueryExecutor executor = new QueryExecutor();
				executor.loadXMLFile((String)fileNames.elementAt(1));
				//obtaining the query results.
				
				Vector results = executor.executeQuery(query);
				display(results);
			//}					
		}
		private void getFileNames(Vector fileNames){
			Properties properties = new Properties();
			FileInputStream fis;
			try {
				fis = new FileInputStream(SemanticProcessorConstants.X_PROPERTIES_FILENAME);
				properties.load(fis);
			} catch (IOException e) {e.printStackTrace();}
			fileNames.add(properties.getProperty(SemanticProcessorConstants.SCHEMA_FILENAME));
			fileNames.add(properties.getProperty(SemanticProcessorConstants.XML_FILENAME));			
		}
		private void display(Vector results) {
			for(int i=0;i<results.size();i++){
				System.out.println((String)results.elementAt(i));
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
