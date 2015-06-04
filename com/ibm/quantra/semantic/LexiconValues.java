/*
 * Created on Dec 27, 2004
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
public class LexiconValues {
	private Vector secondaryAliases = null;
	private Vector tertiaryAliases = null;
	
	public LexiconValues(){
		secondaryAliases = new Vector();
		tertiaryAliases = new Vector();
	}
	public Vector getAllSecondaryAliases(){
		return secondaryAliases;
	}
	public Vector getAllTertiaryAliases(){
		return tertiaryAliases;
	}
	public void setSecondary(String word){
		secondaryAliases.add(word);
	}
	public void setTertiary(String word){
		tertiaryAliases.add(word);
	}
	public void setTertiary(Vector words){
		tertiaryAliases.addAll(words);
	}
	public String getKind(String word){
		if(getIndexInSecondary(word)!=-1)
			return SemanticProcessorConstants.SECONDARY_ALIAS ;
		else if(getIndexInTertiary(word)!=-1)
			return SemanticProcessorConstants.TERTIARY_ALIAS;
		else
			return null;
	}
	private int getIndexInSecondary(String word){
		int retVal = -1;
		for(int i=0;i<secondaryAliases.size();i++){
			if(word.equalsIgnoreCase((String)secondaryAliases.elementAt(i))) {
				retVal = i;
				break;
			}
		}
		return retVal;
	}
	private int getIndexInTertiary(String word){
		int retVal = -1;
		for(int i=0;i<tertiaryAliases.size();i++){
			if(word.equalsIgnoreCase((String)tertiaryAliases.elementAt(i))) {
				retVal = i;
				break;
			}
		}
		return retVal;
	}
	
}
