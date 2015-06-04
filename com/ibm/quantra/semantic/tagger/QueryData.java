/*
 * Created on Mar 2, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic.tagger;

import java.util.Vector;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryData {
	private QuestionPart qPart;
	private Vector databaseValues;
	
	public void setQuestionPart(QuestionPart qPart){
		this.qPart = qPart;
	}
	public void setDataBaseValues(Vector dbValues){
		databaseValues = dbValues;
	}
	public QuestionPart getQuestionPart(){
		return qPart;
	}	
	public Vector getDBValues(){
		return databaseValues;
	}
}
