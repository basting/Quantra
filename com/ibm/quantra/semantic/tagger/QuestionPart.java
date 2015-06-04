/*
 * Created on Mar 2, 2005
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
public class QuestionPart {
	private String questionWord;
	private String root;
	
	public void setQuestionWord(String word){
		questionWord = word;
	}
	public void setRoot(String word){
		root = word;
	}
	public String getQuestionWord(){
		return questionWord;
	}
	public String getRoot(){
		return root;
	}
}
