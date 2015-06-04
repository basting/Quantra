/*
 * Created on Feb 28, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic;

import java.util.Vector;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface DictionaryInterface {
	
	public void initialize(String propsFile);
	public Vector getTertiaryWords(String string);
	public boolean checkIfPresentInDict(String word);
	//the checking can be done for all part-of-speech except 
	//prepositions and articles. 
}
