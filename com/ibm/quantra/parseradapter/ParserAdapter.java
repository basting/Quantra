/*
 * Created on Apr 28, 2004 at 7:16:18 AM
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.parseradapter;




/**
 * @author abc1
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public abstract class ParserAdapter {
	//the method 'initGraph' initializes the dictionary and also the parseOptions.
	public abstract void initParser();
	//the method 'parseSentence' passes a sentence to the parser to get parsed.
	public abstract String parseSentence(String sentence);
	//the method 'constructSyntaxGraph' constructs a syntactic graph from the postscript notation obtained from the 'parseSentence'.
	public abstract void releaseParser();
}
