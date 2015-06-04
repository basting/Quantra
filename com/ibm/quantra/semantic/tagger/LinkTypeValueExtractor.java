/*
 * Created on Mar 1, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic.tagger;

import java.util.Enumeration;
import java.util.Vector;

import com.ibm.graph.Edge;
import com.ibm.graph.Net;
import com.ibm.graph.Vertex;
import com.ibm.quantra.utilities.UserdictConstants;
import com.ibm.research.util.KeyMissingException;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LinkTypeValueExtractor implements DatabaseValueExtractorInterface {
	public Vector getDatabaseValues(Net syntaxNet){
		Vector dbValues = new Vector();
		dbValues = getWords(syntaxNet);
		return dbValues;
	}
	//to verify whether the required links are present in the graph.
	private Vector getWords(Net syntaxNet){
		Vector words = new Vector();
		Vector returnWords;
		returnWords = getWordsType1(syntaxNet);
		words.addAll(returnWords);
				
		return words;
	}
	private Vector getWordsType1(Net syntaxNet){
		Vector words = new Vector();
		String wordCurrent = null;
		String valueCurrent = null;
		Enumeration edgeEnum = syntaxNet.enumerateEdgesByUserKeySetToValue(UserdictConstants.NAME,SemanticTaggerConstants.MVP);
		while(edgeEnum.hasMoreElements()){
			Edge edge = (Edge)edgeEnum.nextElement();
			Vertex toVertex1 = edge.getToVertex();
			try {
				wordCurrent = toVertex1.userdict.getString(UserdictConstants.NAME);
			} catch (KeyMissingException e2) {
				try {
					wordCurrent = toVertex1.userdict.getString(UserdictConstants.AUG_NAME);
				} catch (KeyMissingException e1) {e1.printStackTrace();}
			  }
			Enumeration enum2 = toVertex1.enumerateEdgesByUserKeySetToValue(UserdictConstants.NAME,SemanticTaggerConstants.J);
			if(!enum2.hasMoreElements()){
				enum2 = toVertex1.enumerateEdgesByUserKeySetToValue(UserdictConstants.NAME,SemanticTaggerConstants.JS);
			}
			if(!enum2.hasMoreElements()){
				enum2 = toVertex1.enumerateEdgesByUserKeySetToValue(UserdictConstants.NAME,SemanticTaggerConstants.JP);
			}
			Edge edg2 = (Edge)enum2.nextElement();
			Vertex toVertex2 = edg2.getToVertex();
			try {
				valueCurrent = toVertex2.userdict.getString(UserdictConstants.NAME); 
			} catch (KeyMissingException e) {
				try {
					valueCurrent = toVertex2.userdict.getString(UserdictConstants.AUG_NAME);
				} catch (KeyMissingException e1) {e1.printStackTrace();}
			  }
			DatabaseValue newValue = new DatabaseValue();
			newValue.putDbValue(valueCurrent);
				wordCurrent = processWord(wordCurrent);
			newValue.putDbWord(wordCurrent);
			words.add(newValue);
		}
		return words;
	}
	public String processWord(String str){
		if(str.compareTo(SemanticTaggerConstants.TO)==0){
			str=SemanticTaggerConstants.TO;
		}else if(str.compareTo(SemanticTaggerConstants.FROM)==0){
			str=SemanticTaggerConstants.FROM;
		}else if(str.compareTo(SemanticTaggerConstants.AT)==0){
			str=SemanticTaggerConstants.TIME;
		}else if(str.compareTo(SemanticTaggerConstants.ON)==0){
			str=SemanticTaggerConstants.DATE;
		}else if(str.compareTo(SemanticTaggerConstants.OF)==0){
			str=SemanticTaggerConstants.OF;
		}else if(str.compareTo(SemanticTaggerConstants.AFTER)==0){
			str=SemanticTaggerConstants.AFTER;
		}else if(str.compareTo(SemanticTaggerConstants.BEFORE)==0){
			str=SemanticTaggerConstants.BEFORE;
		}		
		return str;
	}
	/*public String getWord(String word,Net syntaxNet){
		String str = null;
		Enumeration ver = syntaxNet.enumerateVerticesByUserKeySetToValue(UserdictConstants.NAME,word);
		if(!ver.hasMoreElements()){
			ver = syntaxNet.enumerateVerticesByUserKeySetToValue(UserdictConstants.AUG_NAME,word);
		}
		Vertex v1 = (Vertex)ver.nextElement();
		try {
			int wordNo = v1.userdict.getInteger(UserdictConstants.NO);
		} catch (KeyMissingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}*/
}
