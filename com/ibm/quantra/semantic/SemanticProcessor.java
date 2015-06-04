/*
 * Created on Aug 27, 2004 at 7:58:22 PM
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.semantic;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * @author abc1
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SemanticProcessor implements SemanticProcessorInterface{
	
	private SemanticNet semanticNet;
	private LexiconContainerInterface lexiconContainerInterface;
	
	public SemanticProcessor(){
		semanticNet = new SemanticNet();
		lexiconContainerInterface = new LexiconContainer();
		semanticNet.addSyncListener((SyncListener)lexiconContainerInterface);
	}
	
	public SemanticNet getSemanticNet(){
		return semanticNet;
	}
	public LexiconContainerInterface getLexiconContainer(){
		LexiconTertiaryAliaseGenerator generator = new LexiconTertiaryAliaseGenerator();
		generator.insertTertiaryAliases(lexiconContainerInterface);
		return lexiconContainerInterface;
	}
	public void loadSchema(String xsdFileName){
		FileInputStream fis = null;
		XmlSchemaParserInterface xParser = new XmlSchemaParser();
		try {
		// obtaining the semantic graph after parsing.
			semanticNet = xParser.giveGraph(new BufferedInputStream(new FileInputStream(xsdFileName)),semanticNet);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
		
}
