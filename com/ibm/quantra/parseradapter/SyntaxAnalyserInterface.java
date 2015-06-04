/*
 * Created on Jan 3, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.parseradapter;

import com.ibm.graph.Net;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface SyntaxAnalyserInterface {
	
	public void createSyntaxNet(String query);
	public Net getSyntaxNet();
	public Statement getInputStatement();
	public Statement getModifiedStatement();
	public StatementParser getStatementParser();
		
	public void initializeSyntaxAnalyser();

}
