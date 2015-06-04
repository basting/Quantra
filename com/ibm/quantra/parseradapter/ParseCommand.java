/*
 * Created on Jun 28, 2004
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.parseradapter;


import com.ibm.graph.Net;

/**
 * @author Administrator
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ParseCommand extends UserCommand {
	private String inputStatement;
	private Net net;
	
	public String getInputStatement(){
		return inputStatement;
	}
	public void setInputStatement(String inputStatement){
		this.inputStatement=inputStatement;
	}
	public void executeCommand(Object target){
		SyntaxAnalyser sAnalyzer = (SyntaxAnalyser)target;
		net = sAnalyzer.getSyntaxNet();
		StatementParser stmtParser = sAnalyzer.getStatementParser();
//		The modified String is send for Parsing.
		String postscriptNotation = stmtParser.parseStatement(getInputStatement());
		String parsedWords[]= stmtParser.getParsedWords();
		stmtParser.releaseParser();
//The Syntax Graph is builded using the postScriptNotation and the sentence.
		SyntaxGraphBuilder syntaxGraph=new SyntaxGraphBuilder();
		net=syntaxGraph.constructSyntaxGraph(getInputStatement(),postscriptNotation,net,parsedWords);
	}
	public Net getNet(){
		return net;
	}	
}
