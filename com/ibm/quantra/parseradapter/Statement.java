/*
 * Created on Jun 28, 2004
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.parseradapter;

import java.util.*;

import com.ibm.quantra.utilities.QConstants;
import com.ibm.quantra.utilities.Trace;

/**
 * @author Administrator
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Statement {
	private String stmt;
	
		
	public void putStatement(String statement){
		stmt = statement;
	}
	public String getStatement(){
		return stmt;
	}
}
