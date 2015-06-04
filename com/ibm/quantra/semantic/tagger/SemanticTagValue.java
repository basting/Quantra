/*
 * Created on Jan 29, 2005
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.semantic.tagger;

import java.util.Vector;

/**
 * @author Bastin
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SemanticTagValue {
	private Vector tagValue;
	
	public SemanticTagValue(){
		tagValue = new Vector();
	}
	public void addTagValue(String value){
		tagValue.add(value);
	}
	public void addTagValue(Vector values){
		tagValue.addAll(values);
	}
	public Vector getTagValue(){
		return tagValue;
	}
	public String getType(){
		String str = null;
		TagValueContent valueContent = (TagValueContent)tagValue.elementAt(0);
		str = valueContent.getType();
		return str;
	}
	public boolean hasWordAsEntity(String entity){
		for(int i=0;i<tagValue.size();i++){
			TagValueContent content = (TagValueContent)tagValue.elementAt(i);
			if(content.getContent().compareTo(entity)==0){
				return true;
			}
		}
		return false;
	}
}
