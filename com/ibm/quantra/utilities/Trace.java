/*
 * Created on Apr 29, 2004 at 8:23:55 PM
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.utilities;

/**
 * @author abc1
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Trace {
	private static int traceLevel = QConstants.INFORMATION;  // 1: Fatal Error, 2: Warning 3: Information 
	
	public static void Print(int tLevel, String msg) {
		if(tLevel >= traceLevel) {
			System.out.println(msg);
		}
	}
}
