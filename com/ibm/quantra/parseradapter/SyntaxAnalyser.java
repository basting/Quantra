 /*
 * Created on Jun 28, 2004
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.parseradapter;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.ibm.graph.*;
/**
 * @author Administrator
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SyntaxAnalyser implements SyntaxAnalyserInterface{
	
	private UserInterface userInterface;
	private Net syntaxNet;
	private Statement inputStatement;
	private Statement modifiedStatement; 
	private StatementParser stmtParser;

	public SyntaxAnalyser(){
		inputStatement = new Statement();
		modifiedStatement = new Statement();
		stmtParser=new StatementParser();
		syntaxNet = new Net();
	}
	public void createSyntaxNet(String query){
		userInterface.getSyntaxNet(query,this);		
	}
	public Net getSyntaxNet(){
		return syntaxNet;
	}
	public Statement getInputStatement(){
		return inputStatement;
	}
	public Statement getModifiedStatement(){
		return modifiedStatement;
	}
	public StatementParser getStatementParser(){
		return stmtParser;
	}

	public void initializeSyntaxAnalyser(){
		String userInterfaceName;
		Properties property=null;
		try {
			FileInputStream in = new FileInputStream(ParserAdapterConstants.SyntaxAnalyserProperty);
			property=new Properties();
			property.load(in);
		}catch (IOException e) {
			e.printStackTrace();
		}
		try {
			// Identifying the type of userInterface,whether it is a CLI or GUI or anyother. 
			userInterfaceName = property.getProperty(ParserAdapterConstants.UserInterface);
			userInterface = (UserInterface)Class.forName(userInterfaceName).newInstance();
		} catch (InstantiationException e1) {
			
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}	
}
