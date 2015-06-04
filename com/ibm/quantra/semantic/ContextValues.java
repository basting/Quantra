/*
 * Created on Jan 2, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;


import com.ibm.quantra.semantic.SemanticProcessorConstants;


/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ContextValues {
	private HashMap conValueHash;
	
	public ContextValues(){
			conValueHash = new HashMap();
	}
	
	public boolean hasElement(String word) {
		
		boolean retVal = false;
		Set keys = conValueHash.keySet();
		Iterator keyIterator = keys.iterator();
		while(keyIterator.hasNext()){
			Object key = keyIterator.next();
			LexiconValues keyValue = (LexiconValues)conValueHash.get(key);
			String kind = keyValue.getKind(word);
			if(kind!=null)
				return true;			
		}
		return false;		
	}

	public void setSecondaryWord(String secondary, String context) {
		if(context==null){
			String key = SemanticProcessorConstants.SQUARE_BRACKET_PAIR;
			Object value = conValueHash.get(key);
			if(value==null){
				LexiconValues newValue = new LexiconValues();
				newValue.setSecondary(secondary);				
				conValueHash.put(SemanticProcessorConstants.SQUARE_BRACKET_PAIR,newValue);
			}
			else{
				LexiconValues oldValue = (LexiconValues)value;
				oldValue.setSecondary(secondary);				
			}
		}
		else{//if the context is not null
			String key = SemanticProcessorConstants.OPENING_BRACKET+context+SemanticProcessorConstants.CLOSING_BRACKET;
			Object value = conValueHash.get(key);
			if(value==null){
				LexiconValues newValue = new LexiconValues();
				newValue.setSecondary(secondary);				
				conValueHash.put(key,newValue);
			}
			else{
				LexiconValues oldValue = (LexiconValues)value;
				oldValue.setSecondary(secondary);				
			}
		}
	}
	public void setSecondaryWord(String primary, Vector secondaryWords) {
		//Yet to be implemented		
	}
	public void setTertiaryWords(Vector tertiaryWords){
		String key = SemanticProcessorConstants.SQUARE_BRACKET_PAIR;
		Object value = conValueHash.get(key);
			if(value==null){
				LexiconValues newValue = new LexiconValues();
				newValue.setTertiary(tertiaryWords);				
				conValueHash.put(key,newValue);
			}
			else{
				LexiconValues oldValue = (LexiconValues)value;
				oldValue.setTertiary(tertiaryWords);				
			}		
	}
	public Vector getSecondaryWords(String context) {
		Vector secondaryWords = null;
		if(context == null){
			String key = SemanticProcessorConstants.SQUARE_BRACKET_PAIR;
			LexiconValues lexValues = (LexiconValues)conValueHash.get(key);
			if(lexValues != null)
				secondaryWords =  lexValues.getAllSecondaryAliases();
		}
		else{
			String key = SemanticProcessorConstants.OPENING_BRACKET+context+SemanticProcessorConstants.CLOSING_BRACKET;
			LexiconValues lexValues = (LexiconValues)conValueHash.get(key);
			if(lexValues!=null)
				secondaryWords = lexValues.getAllSecondaryAliases();
		}
		return secondaryWords;
	}
	public Vector getTertiaryWords() {
		String key = SemanticProcessorConstants.SQUARE_BRACKET_PAIR;
		LexiconValues values = (LexiconValues)conValueHash.get(key);
		if(values == null)
			return null;
		return values.getAllTertiaryAliases();
	}
	public void removeSecondaryWord(String secondaryWord) {
		//yet to be implemented
	}
	public void removeTertiaryWord(String tertiaryWord) {
		//yet to be implemented
	}
	public void loadTertiaryWords(Dictionary dict) {
	}	
}
