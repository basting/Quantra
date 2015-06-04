/*
 * Created on Jun 28, 2004
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.parseradapter;

import java.io.IOException;

/**
 * @author Administrator
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PreProcessCommand extends UserCommand{
	private String inputStatement;
	
	public String getInputStatement(){
		return inputStatement;
	}
	public void setInputStatement(String inputStatement){
		this.inputStatement=inputStatement;
	}
	public void executeCommand(Object target){
		SyntaxAnalyser sAnalyzer = (SyntaxAnalyser)target;
		Statement inputStatement = sAnalyzer.getInputStatement();
		Statement modifiedStatement = sAnalyzer.getModifiedStatement();
		//	An instance of StatementPreprocessor is created.
		StatementPreprocessor preProcessor;
		try {
			preProcessor = new StatementPreprocessor();
			//The input statement is sent for Preprocessing.
			String s=preProcessor.preProcess(getInputStatement());
			//The Statement after preprocessing is inserted into the 
			//input Statement and the modified Stmt.
			inputStatement.putStatement(s);
			modifiedStatement.putStatement(s);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
