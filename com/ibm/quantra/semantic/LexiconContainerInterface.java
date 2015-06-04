/*
 * Created on Dec 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic;

import java.util.Dictionary;
import java.util.Vector;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface LexiconContainerInterface {
	
	public String getPrimaryWord(String word);
	public boolean isPrimaryWord(String word);
	
	public void setSecondaryWord(String primary,String secondary,String context);
	public void setSecondaryWord(String primary,Vector secondaryWords);
	
	public void setTertiaryWord(String primary, Vector tertiaryWords);
	
	public Vector getSecondaryWords(String primaryWord,String context);
	public Vector getTertiaryWords(String primaryWord);
	
	public void removeSecondaryWord(String secondaryWord);
	public void removeTertiaryWord(String tertiaryWord);
	
	public void loadTertiaryWords(Dictionary dict);
	
	public void addPrimaryWord(String primaryWord);
	
	public Vector getAllPrimaryWords();
}
