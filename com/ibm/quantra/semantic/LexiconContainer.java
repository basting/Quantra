/*
 * Created on Dec 27, 2004
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

import com.ibm.graph.Vertex;
import com.ibm.quantra.semantic.LexiconContainerInterface;
import com.ibm.quantra.semantic.SemanticProcessorConstants;
import com.ibm.quantra.semantic.SyncCommand;
import com.ibm.quantra.semantic.SyncEventObject;
import com.ibm.quantra.semantic.SyncListener;
import com.ibm.quantra.utilities.UserdictConstants;
import com.ibm.research.util.KeyMissingException;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LexiconContainer implements LexiconContainerInterface,SyncListener {
	/**
	 * This HashMap{lexicon} has primaryWords as the key
	 * and ContextValue as the key, which is also a HashMap
	 * */
	private HashMap lexicon;
			
	public LexiconContainer(){
		lexicon = new HashMap();
	}
	
	public String getPrimaryWord(String word) {
		
			if(lexicon.keySet().contains(word))
				return word;
			String retVal = null;
			Set keys = lexicon.keySet();
			Iterator keyIterator = keys.iterator();
			while(keyIterator.hasNext()){
				Object key = keyIterator.next();
				ContextValues conValue = (ContextValues)lexicon.get(key);
				if(conValue.hasElement(word))
					return (String)key;	
			}
			return retVal;
	}

	public boolean isPrimaryWord(String word) {
		if(lexicon.containsKey(word))
			return true;
		return false;
	}

	public void setSecondaryWord(String primary, String secondary,String context) {
		Object value = lexicon.get((String)primary);
		if(value == null){
			ContextValues contextValues = new ContextValues();
			contextValues.setSecondaryWord(secondary,context);
			lexicon.put(primary,contextValues);
		}
		else{			
			ContextValues contextValues = (ContextValues)value;
			contextValues.setSecondaryWord(secondary,context);
		}
	}
	public void setSecondaryWord(String primary, Vector secondaryWords) {
		//Yet to be implemented		
	}
	public void setTertiaryWord(String primary, Vector tertiaryWords) {
		Object value = lexicon.get(primary);
		if(value == null){
			ContextValues contextValues = new ContextValues();
			contextValues.setTertiaryWords(tertiaryWords);
			lexicon.put(primary,contextValues);
		}
		else{			
			ContextValues contextValues = (ContextValues)value;
			contextValues.setTertiaryWords(tertiaryWords);
		}
	}
	public Vector getSecondaryWords(String primaryWord,String context) {
		ContextValues values = (ContextValues)lexicon.get(primaryWord);
		if(values == null)
			return null;
		return values.getSecondaryWords(context);
	}

	public Vector getTertiaryWords(String primaryWord) {
		ContextValues values = (ContextValues)lexicon.get(primaryWord);
		if(values == null)
			return null;
		return values.getTertiaryWords();
	}

	public void removeSecondaryWord(String secondaryWord) {
		//yet to be implemented
	}

	public void removeTertiaryWord(String tertiaryWord) {
		//yet to be implemented
	}

	public void loadTertiaryWords(Dictionary dict) {
	}

	public void addPrimaryWord(String primaryWord) {
		lexicon.put(primaryWord,null);
	}
	public void removePrimaryWord(String primaryWord){
		lexicon.remove(primaryWord);
	}	
	public void update(SyncEventObject eventObject) {
		SyncCommand cmd = eventObject.getSyncCommand();
		String str = cmd.command;
		Object obj = cmd.value;
		Vertex vertex = (Vertex)obj;
		String name = null;
		try {
			name = vertex.userdict.getString(UserdictConstants.NAME);
		} catch (KeyMissingException e) {
			e.printStackTrace();
		}
		if(str.compareTo(SemanticProcessorConstants.ADD)==0){
			addPrimaryWord(name);
		}
		else if(str.compareTo(SemanticProcessorConstants.REMOVE)==0){
			removePrimaryWord(name);
		}
	}
	public Vector getAllPrimaryWords(){
		Set set = lexicon.keySet();
		Vector v = new Vector();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			v.add(iter.next());
		}
		return v;
	}	
}
