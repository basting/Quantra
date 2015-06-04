/*
 * Created on Jun 28, 2004
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.parseradapter;

/**
 * @author Administrator
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class StatementParser {
	private static ParserAdapter pAdapter;
	private static String words[];
	static{
		pAdapter = new LGParserAdapter();
	}
	public StatementParser(){
		initParser();
	}
	private void initParser(){
		pAdapter.initParser();
	}
	public String parseStatement(String stmt){
		String parsedStatement=pAdapter.parseSentence(stmt);
		words = LGParserAdapter.getWords();
		return parsedStatement;
	}
	public void releaseParser(){
		pAdapter.releaseParser();
	}
	public String[] getParsedWords(){
		return words;
	}
}
