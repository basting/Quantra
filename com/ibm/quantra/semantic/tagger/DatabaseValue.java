/*
 * Created on Mar 1, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic.tagger;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DatabaseValue {
	private String dbWord;
	private String dbValue;
		
	public void putDbWord(String word){
		dbWord = word;
	}
	public void putDbValue(String value){
		dbValue = value;
	}
	public String getDbValue(){
		return dbValue;
	}
	public String getDbWord(){
		return dbWord;
	}
}
