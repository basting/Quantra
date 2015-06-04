/*
 * Created on Dec 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic.tagger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SemanticTag {
	/**
	 * here the key to the Hashmap is the entries
	 * from the syntaxNet and the Value is 
	 * 'SemanticTagValue' which contains a Vector.
	 * */
	private HashMap map;
	
	public SemanticTag(){
		map = new HashMap();
	}
	public void addTagKey(String name){
		map.put(name,null);
	}
	public void addTagKey(Collection names){
		Iterator iterator = names.iterator();
		while(iterator.hasNext()){
			addTagKey((String)iterator.next());
		}
	}
	public Vector getTagKeys(){
		Vector vector = new Vector();
		Set keys = map.keySet();
		Iterator iterator = keys.iterator();
		while(iterator.hasNext()){
			vector.add(iterator.next());
		}
		return vector;
	}
	public boolean isTagKey(String word){
		if(map.containsKey(word)){
			return true;
		}
		return false;
	}
	//to get the type of word whether it is an 'Entity' or an 'attribute of an entity'.
	public String getTypeOfWord(String word){
		String str = null;
		SemanticTagValue tagValue = (SemanticTagValue)map.get(word);
		str = tagValue.getType();
		return str;
	}
	public Vector getAllEntities(){
		Vector entities = new Vector();
		Set keys = map.keySet();
		Iterator iterator = keys.iterator();
		while(iterator.hasNext()){
			String key = (String)iterator.next();
			if(getTypeOfWord(key).compareTo(SemanticTaggerConstants.ENTITY)==0){
				entities.add(key);
			}
		}
		return entities;
	}
	public boolean addValueToSemanticTag(String tagKey,String tagValue){
		// returns 'false' if the SemanticTag doesn't have
		// such a key.
		if(!map.containsKey(tagKey))
			return false;
		Object value = map.get(tagKey);
		if(value==null){
			SemanticTagValue newValue = new SemanticTagValue();
			newValue.addTagValue(tagValue);
			map.put(tagKey,newValue);
		}
		else{
			SemanticTagValue previousValue = (SemanticTagValue)value;
			previousValue.addTagValue(tagValue);
		}
		return true;		
	}
	public void removeKeyFromTag(String key) {
		map.remove(key);		
	}
	public boolean addValueToSemanticTag(String tagKey, Vector tagValues) {
		if(!map.containsKey(tagKey))
			return false;
		Object value = map.get(tagKey);
		SemanticTagValue newValue = new SemanticTagValue();
		newValue.addTagValue(tagValues);
		map.put(tagKey,newValue);
		return true;
	}
	public boolean hasAsEntity(String queryWord,String entity){
		SemanticTagValue value = (SemanticTagValue)map.get(queryWord);
		if(value.hasWordAsEntity(entity)){
			return true;
		}		
		return false;
	}	
}