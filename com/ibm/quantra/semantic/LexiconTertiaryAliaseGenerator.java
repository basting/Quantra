/*
 * Created on Jan 5, 2005
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
public class LexiconTertiaryAliaseGenerator {
	
	public void insertTertiaryAliases(LexiconContainerInterface lexCon){
		DictionaryInterface dictCom = new WordNetCommunicator();
		dictCom.initialize(WordNetConstants.propsFileName);
		Vector primaryWords = lexCon.getAllPrimaryWords();
		for(int i=0;i<primaryWords.size();i++){
			String primaryWord = (String)primaryWords.elementAt(i);
			Vector tertiaryWords = dictCom.getTertiaryWords(primaryWord);
			lexCon.setTertiaryWord(primaryWord,tertiaryWords);					
		}
	}
}
